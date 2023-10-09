<template>
  <el-tag v-bind="$attrs" :type="modelValue === 1 ? 'success' : 'info'">
    {{ modelValue === 1 ? '正常' : '停用' }}
  </el-tag>
</template>
<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  modelValue: { type: Number, default: 1 },
});
</script>
<style lang="scss" scoped></style>
