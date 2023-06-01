<template>
	<view class="app-container">
		<view class="tab">
			<tabs activeColor="#000" :tabList="tabList" :active="currentTab" @clickTab="clickTab" />
		</view>

		<scroll-view class="content" scroll-with-animation scroll-y="true">
			<view v-if="orderList.length===0">
				<u-empty mode="order" margin-top="100" class="empty" />
			</view>
			<view v-else class="order" v-for="(orderItem, index) in orderList" :key="index">

				<u-count-down v-if="orderItem.orderStatus === 1" class="un-pay-time"
					:timestamp="new Date(orderItem.unPayEndTime).getTime() - new Date().getTime()" format="mm:ss"
					@finish="cancelOrder()" />

				<view class="title">
					<view class="left">
						<!-- <view>天府三街测试店</view> -->
						<view class="take-type">堂食</view>
					</view>
					<navigator :url="'/pages/order/detail?orderNo='+orderItem.orderNo">
						<view class="right">{{orderItem.orderStatusName}}</view>
					</navigator>
				</view>
				<view class="spu-box">
					<view class="item" v-for="(spu, index) in orderItem.spuList" :key="index">
						<view class="spu-info">
							<image :src="spu.coverImg" />
							<view class="right">
								<view class="name">{{spu.name}}</view>
								<view class="price">￥{{spu.price/100}}</view>
								<view class="num">x{{spu.num}}</view>
							</view>
						</view>
						<view class="line" />
					</view>
				</view>
				<view class="spu-bottom">
					<view class="time">下单时间：{{orderItem.createTime}}</view>
					<view class="sum-price">实付：
						<text style="color:red">￥{{orderItem.payPrice/100}}</text>
					</view>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import tabs from './component/tabs.vue'

	export default {
		components: {
			tabs
		},
		data() {
			return {
				// tabList: [{
				// 		name: '待支付',
				// 		orderStatus: 1
				// 	}, {
				// 		name: '已取消',
				// 		orderStatus: 2
				// 	}, {
				// 		name: '待发货',
				// 		orderStatus: 3
				// 	}, {
				// 		name: '待收货',
				// 		orderStatus: 4
				// 	},
				// 	{
				// 		name: '已完成',
				// 		orderStatus: 5
				// 	}, {
				// 		name: '已退款',
				// 		orderStatus: 6
				// 	}
				// ],
				tabList: [{
					name: '今日订单',
					dataType: 1
				}, {
					name: '历史订单',
					dataType: 2
				}],
				currentTab: 0,
				orderList: [],

			};
		},
		onLoad() {
			// 默认查询今日订单
			this.orderPage(1)
		},
		methods: {
			clickTab(item, index) {
				this.currentTab = index
				this.orderPage(item.dataType)
			},
			async orderPage(dataType) {
				let result = await this.$api.order.page({
					dataType: dataType
				});
				this.orderList = result.records
			},
			getUnPayTime(unPayEndTime) {
				let time = new Date(unPayEndTime).getTime() - new Date().getTime()
				return time
			}
		}
	}
</script>

<style lang="scss">
	.app-container {
		background-color: $bg-color;

		.tab {}

		.empty {
			// background-color: #000;
			// height: calc(100%)
		}

		.content {
			// padding: 10rpx 10rpx;
			width: 100%;
			height: 100%;

			.order {
				margin: 10rpx;
				border-radius: 30rpx;
				background-color: white;

				.un-pay-time,
				.title,
				.spu-box,
				.spu-bottom {
					padding: 0rpx 20rpx;

				}

				.title {
					display: flex;
					justify-content: space-between;
					font-size: $font-size-lg;
					height: 80rpx;
					line-height: 80rpx;


					.left {
						display: flex;
						font-weight: bold;

						.take-type {
							font-size: $font-size-sm;
							color: $text-color-assist;
							margin-left: 10rpx;
						}
					}

					.right {
						font-size: $font-size-lg;
						font-weight: bold;
						color: $color-primary;
					}
				}

				.spu-box {
					.item {
						.spu-info {
							display: flex;
							justify-content: space-between;

							image {
								padding: 0rpx 10rpx;
								width: 110rpx;
								height: 110rpx;
							}

							.right {
								display: flex;
								flex-direction: column;
								align-items: flex-end;

								.name,
								.price {
									font-size: $font-size-base;
								}

								.num {
									color: $text-color-assist;
									font-size: $font-size-sm;
								}
							}
						}

						.line {
							border-bottom: 1rpx $border-color dashed;
							margin-bottom: 5rpx;
						}

						&:nth-last-child(1) {
							.line {
								border-bottom: 0rpx $border-color dashed;
							}
						}
					}




				}

				.spu-bottom {
					display: flex;
					justify-content: space-between;
					align-items: center;

					.time {
						font-size: $font-size-sm;
					}

					.sum-price {
						font-weight: bold;
						font-size: $font-size-base;
					}
				}
			}
		}
	}
</style>