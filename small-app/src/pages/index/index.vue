<template>
  <base-wrapper activeTabName="index">
    <u-swiper
      :list="mall_index_slide_img_list.map((item) => item.url)"
      indicator
      indicatorMode="line"
      circular
      height="550rpx" />

    <view class="introText h-full flex-center-center">
      <text>Vue3+Vite4 小程序</text>
    </view>

    <!-- <view @click="test" style="height: 300rpx" class="bg-color-red">test</view> -->
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let { mall_index_slide_img_list } = toRefs(proxy.$store.system.useSystemStore());
const bannerList = ref([]);

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
