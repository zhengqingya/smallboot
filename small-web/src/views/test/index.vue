<template>
  <base-Editor v-model="valueHtml" />
</template>

<script setup>
const { proxy } = getCurrentInstance();
let valueHtml = ref('<p>hello</p>');
</script>
<style lang="scss" scoped></style>
