<!-- tips: 不要用这个，直接用 base-table-p  -->
<template>
  <base-table-p ref="baseTableRef" v-bind="$attrs" :is-page="false"> <slot /></base-table-p>
</template>
<script setup>
const { proxy } = getCurrentInstance();
defineExpose({ refresh });
function refresh() {
  proxy.$refs.baseTableRef.refresh();
}
</script>
<style lang="scss" scoped></style>
