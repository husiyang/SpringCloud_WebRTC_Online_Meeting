import Vue from 'vue'

import './styles/quasar.scss'
import 'quasar/dist/quasar.ie.polyfills'
import lang from 'quasar/lang/zh-hans.js'
import '@quasar/extras/roboto-font/roboto-font.css'
import '@quasar/extras/material-icons/material-icons.css'
import '@quasar/extras/material-icons-outlined/material-icons-outlined.css'
import '@quasar/extras/material-icons-round/material-icons-round.css'
import '@quasar/extras/material-icons-sharp/material-icons-sharp.css'
import '@quasar/extras/fontawesome-v5/fontawesome-v5.css'
import '@quasar/extras/eva-icons/eva-icons.css'
import { Quasar, Cookies, LocalStorage, SessionStorage, QCard,
    QCardSection,   Intersection,
    QCardActions,  QAjaxBar, QBtn,  QSlideTransition,QSpace,QSeparator, QInput,  QForm,QIcon,QTooltip,  QToggle,QSpinnerBall,QSpinnerRadio, Notify, QChatMessage, QInfiniteScroll,QUploader, QFile,QScrollObserver, QBar,
    QList,  QMenu,
    ClosePopup,QSelect,
    QItem,
    QItemSection,
    QItemLabel,QDrawer,  QLayout,
    QPageContainer,
    QPage,
    QHeader,
    QFooter,
    QPageSticky,
    QPageScroller} from 'quasar'

Vue.use(Quasar, {
  config: {notify: { /* look at QUASARCONFOPTIONS from the API card (bottom of page) */ }},
  plugins: {
      Cookies,
      LocalStorage,
      SessionStorage,
      Notify
  },
    directives: {
        Intersection
    },
  components: {
      QCard,
      QCardSection,
      QCardActions,
      QAjaxBar,
      QBtn,
      QSlideTransition,
      QSpace,
      QSeparator,
      QInput,
      QForm,
      QIcon,
      QTooltip,
      QToggle,
      QSpinnerBall,
      QSpinnerRadio,
      QChatMessage,
      QInfiniteScroll,
      QScrollObserver,
      QUploader,
      QFile,
      QBar,
      QList,
      QItem,
      QItemSection,
      QItemLabel,
      QDrawer,
      QMenu,
      ClosePopup,
      QLayout,
      QPageContainer,
      QPage,
      QHeader,
      QFooter,
      QPageSticky,
      QPageScroller,
      QSelect
  },
  lang: lang
 })
