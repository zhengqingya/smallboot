<template>
  <el-card class="app">
    <div class="flex-start-center">
      <div class="flex-1">
        <slot />
      </div>
      <slot name="right"></slot>
    </div>
  </el-card>
</template>
<script setup></script>
<style lang="scss" scoped>
.app {
  margin: 10px 0px;
  border-radius: 5px;
  // padding: 10px 5px;
}
</style>
