<template>
  <el-form-item>
    <el-input :placeholder="label ? `请输入${label}` : '请输入'" :prefix-icon="Search" style="width: 200px; margin-right: 0px" clearable v-bind="$attrs" />
  </el-form-item>
</template>
<script setup>
import { Search } from '@element-plus/icons-vue';
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
