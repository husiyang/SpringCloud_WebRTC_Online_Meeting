package com.hly.july.biz.meeting.controller;

import com.hly.july.biz.meeting.entity.Event;
import com.hly.july.biz.meeting.entity.EventEnum;
import com.hly.july.biz.meeting.entity.MessageVO;
import com.hly.july.biz.meeting.entity.Watcher;
import com.hly.july.biz.meeting.service.api.BizUserApiService;
import com.hly.july.biz.meeting.service.impl.AsyncJobService;
import com.hly.july.biz.meeting.service.impl.ChatService;
import com.hly.july.biz.meeting.service.impl.MessageServiceImpl;
import com.hly.july.biz.meeting.service.impl.UserService;
import com.hly.july.common.core.constant.ContainerEnum;
import com.hly.july.common.core.exception.ServiceInternalException;
import com.hly.july.common.core.result.Result;
import com.hly.july.common.core.result.ResultCode;
import com.hly.july.common.core.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName ChatWebSocketController
 * @Description
 * @Author Linyuan Hou
 * @Date 2021/6/16 10:16
 * @Version 1.0.0
 **/
@RestController
@Slf4j
public class ChatWebSocketController {
    @Autowired
    private ChatService chatService;

    @Autowired
    private MessageServiceImpl messageService;

    @Resource
    private BizUserApiService bizUserApiService;

    @Autowired
    private AsyncJobService asyncJobService;

    @Autowired
    private UserService userService;

    @MessageMapping({"/watch/{userId}"})
    public Result<String> watchChatSession(@DestinationVariable String userId, Watcher watcher, OAuth2Authentication auth2Authentication) {
        String hostId = auth2Authentication.getPrincipal().toString();
        if (StringUtils.isNotEmpty(hostId)&&hostId.equals(watcher.getWatcherId())){
            watcher.setPeerId(userId);
            watcher.setGmtWatch(DateUtils.getCurrentDateTime());
            boolean result =  chatService.upInsertWatcher(watcher);
            if (result){
                return Result.success();
            }else{
                return Result.failure(ResultCode.API_DB_FAIL);
            }
        }else{
            return Result.failure(ResultCode.AUTH_NEED_LOGIN,"未登录");
        }

    }

    @MessageMapping({"/personal/{peerId}/sendmsg"})
    public Result<String> sendPersonalMessage(@DestinationVariable String peerId, MessageVO message, OAuth2Authentication auth2Authentication) {
        log.info("sendPersonalMessage peerId:{}, message:{}",peerId,message.toString());
        if (auth2Authentication!=null) {
            String hostId = auth2Authentication.getPrincipal().toString();
//            String hostId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            if (StringUtils.isNotEmpty(hostId)&&StringUtils.isNotEmpty(peerId)){
                log.info("sendPersonalMessage hostId:{}",hostId);
                Date messageDate = DateUtils.getCurrentDateTime();
                message.setFrom(hostId);
                message.setTo(peerId);
                message.setPeerType(ContainerEnum.PERSON.getDesc());
                message.setGmtCreate(messageDate);
                message.setType("message"); //用户只能发送message，不能发送event
                try {
                    boolean result = chatService.sendPersonalMessage(peerId,hostId,message);
                    if(result){
                        return Result.success();
                    }else{
                        return Result.failure(ResultCode.WEBSOCKET_MESSAGE_FAIL);
                    }
                }catch (ServiceInternalException e){
                    if(e.getResultCode().getCode()==ResultCode.USER_SOCIAL_BE_IGNORED.getCode()){
                        Event<String> event = Event.buildPersonal(EventEnum.EVENT_CONSPICUOUS_NOTIFY,"已被对方屏蔽",hostId,peerId);
                        userService.sendPersonalEvent(hostId,event);
                    }else if(e.getResultCode().getCode()==ResultCode.USER_SOCIAL_BE_BLACKED.getCode()){
                        Event<String> event = Event.buildPersonal(EventEnum.EVENT_CONSPICUOUS_NOTIFY,"已被对方拉黑",hostId,peerId);
                        userService.sendPersonalEvent(hostId,event);
                    }
                    return Result.failure(ResultCode.WEBSOCKET_MESSAGE_FAIL,e.getErrorMsg());
                }

            }else{
                return Result.failure(ResultCode.API_VALIDATION_ERROR);
            }
        }else{
            return Result.failure(ResultCode.AUTH_NEED_LOGIN,"未登录");
        }

    }

    @SubscribeMapping({"/historyMessage/{peerId}/{count}"})
    public Result<List<MessageVO>> getHistoryMessage(@DestinationVariable String peerId,@DestinationVariable String count, OAuth2Authentication auth2Authentication) {
        log.info("getHistoryMessage:{},count:{}",peerId,count);
        if (auth2Authentication!=null) {
            String hostId = auth2Authentication.getPrincipal().toString();
//            String hostId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            if (StringUtils.isNotEmpty(hostId)&&StringUtils.isNotEmpty(peerId)&&StringUtils.isNumeric(count)){
                Integer dayCount = Integer.valueOf(count);
                chatService.changeUnRead(peerId,ContainerEnum.PERSON.getCode(),hostId,"clear");
                List<MessageVO> messageVOList = messageService.getMessageVOByIdAndDayCount(hostId,peerId,dayCount,ContainerEnum.PERSON.getDesc());
                if(messageVOList!=null){
                    asyncJobService.sendRecentListNotify(hostId,DateUtils.getCurrentDateTime());
                    return Result.success(messageVOList);
                }else{
                    return Result.failure(ResultCode.WEBSOCKET_MESSAGE_FAIL);
                }
            }else{
                return Result.failure(ResultCode.API_VALIDATION_ERROR);
            }
        }else{
            return Result.failure(ResultCode.AUTH_NEED_LOGIN,"未登录");
        }
    }

}
