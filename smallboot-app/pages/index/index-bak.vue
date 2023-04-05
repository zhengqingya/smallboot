<template>
	<view>
		<!-- <van-search v-model="name" placeholder="请输入搜索关键词" /> -->

		<van-search v-model="name" show-action shape="round" placeholder="请输入搜索关键词">
			<template #action>
				<div @click="onClickButton">搜索</div>
			</template>
		</van-search>



		<van-swipe class="my-swipe" :autoplay="3000" lazy-render>
			<van-swipe-item v-for="image in images" :key="image">
				<img class="img" style="" :src="image" />
			</van-swipe-item>
		</van-swipe>


		<!-- <text>{{productList}}</text> -->
	</view>
</template>


<script setup>
	import {
		ref
	} from 'vue';
	import {
		showToast
	} from 'vant';
	const name = '11';
	const onClickButton = () => showToast(name);

	const images = [
		'https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg',
		'https://fastly.jsdelivr.net/npm/@vant/assets/apple-2.jpeg',
	];



	// export default {
	// 	data() {
	// 		return {
	// 			productList: []
	// 		};
	// 	},
	// 	onLoad() {
	// 		this.init()
	// 	},
	// 	methods: {
	// 		async init() {
	// 			// let result = await this.$api.spu.page({
	// 			// 	name: ''
	// 			// })
	// 			// this.productList = result.records;
	// 		}
	// 	}
	// };
</script>

<style lang="scss" scoped>
	.img {
		height: 120px;
		width: 100%;
	}
</style>