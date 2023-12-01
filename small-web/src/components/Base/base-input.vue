<template>
  <div style="display: inline-block; white-space: nowrap" :class="{ 'base-header-gap': label }">
    <span v-if="label" class="label">{{ label }} &nbsp;</span>
    <el-input :placeholder="`请输入${label}`" style="width: 200px; margin-right: 10px" clearable v-bind="$attrs" />
  </div>
</template>
<script setup>
const props = defineProps({
  label: { type: String, default: '' },
  prop: { type: String, default: '' },
});
</script>
<style lang="scss" scoped>
.label {
  font-size: var(--el-form-label-font-size);
  color: var(--el-text-color-regular);
  font-weight: 600;
}
</style>
