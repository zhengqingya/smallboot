<template>
  <el-tag v-if="dataType == 'status'" v-bind="$attrs" :type="modelValue == 1 ? 'success' : 'info'">
    {{ modelValue === 1 ? '正常' : '停用' }}
  </el-tag>
  <el-tag v-else-if="dataType == 'yes'" v-bind="$attrs" :type="modelValue ? 'primary' : 'info'">
    {{ modelValue ? '是' : '否' }}
  </el-tag>
  <el-tag v-else> {{ modelValue }} </el-tag>
</template>
<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  modelValue: { type: null, default: null },
  dataType: { type: String, default: 'status' },
});
</script>
<style lang="scss" scoped></style>
