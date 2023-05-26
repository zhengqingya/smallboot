import config from '@/config.js'

const request = ({
	url, // 请求url
	method, // 请求方式：get/post/put/delete
	params, // get请求提交参数
	data, // post/put请求提交参数
	headers // 请求头
}) => {
	return new Promise((resolve, reject) => {
		if (!headers) {
			const tokenName = uni.getStorageSync('tokenName')
			const tokenValue = uni.getStorageSync(tokenName)
			headers = {
				'Content-Type': 'application/json;charset=utf-8',
				'TENANT_ID': 1,
				'Authorization-smallboot': 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpblR5cGUiOiJsb2dpbiIsImxvZ2luSWQiOiJ7XCJhdXRoU291cmNlRW51bVwiOlwiQlwiLFwidXNlcklkXCI6XCIxXCIsXCJ1c2VybmFtZVwiOlwiYWRtaW5cIixcInJvbGVDb2RlTGlzdFwiOltcInN1cGVyX2FkbWluXCJdfSIsInJuU3RyIjoiY1dnVVV3R2pmbTZUY2locVhjdWI5M2JDa244TG93N2EifQ.CnxadctOk_ErgT1uTPZs82_OciXfyk7qfv0SMoI7ddA' // TODO 仅测试使用
			}
			if (tokenValue) {
				headers[tokenName] = tokenValue
			}
			// console.log(1, headers)
		}
		uni.request({
			url: config.baseUrl + url,
			data: method === 'get' ? params : data,
			method: method,
			header: headers,
			// 收到开发者服务器成功返回的回调函数
			success: (res) => {
				// console.log(666, res)
				const {
					code,
					msg
				} = res.data
				if (code == 200) {
					return resolve(res.data.data)
				}
				uni.showToast({
					icon: 'none',
					duration: 3000,
					title: msg
				});
				return reject(msg)
			},
			// 接口调用失败的回调函数
			fail(error) {
				console.log('请求错误：', error)
				uni.showToast({
					icon: 'none',
					duration: 3000,
					title: '网络异常，请稍后重试！'
				});
				return reject(error)
			},
			// 接口调用结束的回调函数（调用成功、失败都会执行）
			complete() {}
		});
	})
}


//向外暴露request
export default request