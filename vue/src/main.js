import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import elementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import '@/assets/css/global.css'
import locale from 'element-plus/lib/locale/lang/zh-cn'
createApp(App).use(store).use(router).use(elementPlus, {locale}).mount('#app')
