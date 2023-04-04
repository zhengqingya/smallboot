<template>
	<view>
		<input class="" placeholder="请输入商品" />
		<van-button type="primary">主要按钮</van-button>
		<van-button type="success">成功按钮</van-button>
		<van-button type="default">默认按钮</van-button>
		<van-button type="warning">警告按钮</van-button>
		<van-button type="danger">危险按钮</van-button>

		<!-- <text>{{productList}}</text> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				productList: []
			};
		},
		onLoad() {
			this.init()
		},
		methods: {
			async init() {
				// let result = await this.$api.spu.page({
				// 	name: ''
				// })
				// this.productList = result.records;
			}
		}
	};
</script>

<style lang="scss" scoped>

</style>