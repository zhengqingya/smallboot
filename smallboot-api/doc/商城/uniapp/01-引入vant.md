# 引入vant

https://vant-ui.github.io/vant/#/zh-CN/quickstart

安装依赖

```
cnpm i vant
```

`main.js`

```
// #ifdef VUE3
// 引入vant
import 'vant/lib/index.css';
import vant from 'vant';

export function createApp() {
	const app = createSSRApp(App)

	// vant
	app.use(vant)

	return {
		app
	}
}
// #endif
```

### 使用

```
<template>
	<view>
		<van-button type="primary">主要按钮</van-button>
		<van-button type="success">成功按钮</van-button>
		<van-button type="default">默认按钮</van-button>
		<van-button type="warning">警告按钮</van-button>
		<van-button type="danger">危险按钮</van-button>
	</view>
</template>
```

![img.png](images/vant-use.png)

### 运行到微信开发者工具中报错

```shell
17:39:16.618 "isVNode" is not exported by "node_modules/@dcloudio/uni-mp-vue/dist/vue.runtime.esm.js", imported by "../../../../../../code/workspace-me/smallboot/smallboot-app/node_modules/vant/node_modules/@vant/use/dist/index.esm.mjs".
17:39:16.618 at node_modules/vant/node_modules/@vant/use/dist/index.esm.mjs:79:2
17:39:16.620   77: // src/useRelation/useChildren.ts
17:39:16.620   78: import {
17:39:16.623   79:   isVNode,
17:39:16.623         ^
17:39:16.625   80:   provide,
17:39:16.626   81:   reactive,
```

![img.png](images/vant-use-problem.png)

无法解决，因此换用其它UI组件库...

