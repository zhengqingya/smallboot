<template>
	<!--  商品详情  -->
	<u-popup class="choose-sku" :show="isShow" @close="handleClose" :round="10" mode="bottom" @open="open"
		:closeable="false">
		<view class="app-container">
			<view class="top">
				<image :src="spu.coverImg" class="image"></image>
			</view>
			<scroll-view class="detail" scroll-y>
				<view class="wrapper">
					<view class="basic">
						<view class="name">{{ spu.name }}</view>
					</view>
					<view class="attrList">
						<view class="attr" v-for="(item, index) in spu.attrList" :key="index">
							<view class="title">
								<text class="name">{{ item.attrKeyName }}</text>
								<view class="desc" v-if="item.attrKeyName">({{ item.attrKeyName }})</view>
							</view>
							<view class="values">
								<view class="value" :class="{'default': value.isChoose}"
									v-for="(value, key) in item.attrValueList" :key="key" @tap="chooseSku(index, key)">
									{{ value.attrValueName }}
								</view>
							</view>
						</view>
					</view>
				</view>
			</scroll-view>
			<view class="action">
				<view class="left">
					<view class="price">￥{{ spu.sellPrice/100 }}</view>
					<view class="spec-desc">
						{{ calSkuSpecDesc() }}
					</view>
				</view>
				<view class="right">
					<button type="default" plain class="btn" size="mini" hover-class="none"
						@tap="updateSkuNum(-1)">-</button>
					<view class="number">{{ spu.num }}</view>
					<button type="primary" class="btn btn-right" size="min" hover-class="none"
						@tap="updateSkuNum(+1)">+</button>
				</view>
			</view>
			<view class="add-cart" @tap="addCart">
				加入购物车
			</view>
		</view>
	</u-popup>
</template>

<script>
	export default {
		name: "sku",
		props: {
			// 商品选规格时的详情框是否显示 
			isShow: {
				type: Boolean,
				default: false
			},
			spu: {
				type: Object,
				default: () => {},
			},
		},
		data() {
			return {

			}
		},
		methods: {
			async init() {

			},

			calSkuSpecDesc() {
				let attrDescList = []
				if (this.spu.attrList) {
					this.spu.attrList.forEach(attr => {
						attr.attrValueList.forEach(value => {
							if (value.isChoose) {
								attrDescList.push(value.attrValueName)
							}
						})
					})
				}
				return attrDescList.join(',')
			},
			// 加入购物车
			addCart() {
				// 确认sku-id
				let specList = []
				let skuId = null;
				let skuPrice = null;
				this.spu.attrList.forEach(attr => {
					attr.attrValueList.forEach(value => {
						if (value.isChoose) {
							// 这里是选择的sku
							specList.push({
								"attrKeyId": attr.attrKeyId,
								"attrKeyName": attr.attrKeyName,
								"attrValueId": value.attrValueId,
								"attrValueName": value.attrValueName
							})
						}
					})
				})
				this.spu.skuList.forEach(sku => {
					let skuReSpecList = sku.specList
					if (JSON.stringify(skuReSpecList) == JSON.stringify(specList)) {
						skuId = sku.id
						skuPrice = sku.sellPrice
					}
				})
				if (skuId == null) {
					uni.showToast({
						icon: 'none',
						duration: 1000,
						title: '请选择sku！'
					});
					return
				}
				// 请求后端开始加入购物车数据
				this.$api.cart.add({
					spuId: this.spu.id,
					skuId: skuId,
					num: this.spu.num
				});
				this.cartList.push({
					spuId: this.spu.id,
					skuId: skuId,
					name: this.spu.name,
					num: this.spu.num,
					specDesc: this.calSkuSpecDesc(),
					price: skuPrice
				})
				this.isShowSku = false
			},
			updateSkuNum(num) {
				if (this.spu.num + num === 0) {
					return
				}
				this.spu.num += num
				this.num = this.spu.num
			},
			handleClose() {
				this.$emit('close', false)
			}
		}
	}
</script>

<style lang="scss" scoped>
	.app-container {
		height: 500px;
		overflow-y: scroll; // 超出滚动

		width: 100%;
		height: 100%;
		display: flex;
		flex-direction: column;


		.top {
			height: 220rpx;
			padding: 30rpx 0;
			display: flex;
			justify-content: center;
			align-items: center;
			position: relative;

			.image {
				width: 200rpx;
				height: 200rpx;
			}
		}

		.detail {
			min-height: 1vh;
			max-height: calc(90vh - 320rpx - 80rpx - 120rpx);

			.wrapper {
				width: 100%;
				height: 100%;
				overflow: hidden;

				.basic {
					padding: 10rpx 30rpx;
					font-size: $font-size-base;
					color: #5A5B5C;
					margin-bottom: 10rpx;
				}

				.attrList {
					width: 100%;
					border-top: 2rpx solid #F5F5F5;
					padding: 10rpx 30rpx 0;
					display: flex;
					flex-direction: column;

					.attr {
						width: 100%;
						display: flex;
						flex-direction: column;
						margin-bottom: 30rpx;
						padding-bottom: -16rpx;

						.title {
							width: 100%;
							display: flex;
							justify-content: flex-start;
							align-items: center;
							margin-bottom: 20rpx;

							.name {
								font-size: $font-size-base;
								color: #5A5B5C;
								margin-right: 20rpx;
							}

							.desc {
								flex: 1;
								font-size: $font-size-base;
								color: $color-primary;
								overflow: hidden;
								text-overflow: ellipsis;
								white-space: nowrap;
							}
						}

						.values {
							width: 100%;
							display: flex;
							flex-wrap: wrap;

							.value {
								border-radius: 8rpx;
								background-color: #F5F5F5;
								padding: 16rpx 30rpx;
								font-size: $font-size-base;
								color: #919293;
								margin-right: 16rpx;
								margin-bottom: 16rpx;

								&.default {
									background-color: $color-primary;
									color: white;
								}
							}
						}
					}
				}
			}
		}

		.action {
			display: flex;
			align-items: center;
			justify-content: space-between;
			background-color: #F5F5F5;
			height: 100rpx;
			padding: 0 26rpx;

			.left {
				flex: 1;
				display: flex;
				flex-direction: column;
				justify-content: center;
				margin-right: 20rpx;
				overflow: hidden;

				.price {
					font-size: 28rpx;
					color: #5A5B5C;
				}

				.spec-desc {
					color: #919293;
					font-size: 24rpx;
					width: 100%;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
			}

			.right {
				display: flex;
				align-items: center;
				justify-content: space-around;

				.number {
					font-size: 28rpx;
					width: 44rpx;
					height: 44rpx;
					line-height: 44rpx;
					text-align: center;
				}

				.btn {
					padding: 0;
					font-size: 28rpx;
					width: 44rpx;
					height: 44rpx;
					line-height: 44rpx;
					border-radius: 100%;

					&.btn-right {
						background-color: $color-primary;
					}
				}
			}
		}

		.add-cart {
			background-color: $color-primary;
			color: white;
			height: 80rpx;
			text-align: center;
			line-height: 80rpx;
			font-size: $font-size-base;
			border-radius: 0 0 12rpx 12rpx;
		}
	}
</style>