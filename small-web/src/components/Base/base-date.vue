<template>
  <el-form-item :label="label">
    <el-date-picker format="YYYY-MM-DD HH:mm:ss" value-format="YYYY-MM-DD HH:mm:ss" :placeholder="`请选择${label}`" style="width: 200px; margin-right: 10px" clearable v-bind="$attrs" />
  </el-form-item>
</template>
<script setup>
const props = defineProps({
  label: { type: String, default: '' },
});
</script>
<style lang="scss" scoped>
.label {
  font-size: var(--el-form-label-font-size);
  color: var(--el-text-color-regular);
}
</style>
