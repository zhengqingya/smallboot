<template>
  <view class="h-full overflow-y-scroll">
    <view v-for="item in 10">
      <l-indexes-anchor :index="item">标题{{ item }}</l-indexes-anchor>
      <view class="cell">文本</view>
      <view class="cell">文本</view>
      <view class="cell">文本</view>
    </view>
  </view>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let indexList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
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

async function init() {
  // 购物车数据
  cartList.value = await proxy.$api.cart.list();
}
</script>

<style lang="scss" scoped></style>
