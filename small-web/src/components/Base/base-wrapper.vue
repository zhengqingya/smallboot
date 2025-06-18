<template>
  <div class="base-wrapper-box">
    <div class="content">
      <slot />
    </div>
  </div>
</template>
<script setup></script>
<style lang="scss" scoped>
.base-wrapper-box {
  min-height: calc(100vh - 50px - 35px); // 满屏 - navbar - tabs  fixme:临时高度

  height: 100%;
  width: 100%;
  padding: 10px;
  background-color: rgba(243, 242, 242, 0.555);
  display: grid;
  grid-template-rows: 1fr;

  .content {
    height: 100%;
    width: 100%;
    background-color: #fff;
    padding: 16px;
    border-radius: 6px;
  }
}
</style>
