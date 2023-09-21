<template>
  <view class="app flex-start-center p-30 m-b-2" style="height: 80rpx">
    <text class="font-bold font-size-base">天府三街测试门店 ></text>
  </view>
</template>

<script setup>
const { proxy } = getCurrentInstance();

const props = defineProps({
  modelValue: {
    type: Array,
    required: false,
    default: () => [],
  },
});

// 暴露方法
defineExpose({
  init,
});

async function init() {}
</script>

<style lang="scss" scoped>
.app {
  box-shadow: 1rpx 0 5rpx rgba(0, 0, 0, 0.2);
}
</style>
