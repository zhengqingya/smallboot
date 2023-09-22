<template>
  <view class="app flex-start-center p-30 m-b-2" style="height: 80rpx">
    <navigator :url="'/subPackages/product/shop'">
      <text class="font-bold font-size-base">天府三街测试门店: {{ chooseRegionData.name }}</text>
    </navigator>
  </view>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let { chooseRegionData } = toRefs(proxy.$store.system.useSystemStore());
const props = defineProps({
  modelValue: { type: Array, required: false, default: () => [] },
});

defineExpose({ init });

async function init() {}
</script>

<style lang="scss" scoped>
.app {
  box-shadow: 1rpx 0 5rpx rgba(0, 0, 0, 0.2);
}
</style>
