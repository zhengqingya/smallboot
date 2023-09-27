<template>
  <base-wrapper activeTabName="index">
    <u-swiper :list="bannerList" indicator indicatorMode="line" circular height="550rpx" />

    <view class="introText h-full flex-center-center">
      <text>Vue3+Vite4 小程序</text>
    </view>

    <!-- <view @click="test" style="height: 300rpx" class="bg-color-red">test</view> -->
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
const bannerList = ref([
  'http://127.0.0.1:886/2023-09-11/1701130377848147968-美图35.jpg',
  'http://127.0.0.1:886/2023-09-11/1701131110123294720-美图13.png',
]);

function test() {
  uni.getLocation({
    type: 'gcj02', // 返回可以用于uni.openLocation的经纬度
    isHighAccuracy: true, // 开启高精度定位
    success: function (res) {
      console.log('uni.getLocation', res);
      const latitude = res.latitude;
      const longitude = res.longitude;

      proxy.$qqmap.reverseGeocoder(res);
    },
    fail: function (err) {
      console.log('异常：', err);
    },
  });
}
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
