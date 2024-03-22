<template>
  <el-card>
    <div class="header">
      <div class="flex-between-center">
        <div v-if="isCustomLeft" style="width: 100%"><slot name="left" /></div>
        <div class="font-bold font-size-base" style="width: 100%" v-else>{{ title }}</div>

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
  title: { type: String, default: '标题' },
  isCustomLeft: { type: Boolean, default: false },
});
</script>
<style lang="scss" scoped>
.line {
  border-bottom: 1px solid #e5e7ee;
  margin: 10px 0;
}
</style>
