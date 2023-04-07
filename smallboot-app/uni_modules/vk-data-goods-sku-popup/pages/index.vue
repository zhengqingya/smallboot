<template>
	<view class="app">
		<!-- 此为全功能演示版本，新手上手建议先看 pages/index/index-static.vue 页面内的代码 -->
		<button @click="sku_key = true">打开SKU组件</button>
		<vk-data-goods-sku-popup
			ref="skuPopup"
			v-model="sku_key" 
			border-radius="20" 
			:custom-action="findGoodsInfo"
			:mode="form.skuMode"
			:buy-now-text="form.buyNowText"
			:add-cart-text="form.addCartText"
			:no-stock-text="form.noStockText"
			:min-buy-num="form.minBuyNum"
			:max-buy-num="form.maxBuyNum"
			:step-buy-num="form.stepBuyNum"
			:stepStrictly="form.stepStrictly"
			:show-close="form.showClose"
			:mask-close-able="form.maskCloseAble"
			:hide-stock="form.hideStock"
			:theme="form.theme"
			:default-select="form.defaultSelect"
			:goods-id="goods_id"
			@open="openSkuPopup"
			@close="closeSkuPopup"
			@add-cart="addCart"
			@buy-now="buyNow"
		></vk-data-goods-sku-popup>
		<view class="config-wrap">
			<view class="config-title">
				参数配置
			</view>
			<view>
				<view class="form-item">
					<view class="title" style="width: 180rpx;">更换商品</view>
					<radio-group name="radio"  @change="goodsChange">
						<view class="radio">
							<radio value="001" checked/><text>商品1：多组多规格商品</text>
						</view>
						<view class="radio">
							<radio value="002" /><text>商品2：单组多规格商品</text>
						</view>
						<view class="radio">
							<radio value="003" /><text>商品3：单组单规格商品</text>
						</view>
						<view class="radio">
							<radio value="004" /><text>商品4：暂无库存商品</text>
						</view>
					</radio-group>
				</view>
				
				<view class="form-item" style="margin-top: 20rpx;">
					<view class="title" style="width: 180rpx;">模式</view>
					<radio-group name="radio"  @change="skuModeChange">
						<view class="radio">
							<radio value="1" checked/><text>都显示</text>
						</view>
						<view class="radio">
							<radio value="2" /><text>只显示购物车</text>
						</view>
						<view class="radio">
							<radio value="3" /><text>只显示立即购买</text>
						</view>
					</radio-group>
				</view>
				<view class="form-item" style="margin-top: 20rpx;">
					<view class="title" style="width: 180rpx;">主题风格</view>
					<radio-group name="radio" @change="themeChange">
						<view class="radio">
							<radio value="default" checked/><text>默认</text>
						</view>
						<view class="radio">
							<radio value="red-black" /><text>红黑</text>
						</view>
						<view class="radio">
							<radio value="black-white" /><text>黑白</text>
						</view>
						<view class="radio">
							<radio value="coffee" /><text>咖啡</text>
						</view>
						<view class="radio">
							<radio value="green" /><text>浅绿</text>
						</view>
					</radio-group>
				</view>
				<view class="form-item">
					<view class="title">立即购买文字</view>
					<view class="input-view">
						<view style="width: 30rpx;height: 30rpx;"></view>
						<input class="input" v-model="form.buyNowText"/>
					</view>
				</view>
				<view class="form-item">
					<view class="title">加入购物车文字</view>
					<view class="input-view">
						<view style="width: 30rpx;height: 30rpx;"></view>
						<input class="input" v-model="form.addCartText"/>
					</view>
				</view>
				<view class="form-item">
					<view class="title">无库存时按钮文字</view>
					<view class="input-view">
						<view style="width: 30rpx;height: 30rpx;"></view>
						<input class="input" v-model="form.noStockText"/>
					</view>
				</view>
				<view class="form-item">
					<view class="title">最小购买数量</view>
					<vk-data-input-number-box
						v-model="form.minBuyNum" :min="1" :max="10000" :step="1" :positive-integer="true">
					</vk-data-input-number-box>
				</view>
				<view class="form-item">
					<view class="title">最大购买数量</view>
					<vk-data-input-number-box
						v-model="form.maxBuyNum" :min="1" :max="10000" :step="1" :positive-integer="true">
					</vk-data-input-number-box>
				</view>
				<view class="form-item">
					<view class="title">步进器步长</view>
					<vk-data-input-number-box
						v-model="form.stepBuyNum" :min="1" :max="10000" :step="1" :positive-integer="true">
					</vk-data-input-number-box>
				</view>
				<view class="form-item">
					<view class="title">显示关闭按钮</view>
					<switch checked @change="showCloseChange" />
				</view>
				<view class="form-item">
					<view class="title">点击遮罩关闭组件</view>
					<switch checked @change="maskCloseAbleChange" />
				</view>
				<view class="form-item">
					<view class="title">是否只能输入 step 的倍数</view>
					<switch @change="stepStrictlyChange" />
				</view>
				<view class="form-item">
					<view class="title">是否隐藏库存</view>
					<switch @change="hideStockChange" />
				</view>
		</view>
		</view>
	</view>
