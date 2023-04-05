<template>
	<div class="goods-list">
		<van-search v-model="search" placeholder="输入关键字搜索商品" />
		<van-tab v-model="selectedTab" :tabs="tabs" />
		<van-pull-refresh v-model="refreshing" @refresh="onRefresh" />
		<van-list :finished="finished" :loading="loading" finished-text="没有更多了">
			<van-cell v-for="(item, index) in goodsList" :key="index" :title="item.title" :value="item.price">
				<img slot="icon" :src="item.image" />
			</van-cell>
		</van-list>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				search: '',
				selectedTab: 0,
				tabs: [{
					title: '综合'
				}, {
					title: '价格'
				}, {
					title: '销量'
				}],
				refreshing: false,
				finished: false,
				loading: false,
				goodsList: [{
						title: '商品1',
						price: 100,
						image: 'https://img.yzcdn.cn/vant/cat.jpeg'
					},
					{
						title: '商品2',
						price: 200,
						image: 'https://img.yzcdn.cn/vant/cat.jpeg'
					},
					{
						title: '商品3',
						price: 300,
						image: 'https://img.yzcdn.cn/vant/cat.jpeg'
					},
					{
						title: '商品4',
						price: 400,
						image: 'https://img.yzcdn.cn/vant/cat.jpeg'
					},
					{
						title: '商品5',
						price: 500,
						image: 'https://img.yzcdn.cn/vant/cat.jpeg'
					},
					{
						title: '商品6',
						price: 600,
						image: 'https://img.yzcdn.cn/vant/cat.jpeg'
					},
				],
			};
		},
		methods: {
			onRefresh() {
				setTimeout(() => {
					this.goodsList = [{
							title: '商品7',
							price: 700,
							image: 'https://img.yzcdn.cn/vant/cat.jpeg'
						},
						{
							title: '商品8',
							price: 800,
							image: 'https://img.yzcdn.cn/vant/cat.jpeg'
						},
						{
							title: '商品9',
							price: 900,
							image: 'https://img.yzcdn.cn/vant/cat.jpeg'
						},
					];
					this.refreshing = false;
				}, 1000);
			},
			onLoad() {
				setTimeout(() => {
					if (this.goodsList.length >= 9) {
						this.finished = true;
					} else {
						this.goodsList.push({
							title: `商品${this.goodsList.length + 1}`,
							price: (this.goodsList.length + 1) * 100,
							image: 'https://img.yzcdn.cn/vant/cat.jpeg'
						});
						this.loading = false;
					}
				}, 1000);
			}
		}
	};
</script>