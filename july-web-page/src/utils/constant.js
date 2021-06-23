
export let CONSTANT = {
    CONTAINER_GROUP : "group",
    CONTAINER_PERSON : "person",
    WS_METHOD_PERSONAL : "personal",
    WS_METHOD_BROADCAST : "broadcast",
    SHOUTING_MESSAGE : "message",
    SHOUTING_EVENT : "event",
    CONTACTS_CATEGORY_RECENT: "recent",
    CONTACTS_CATEGORY_BOOKMARK: "bookmark",
    CONTACTS_CATEGORY_GROUP: "group",

    CHAT_WATCH_ACTION_ENTRY:"entry",
    CHAT_WATCH_ACTION_LEAVE:"leave",

    RELATION_TYPE_FRIEND:1, //1
    RELATION_TYPE_TEMP:2, //2
    RELATION_TYPE_BLACK:3, //3
    RELATION_TYPE_IGNORE:4, //4
    DATE_FORMAT:'YYYY-MM-DD HH:mm:ss',

    USER_ACTIVE_STATUS_OFFLINE:1, //offline
    USER_ACTIVE_STATUS_INVISIBLE:2, //active_invisible
    USER_ACTIVE_STATUS_VISIBLE:3, //active_visible
    USER_ACTIVE_STATUS_ONLY_CATEGORY_BOOKMARK:4, //active_only_category_bookmark
    USER_ACTIVE_STATUS_ONLY_MESSAGE:5, //active_only_message
}

export let EVENT_CODE = {
    NEW_JOINER : 'E1001',// E1001 有新人进入事件
    RECENT_CHANGED: 'E1005',// E1005 最近联系人列表改变
    UNREAD_CHANGED: 'E1006', // E1006 未读消息数改变
    CONSPICUOUS_NOTIFY: 'E1007', // E1007 显示消息
    EVENT_CALL_RING: 'E2001', //呼叫响铃
    EVENT_CALL_IN: 'E2002', //有新呼叫
    EVENT_CALL_FAIL: 'E2003', //呼叫失败
    EVENT_CALL_BUSY: 'E2004', //呼叫对方忙
    EVENT_CALL_OFFLINE: 'E2005', //呼叫对方不在线
    EVENT_CALL_DENY: 'E2006', //呼叫拒绝
    EVENT_CALL_PRE_CONNECT: 'E2007', //呼叫双方同意
    EVENT_CALL_CONNECTED: 'E2008', //呼叫双方通话中
    EVENT_CALL_HANGUP: 'E2009',//呼叫挂断
}

export let RESULT_CODE = {
    SUCCESS : 10001,// 成功返回
}