<template>
  <div style="margin-top: 10px">1{{ a }}</div>
</template>
<script setup>
let a = ref(1);
test();

function test() {
  console.log('test1');
  test2();
  console.log('test2');
}

function test2() {
  a.value = 2;
  let b = 2;
}
</script>
<style lang="scss" scoped></style>
