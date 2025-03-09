<template>
  <div style="padding: 0px">
    <div class="app">
      <slot />
    </div>
  </div>
</template>
<script setup></script>
<style lang="scss" scoped>
.app {
  height: 100%;
  width: 100%;
  // padding: 12px 10px;
  // box-shadow: 1px 1px 3px 1px rgba(0, 0, 0, 0.1);
  // border-radius: 5px;
  background-color: white;
  overflow: hidden;
}
</style>
