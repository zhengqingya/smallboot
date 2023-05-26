<template>
	<view class="app-container">
		<view>
			<!-- 获取基本信息 -->
			<button type="primary" withCredentials="true" @tap="getUserProfile">授权登录</button>

			<view>
				openid：{{$store.state.user.openid}}
				<br />
				昵称：{{$store.state.user.nickname}}
			</view>

			<button type="warn" @tap="localLogin">本地临时登录</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// openid: this.$store.state.user.openid, 
				// nickname: this.$store.state.user.nickname
			}
		},
		onLoad() {
			this.init()
		},
		methods: {
			async init() {

			},
			async getUserProfile() {
				await this.$store.dispatch("user/login");
			},
			async localLogin() {
				await this.$store.dispatch("user/localLogin");
			}
		}
	}
</script>

<style lang="scss">
	.app-container {}
</style>