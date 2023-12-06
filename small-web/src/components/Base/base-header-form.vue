<template>
  <div class="app" :class="{ 'base-header-gap': label }">
    <span v-if="label" class="label">{{ label }} &nbsp;</span>
    <slot />
  </div>
</template>
<script setup>
const props = defineProps({
  label: { type: String, default: '' },
});
</script>
<style lang="scss" scoped>
.app {
  display: inline-block;
  white-space: nowrap; // 文本不会换行，文本会在在同一行上继续，直到遇到 <br> 标签为止。
  margin-right: 10px;
}
.label {
  font-size: var(--el-form-label-font-size);
  color: var(--el-text-color-regular);
  font-weight: 600;
}
</style>
