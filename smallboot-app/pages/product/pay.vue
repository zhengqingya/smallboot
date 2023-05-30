<template>
	<view class="app-container">
		<view class="base-box">
			<view class="list">
				<text class="title">配送方式</text>
				<view class="right-title">堂食</view>
			</view>
			<view class="list">
				<text class="title">取餐时间</text>
				<view class="right-title">立即</view>
			</view>
			<view class="list2">
				<text class="title">商品明细</text>
				<view class="cart-list">
					<view class="item" v-for="(item, index) in cartList" :key="index">
						<image :src="item.coverImg" class="image" />
						<view class="left">
							<view class="name">{{item.name}}</view>
							<view class="spec-desc">{{item.specDesc}}</view>
						</view>
						<view class="center">
							<text>￥{{item.price/100}}</text>
						</view>
						<view class="right">
							<view class="num">x{{item.num}}</view>
						</view>
					</view>
				</view>
			</view>
		</view>
		<!-- <view class="product-box">
			<h5>&nbsp;商品明细</h5>
			<u-cell-group>
				<u-cell>
					<template #value>
						<view class="cart-list">
							<view class="item" v-for="(item, index) in cartList" :key="index">
								<image :src="item.coverImg" class="image" />
								<view class="left">
									<view class="name">{{item.name}}</view>
									<view class="spec-desc">{{item.specDesc}}</view>
								</view>
								<view class="center">
									<text>￥{{item.price/100}}</text>
								</view>
								<view class="right">
									<view class="num">x{{item.num}}</view>
								</view>
							</view>
						</view>
					</template>
				</u-cell>
			</u-cell-group>
		</view>
		<view class="remark-box">
			<u-cell-group>
				<u-cell title="备注">
					<template #value>
						<input class="right-input" placeholder="无..." v-model="orderRemark" />
					</template>
				</u-cell>
			</u-cell-group>
		</view>
		<view class="pay-box">
			<u-cell>
				<template #value>
					<view>
						合计￥{{ cartList.reduce((total, item) => total += item.num * item.price, 0)/100 }}
					</view>
				</template>
			</u-cell>
			<u-cell>
				<template #value>
					<button type="warn" class="right-input" @tap="createOrder()">创建订单</button>
				</template>
			</u-cell>
		</view> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				cartList: [], // 购物车数据
				orderRemark: '',
			};
		},
		onLoad() {
			this.init()
		},
		methods: {
			async init() {
				// 购物车数据
				this.cartList = await this.$api.cart.list();
			},
			// 创建订单
			async createOrder() {
				let skuList = JSON.parse(JSON.stringify(this.cartList))
				let sumPrice = this.cartList.reduce((total, item) => total += item.num * item.price, 0)
				let {
					orderNo
				} = await this.$api.order.create({
					wxOpenid: "666",
					skuList: skuList,
					freight: Math.max.apply(Math, this.cartList.map(function(item) {
						return item.freight << 0
					})),
					totalPrice: sumPrice,
					payPrice: sumPrice,
					orderRemark: this.orderRemark
				});
				// 跳转到订单详情页面
				uni.navigateTo({
					url: '/pages/order/detail?orderNo=' + orderNo
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
	.list {
		display: flex;
		align-items: center;
		justify-content: space-between;
		border-bottom: 1rpx dashed #eee;
		height: 80rpx;

		.title {
			color: #333;
			font-size: 30rpx;
			font-weight: bold;
			display: flex;
			align-items: center;
		}

		.right-title {
			color: #999;
			font-size: 30rpx;
		}
	}

	.app-container {
		height: 1000rpx;
		// background-color: $bg-color;
		overflow-y: scroll; // 超出滚动

		.base-box,
		.product-box,
		.pay-box,
		.remark-box {
			padding: 10rpx 10rpx;

			.right-input {
				width: 200rpx;
				font-size: $font-size-sm;
			}
		}

		.product-box {

			.cart-list {
				width: 100%;

				.item {
					display: flex;
					justify-content: space-between;
					align-items: center;
					padding: 5rpx 0;
					position: relative;
					// border: 1rpx solid gainsboro;

					.image {
						width: 60rpx;
						height: 60rpx;
						margin-right: 10rpx;
					}

					.left {
						flex: 1;
						display: flex;
						flex-direction: column;

						.name {
							font-size: $font-size-sm;
							color: $text-color-base;
						}

						.spec-desc {
							color: $text-color-assist;
							font-size: $font-size-sm;
						}
					}

					.center {
						margin-right: 120rpx;
						font-size: $font-size-base;
					}

					.right {
						display: flex;
						align-items: center;
						justify-content: space-between;

						.num {
							font-size: $font-size-sm;
							width: 46rpx;
							height: 46rpx;
							text-align: center;
							line-height: 46rpx;
						}
					}
				}

			}
		}

		.pay-box {
			// position: absolute;
			// width: 100%;
			// bottom: 10rpx;
		}

	}
</style>