<template>
  <base-wrapper activeTabName="product">
    <!-- 店铺 -->
    <view class="shop flex-between-center p-10 m-b-2" v-if="chooseShop">
      <view class="flex-c-center-start">
        <navigator
          hover-class="none"
          :url="'/subPackages/product/shop'"
          class="flex-start-center"
          style="height: 40rpx">
          <text class="font-bold font-size-base">{{ chooseShop.shopName }}</text>
          <u-icon name="arrow-right" color="#999" size="16"></u-icon>
        </navigator>
        <view class="m-t-10 font-size-sm text-color-grey">
          该门店距离您{{ $filters.calDistance(chooseShop.distance) }}
        </view>
      </view>
      <view class="font-size-base">堂食</view>
    </view>

    <!-- 分类关联商品数据 -->
    <base-linkage
      class="h-full overflow-y-scroll"
      ref="linkageRef"
      categoryClass="p-r-10 bg-color-lightgrey text-color-grey"
      category-width="25%">
      <template #empty>
        <u-empty mode="data" text="商品为空" />
      </template>
      <template #category="{ data, isActive }">
        <view
          class="category-item flex-center-center position-relative font-size-base"
          :class="{ active: isActive }"
          style="height: 90rpx">
          <text class="text-overflow-1">{{ data.name }}</text>
          <!-- <up-badge :value="getCategoryCartNum(item.id)"  max="99" :offset="[-8, -20]" :absolute="true" /> -->
        </view>
      </template>
      <template #categoryReList="{ data }">
        <view style="height: 30rpx" class="flex-start-center font-size-base font-bold">
          {{ data.name }}
        </view>
        <view>
          <view
            class="flex-center-center m-10"
            style="height: 160rpx"
            v-for="(spuItem, index) in data.spuList"
            :key="index"
            @tap="showSpuDetailModal(spuItem)">
            <image class="img-base" :src="spuItem.coverImg" />
            <view class="flex-1 flex-c-between-start h-full p-10">
              <text class="font-size-lg text-overflow-1">{{ spuItem.name }}</text>

              <text class="font-size-base text-color-grey">库存：{{ spuItem.usableStockSum }}</text>
              <view class="w-full flex-between-center">
                <text class="font-size-base font-bold">
                  ￥{{ spuItem.skuList[0].sellPrice / 100 }}
                </text>
                <view class="flex-between-center position-relative">
                  <u-icon name="plus" />
                  <!-- <up-badge :value="getSkuNum(spuItem)" max="99" :inverted="true" :offset="[-15, -10]" :absolute="true" /> -->
                </view>
              </view>
            </view>
          </view>
        </view>
      </template>
    </base-linkage>

    <!--  商品sku详情  -->
    <sku ref="skuRef" @close="handleCloseSkuChoose" />

    <!-- 购物车 -->
    <cart ref="cartRef" v-model="cartList" />
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
import { onLoad } from '@dcloudio/uni-app';
import cart from './component/cart.vue';
import sku from './component/sku.vue';
let { chooseRegionData, chooseShop } = toRefs(proxy.$store.system.useSystemStore());
let { userGeoObj } = toRefs(proxy.$store.user.useUserStore());

// const orderType = ref('takein'); // 堂食：takein  外卖：takeout
let reSpuList = ref([]); // 分类关联的商品列表数据
let spu = ref({}); // 当前选择的商品
let cateScrollTopList = ref([]); // 左侧分类关联右边商品滑动的顶部位置
let categoryScrollTop = ref(0); // 竖向滚动条位置
let cartList = ref([]); // 购物车数据

onMounted(() => {
  // #ifdef MP-WEIXIN
  initLocation();
  // #endif

  // #ifndef MP-WEIXIN
  if (!chooseShop.value || !chooseShop.value.shopId) {
    uni.redirectTo({ url: '/subPackages/product/region' });
  }
  init();
  // #endif
});

// watch：监听器
watch(chooseShop, (newValue, oldValue) => {
  if (newValue) {
    init();
  }
});

async function init() {
  let res = await proxy.$api.category.reSpuList();
  reSpuList.value = res.data;
  showCart();
  proxy.$refs.linkageRef.init(reSpuList.value);
}

function initLocation() {
  if (chooseShop.value && chooseShop.value.shopId) {
    init();
    return;
  }
  uni.getLocation({
    type: 'gcj02',
    isHighAccuracy: true, // 开启高精度定位
    success: async function (res) {
      userGeoObj.value = res;
      let apiRes = await proxy.$api.shop.lately({
        longitude: res.longitude,
        latitude: res.latitude,
      });
      chooseShop.value = apiRes.data;
      if (!chooseShop.value) {
        uni.redirectTo({ url: '/subPackages/product/region' });
      }
    },
    fail: function (err) {
      console.log('product定位获取异常：', err);
    },
  });
}

// 购物车
async function showCart() {
  // 延时500毫秒，防止数据库未及时更新数据
  setTimeout(() => {
    if (proxy.$refs.cartRef) {
      proxy.$refs.cartRef.init();
    }
  }, 500);
}
// 商品在购物车中的数量
function getSkuNum(spuItem) {
  return cartList.value
    .filter((e) => e.spuId === spuItem.id)
    .reduce((total, item) => (total += item.num), 0);
}
// 指定分类下所有商品在购物车中的数量
function getCategoryCartNum(categoryId) {
  let sum = 0;
  reSpuList.value.forEach((e) => {
    if (e.id === categoryId) {
      let spuIdList = e.spuList.map((item) => item.id);
      sum = cartList.value
        .filter((e) => spuIdList.includes(e.spuId))
        .reduce((total, item) => (total += item.num), 0);
      return;
    }
  });
  return sum;
}
// 选规格-商品详情
async function showSpuDetailModal(spu) {
  let res = await proxy.$api.spu.detail(spu.id);
  spu.value = res.data;
  spu.value.num = 1;
  await proxy.$refs.skuRef.show(spu.value);
}
// 关闭sku选择时触发
function handleCloseSkuChoose() {
  showCart();
}
</script>

<style lang="scss" scoped>
.shop {
  box-shadow: 1rpx 0 5rpx rgba(0, 0, 0, 0.2);
}
.category-item {
  border-left: 10rpx solid transparent;
  &.active {
    border-left: 10rpx solid #3c9cff;
    background-color: #fff;
    color: #000;
    font-weight: bold;
  }
}
</style>
