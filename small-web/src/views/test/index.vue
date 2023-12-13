<template>
  <div style="margin-top: 10px">{{ test }}</div>
</template>
<script setup lang="ts">
// import { onMounted, getCurrentInstance } from 'vue';

const { proxy } = getCurrentInstance();
let test = $ref('hello world');
onMounted(() => {
  let num: String = 'xx';
  // console.log('num', num);
});
</script>
<style lang="scss" scoped></style>
