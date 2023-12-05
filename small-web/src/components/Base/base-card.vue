<template>
  <el-card class="app">
    <div class="header">
      <div class="flex-between-center">
        <span class="font-bold font-size-base">{{ title }}</span>
        <div><slot name="append" /></div>
      </div>
    </div>
    <div class="line"></div>
    <div class="content">
      <slot />
    </div>
  </el-card>
</template>
<script setup>
defineProps({
  title: {
    type: String,
    default: '标题',
  },
});
</script>
<style lang="scss" scoped>
.app {
  margin: 10px;
}
.line {
  border-bottom: 1px solid #e5e7ee;
  margin: 10px 0;
}
</style>
