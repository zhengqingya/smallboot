<template>
  <div class="app bg-color-primary h100 w100 flex">
    <div class="bg-color-warning w-200">
      <h1>{{ $filters.sexName(1) }}</h1>
    </div>

    <div class="flex-1">
      <div class="bg-color-success font-size-100">123456789123456789</div>
      <span class="hello font-size-lg w-200 h-100 line-height-100 text-center bg-color-red display-block">hello</span>
      <p v-for="index in 10" :key="index" class="font-size-30">{{ index }}</p>
      <div class="test m-l-300"></div>
    </div>
  </div>
</template>

<script setup>
import { getCurrentInstance, toRefs } from 'vue';
const { proxy } = getCurrentInstance();

let useUserStore = proxy.$store.user.useUserStore();
let { login } = useUserStore;
let { userObj } = toRefs(useUserStore);

async function handleClick() {
  proxy.submitOk('保存成功');
  proxy.submitFail('操作失败');
}
</script>
<style lang="scss" scoped>
.app {
  .hello {
    border: 10px solid #000;
    box-shadow: 10px 10px 5px rgba(0, 0, 0, 0.2);
  }
  .test {
    width: 0;
    height: 0;

    // 方式一：
    // border-top: 100px solid transparent;
    // border-right: 50px solid rgb(43, 241, 17);
    // border-bottom: 0 solid rgb(6, 30, 247);
    // border-left: 0 solid rgb(203, 236, 11);

    // 方式二：
    border-color: transparent rgb(43, 241, 17) transparent transparent;
    border-style: solid;
    border-width: 100px 50px 0 0;
  }
}
</style>
