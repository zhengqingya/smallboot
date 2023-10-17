<template>
  <el-radio-group v-bind="$attrs">
    <div v-if="dataType == 'status'">
      <el-radio :label="1">正常</el-radio>
      <el-radio :label="0">停用</el-radio>
    </div>
    <div v-if="dataType == 'yes'">
      <el-radio :label="true">是</el-radio>
      <el-radio :label="false">否</el-radio>
    </div>
  </el-radio-group>
</template>
<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  dataType: { type: String, default: 'status' },
});
</script>
<style lang="scss" scoped></style>
