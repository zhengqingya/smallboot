import api from '@/api'

const store = {
	namespaced: true,
	// 存放数据
	state: {
		id: null,
		openid: null,
		nickname: 'zhengqingya',
		avatarUrl: ''
	},
	getters: {
		getUserInfo(state) {
			return state
		}
	},
	// 同步变更数据
	mutations: {
		setUserInfo(state, userInfo) {
			// 只能一个一个设置值...
			state.id = userInfo.id
			state.openid = userInfo.openid
			state.nickname = userInfo.nickname
			state.avatarUrl = userInfo.avatarUrl
		}
	},
	// 和后台交互获取数据
	actions: {
		// 登录
		async login({
			commit,
			state
		}, params) {
			uni.getUserProfile({
				desc: '登录',
				success: async (data) => {
					// 拿到的微信用户信息
					console.log(data)
					uni.login({
						provider: 'weixin',
						success: async (res) => {
							// console.log(res)
							// 请求后台获取openid注册登录成功后返回基本用户信息
							let result = await api.user.login({
								code: res.code,
								iv: data.iv,
								encryptedData: data.encryptedData,
								userInfo: {
									nickName: data.userInfo.nickName,
									avatarUrl: data.userInfo.avatarUrl
								}
							})
							const {
								tokenName,
								tokenValue
							} = result
							uni.setStorageSync('tokenName', tokenName)
							uni.setStorageSync(tokenName, tokenValue)

							// console.log(1, result)
							commit('setUserInfo', result)
						}
					})
				},
				fail: (err) => {
					console.log(err);
				}
			})
		},
		// 获取用户信息
		// async getUserInfo({
		// 	commit,
		// 	state
		// }, userId) {
		// 	let userInfo = await api.user.getUserInfo({
		// 		userId: userId
		// 	})
		// 	commit('setUserInfo', userInfo)
		// },
		// 退出登录
		logout({
			commit,
			state
		}) {
			api.user.logout({
				id: state.id
			})
			uni.removeStorage({
				key: 'userInfo'
			})
			uni.removeStorage({
				key: 'token'
			})
			commit('setUserInfo', {})
		}
	}
}

export default store