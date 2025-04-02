<template>
  <el-button style="color: #fff" v-bind="$attrs" type="primary"> <slot /></el-button>
</template>
<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  // label: { type: String, default: '' },
});
</script>
<style lang="scss" scoped></style>
