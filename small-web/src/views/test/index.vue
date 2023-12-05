<template>
  <div style="margin-top: 100px">
    <codemirror v-model="code" basic :extensions="[java(), oneDark]" />
  </div>
</template>
<script setup>
const { proxy } = getCurrentInstance();
import codemirror from 'vue-codemirror6';
import { java } from '@codemirror/lang-java';
import { oneDark } from '@codemirror/theme-one-dark';

let code = `public class Main {
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}`;

onMounted(() => {});
</script>
<style lang="scss" scoped>
.codemirror,
.vue-codemirror {
  height: 100px;
  background: red;
}
</style>
