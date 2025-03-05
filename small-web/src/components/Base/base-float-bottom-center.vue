<template>
  <div class="opt-container">
    <div class="opt-content">
      <div class="flex-center-center">
        <slot />
      </div>
    </div>
  </div>
</template>
<script setup>
const props = defineProps({
  float: { type: String, default: 'bottom' },
});
</script>
<style lang="scss" scoped>
.opt-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: flex-end; /* 垂直底部对齐 */
  .opt-content {
    position: fixed; /* 固定在视口中 */
    bottom: 20px; /* 距离底部的距离 */
    left: 50%; /* 水平居中 */
    transform: translateX(-50%); /* 偏移自身宽度的一半 */
    background-color: #fff;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    z-index: 1000; /* 确保内容在其他元素之上 */
  }
}
</style>
