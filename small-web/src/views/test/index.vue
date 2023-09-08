<template>
  <div>
    <div class="text-color-primary w-full h-full text-100px p-100 h-full b-rd-6 text-center overflow-y-auto font-bold font-size-sm border-b-red" bg="blue" font="mono light">hello</div>
  </div>
</template>

<script setup>
import { getCurrentInstance, toRefs } from 'vue';
const { proxy } = getCurrentInstance();
let useUserStore = proxy.$store.user.useUserStore();
let { login } = useUserStore;
let { userObj } = toRefs(useUserStore);
</script>
<style lang="scss" scoped>
.app {
}
</style>
