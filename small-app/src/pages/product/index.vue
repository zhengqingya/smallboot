<template>
  <base-wrapper activeTabName="product">
    <!-- 店铺 -->
    <!-- <shop ref="shopRef" /> -->

    <!-- 分类关联商品数据 -->
    <base-linkage
      class="h-full"
      :list="reSpuList"
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
            @tap="showSpuDetailModal(data, spuItem)">
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
import shop from './component/shop.vue';
import cart from './component/cart.vue';
import sku from './component/sku.vue';

const orderType = ref('takein'); // 堂食：takein  外卖：takeout
let reSpuList = ref([]); // 分类关联的商品列表数据
let spu = ref({}); // 当前选择的商品
let cateScrollTopList = ref([]); // 左侧分类关联右边商品滑动的顶部位置
let categoryScrollTop = ref(0); // 竖向滚动条位置
let cartList = ref([]); // 购物车数据

onMounted(() => {
  init();
});

async function init() {
  reSpuList.value = await proxy.$api.category.reSpuList();
  showCart();
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
async function showSpuDetailModal(item, spu) {
  spu.value = await proxy.$api.spu.detail(spu.id);
  spu.value.num = 1;
  await proxy.$refs.skuRef.show(spu.value);
}
// 关闭sku选择时触发
function handleCloseSkuChoose() {
  showCart();
}
</script>

<style lang="scss" scoped>
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
