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

