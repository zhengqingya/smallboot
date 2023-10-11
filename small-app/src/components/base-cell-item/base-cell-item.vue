<template>
  <view class="flex-start-center font-size-sm" style="margin-top: 10rpx">
    <text>{{ label }}</text>
    <slot />
  </view>
</template>
<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  label: { type: String, default: '' },
});
</script>
<style lang="scss" scoped></style>
