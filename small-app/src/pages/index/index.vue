<template>
  <base-wrapper activeTabName="index">
    <u-swiper :list="bannerList" indicator indicatorMode="line" circular height="550rpx" />

    <view class="introText h-full flex-center-center">
      <text>Vue3+Vite4 小程序</text>
    </view>

    <view class="h-full bg-color-primary" style="width: 600rpx">
      <navigator :url="'/subPackages/product/shop'">
        <view>进入店铺</view>
      </navigator>
    </view>
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
const bannerList = ref([
  'http://127.0.0.1:886/2023-09-11/1701130377848147968-美图35.jpg',
  'http://127.0.0.1:886/2023-09-11/1701131110123294720-美图13.png',
]);
</script>

<style lang="scss" scoped>
// @font-face {
//     font-family: 'iconfont';
//     src: url('https://test.ttf');
// }
// .introText {
//     font-family: iconfont;
// }
</style>
