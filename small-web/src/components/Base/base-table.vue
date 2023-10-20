<template>
  <el-table border stripe :header-cell-style="{ background: 'rgba(243, 242, 242, 0.555)', color: '#000' }" v-bind="$attrs">
    <slot />
  </el-table>
</template>
<script setup>
const { proxy } = getCurrentInstance();
</script>
<style lang="scss" scoped></style>
