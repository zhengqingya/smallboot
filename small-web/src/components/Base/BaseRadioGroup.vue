<template>
  <el-radio-group v-bind="$attrs">
    <el-radio :label="1">正常</el-radio>
    <el-radio :label="0">停用</el-radio>
  </el-radio-group>
</template>
<script setup>
const { proxy } = getCurrentInstance();
</script>
<style lang="scss" scoped></style>
