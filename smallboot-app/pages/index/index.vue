<template>
	<div>
		111
		<!-- 搜索栏 -->
		<!-- <van-search v-model="state.keyword" show-action shape="round" placeholder="请输入搜索关键词">
			<template #action>
				<div @click="onLoad">搜索</div>
			</template>
		</van-search> -->

		<!-- 轮播图 -->
		<!-- <van-swipe class="my-swipe" :autoplay="3000" lazy-render>
			<van-swipe-item v-for="image in [
			'https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg',
			'https://fastly.jsdelivr.net/npm/@vant/assets/apple-2.jpeg',
		]" :key="image">
				<img class="img" :src="image" />
			</van-swipe-item>
		</van-swipe> -->


		<!-- 商品列表 -->
		<!-- <van-pull-refresh v-model="state.refreshing" @refresh="onRefresh">
			<van-list v-model:loading="state.loading" :finished="state.finished" finished-text="没有更多了" @load="onLoad">
				<navigator v-for="item in state.list" :key="item" :url="'/pages/index/detail?id='+item.id">
					<van-card :price="item.sellPrice/100" :title="item.name" :thumb="item.coverImg" />
				</navigator>
			</van-list>
		</van-pull-refresh> -->
	</div>
</template>

<script setup>
	import {
		ref,
		toRefs,
		reactive,
		getCurrentInstance
	} from 'vue';
	// 组件实例
	const {
		proxy
	} = getCurrentInstance();



	const state = reactive({
		keyword: '',
		list: [],
		page: 0,
		loading: false,
		finished: false,
		refreshing: false,
	})

	const onLoad = () => {
		if (state.refreshing) {
			state.list = [];
			state.page = 1
			state.refreshing = false;
		} else {
			state.page++
		}

		getList(state.keyword);

		state.loading = false;
	};

	async function getList(name) {
		let result = await proxy.$api.spu.page({
			name: name
		})
		state.list = result.records;
		if (result.current == result.pages) {
			state.finished = true;
		}
	};

	const onRefresh = () => {
		// 清空列表数据
		state.finished = false;

		// 重新加载数据
		// 将 loading 设置为 true，表示处于加载状态
		state.loading = true;
		onLoad();
	};
</script>
<style lang="scss" scoped>
	.img {
		height: 320rpx;
		width: 100%;
	}
</style>