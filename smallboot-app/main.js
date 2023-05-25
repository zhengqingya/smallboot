import App from './App'


// #ifndef VUE3
import Vue from 'vue'
Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
	...App
})
app.$mount()
// #endif


// #ifdef VUE3
import {
	createSSRApp
} from 'vue'
import api from '@/api/index.js'
import store from '@/store'


import myComponent from '@/components/index'


import mixin from '@/utils/mixin'

import uviewPlus from 'uview-plus'


export function createApp() {
	const app = createSSRApp(App)

	// 配置全局api
	app.config.globalProperties.$api = api
	// store
	app.use(store)

	// 全局组件注册
	Object.keys(myComponent).forEach((key) => {
		app.component(key, myComponent[key])
	})

	// 抽取公用的实例 - 操作成功与失败消息提醒内容等
	app.mixin(mixin)

	// uview-plus
	app.use(uviewPlus)



	return {
		app
	}
}
// #endif