</template>

<script>
	var that;											// 当前页面对象
	var app = getApp();						// 可获取全局配置
	export default {
		data() {
			return {
				goods_id:"001",
				sku_key:false,
				form:{
					skuMode:1,
					buyNowText:"立即购买",
					addCartText:"加入购物车",
					noStockText:"该商品已抢完",
					minBuyNum:1,
					maxBuyNum:10000,
					stepBuyNum:1,
					stepStrictly:false,
					hideStock:false,
					theme:"default", // 主题
					// defaultSelect:{
					// 	sku:["红色","256G","公开版"],
					// 	num:5
					// }
				}
			}
		},
		// 监听 - 页面每次【加载时】执行(如：前进)
		onLoad(options) {
			that = this;
			that.init(options);
		},
		methods: {
			// 初始化
			init(options = {}){
				
			},
			// sku组件 开始-----------------------------------------------------------
			openSkuPopup(){
				console.log("监听 - 打开sku组件");
				// that.$refs.skuPopup.selectSku({
				// 	sku:["白色","256G","公开版"],
				// 	num:5
				// });
			},
			closeSkuPopup(){
				console.log("监听 - 关闭sku组件");
			},
			// 加入购物车前的判断
			addCartFn(obj){
				let { selectShop } = obj;
				// 模拟添加到购物车,请替换成你自己的添加到购物车逻辑
				let res = {};
				let name = selectShop.goods_name;
				if(selectShop.sku_name != "默认"){
					name += "-"+selectShop.sku_name;
				}
				res.msg = `${name} 已添加到购物车`;
				if(typeof obj.success == "function") obj.success(res);
			},
			// 加入购物车按钮
			addCart(selectShop){
				console.log("监听 - 加入购物车");
				that.addCartFn({
					selectShop : selectShop,
					success : function(res){
						// 实际业务时,请替换自己的加入购物车逻辑
						that.toast(res.msg);
					}
				});
			},
			// 立即购买
			buyNow(selectShop){
				console.log("监听 - 立即购买");
				that.addCartFn({
					selectShop : selectShop,
					success : function(res){
						// 实际业务时,请替换自己的立即购买逻辑
						that.toast("立即购买");
					}
				});
			},
			/**
			 * 获取商品信息
			 * 这里可以看到每次打开SKU都会去重新请求商品信息,为的是每次打开SKU组件可以实时看到剩余库存
			 */
			findGoodsInfo(obj){
				let { useCache } = obj;
				return new Promise(function (resolve, reject) {
					that.callFunction({
						useCache:useCache,
						success(data) {
							resolve(data.goodsInfo);
						}
					});
				});
			},
			toast(msg){
				uni.showToast({
				    title: msg,
						icon:"none"
				});
			},
			callFunction(obj){
				let { useCache, success } = obj;
				if(!useCache) {
					uni.showLoading({
						title: '请求中'
					});
				}
				uniCloud.callFunction({
					name: 'findGoodsInfo',
					data: { 
						goods_id : that.goods_id
					},
					success(res){
						console.log(res);
						if(typeof success == "function") success(res.result);
					},
					fail(err){
						console.error(err);
					},
					complete(){
						if(!useCache) uni.hideLoading();
					}
				});
			},
			// 参数配置开始 -----------------------------------------------------------
			goodsChange(e){
				that.goods_id = e.detail.value;
				that.sku_key = true;
			},
			showCloseChange(e){
				that.form.showClose = e.detail.value;
				that.sku_key = true;
			},
			maskCloseAbleChange(e){
				that.form.maskCloseAble = e.detail.value;
				that.sku_key = true;
			},
			skuModeChange(e){
				that.form.skuMode = e.detail.value;
				that.sku_key = true;
			},
			themeChange(e){
				that.form.theme = e.detail.value;
				that.sku_key = true;
			},
			stepStrictlyChange(e){
				that.form.stepStrictly = e.detail.value;
				that.sku_key = true;
			},
			hideStockChange(e){
				that.form.hideStock = e.detail.value;
				that.sku_key = true;
			},
			// 参数配置结束 -----------------------------------------------------------
		}
	}
</script>

<style lang="scss" scoped>
	.app {
		padding: 30rpx;
		font-size: 28rpx;
	}
	.form-item{
		display: flex;
	}
	.form-item .title, {
		padding: 20rpx 0;
		width:360rpx
	}
	.form-item .input-view {
		display: flex;
		align-items: center;
	}
	.form-item .input {
		margin-left: 40rpx;
		border: 1px solid #d6d6d6;
		border-radius: 10rpx;
		padding: 8rpx 30rpx;
		font-size: 28rpx;
	}
	.radio{
		padding: 6rpx 0rpx;
	}
	.config-title {
		text-align: center;
		font-size: 32rpx;
		font-weight: bold;
		margin-bottom: 40rpx;
		margin-top: 40rpx;
		padding-bottom: 10rpx;
	}
	
</style>
