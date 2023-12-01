<template>
  <el-form-item :label="`${label}:`" :prop="prop">
    <slot />
  </el-form-item>
</template>
<script setup>
const props = defineProps({
  label: { type: String, default: '' },
  prop: { type: String, default: '' },
});
</script>
<style lang="scss" scoped>
.el-form-item {
  margin-bottom: 6px;
}

::v-deep(.el-form-item__label) {
  font-weight: 600;
}
</style>
