<template>

	<view class="app-container">
		<!-- 轮播图 -->
		<u-swiper :list="state.detailData.slideImgList" keyName="url" indicator indicatorMode="line"
			circular></u-swiper>

		<view class="subject">
			<view>
				<view class="name">{{state.detailData.name}}</view>
				<view class="use-stock">已售：{{state.detailData.useStock}}</view>
			</view>
			<view class="price">¥ {{state.detailData.sellPrice/100}}</view>
		</view>

		<view class="content">
			<text>x</text>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		toRefs,
		reactive,
		getCurrentInstance,
		onMounted
	} from 'vue';
	// 组件实例
	const {
		proxy
	} = getCurrentInstance();

	const state = reactive({
		id: null,
		detailData: {}
	});

	const detailData = reactive({});

	onMounted(() => {
		let id = proxy.$route.query.id
		id = '1534420706752856064' // 测试使用
		if (!id) {
			uni.switchTab({
				url: '/pages/index/index'
			})
			return
		}
		state.id = id
		detail()
	});

	async function detail() {
		let result = await proxy.$api.spu.detail(state.id)
		state.detailData = result
	};
</script>
<style lang="scss" scoped>
	$price-color: #ff462e;

	page {
		background-color: #f8f8f8;
	}

	.app-container {
		.subject {
			display: flex;
			justify-content: space-between;
			align-items: center;
			background-color: #fff;
			padding-left: 15rpx;
			height: 120rpx;

			.name {
				font-weight: bold;
			}

			.use-stock {
				color: #999;
				font-size: small;
			}

			.price {
				color: $price-color;
				margin-right: 10rpx;
			}
		}

		.content {
			margin-top: 20rpx;
			background-color: #fff;
		}
	}
</style>