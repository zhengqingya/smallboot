<template>
	<view>
		<text>{{productList}}</text>
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
				let result = await this.$api.spu.page({
					name: ''
				})
				this.productList = result.records;
			}
		}
	};
</script>

<style lang="scss" scoped>

</style>