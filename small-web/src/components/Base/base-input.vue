<template>
  <base-header-form :label="label">
    <el-input :placeholder="`请输入${label}`" style="width: 200px; margin-right: 10px" clearable v-bind="$attrs" />
  </base-header-form>
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
