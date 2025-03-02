<template>
  <el-table highlight-current-row border v-bind="$attrs" :header-cell-style="{ background: 'rgba(243, 242, 242, 0.555)', color: '#000' }">
    <slot />
  </el-table>
</template>
<script setup>
const { proxy } = getCurrentInstance();
</script>
<style lang="scss" scoped></style>
