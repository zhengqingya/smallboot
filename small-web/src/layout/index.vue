<template>
  <!-- <h1>{{ route.meta }}</h1> -->
  <div v-show="isLogin && !$route.meta.isParentView" class="flex h100 w100">
    <!-- 侧边栏菜单 -->
    <sidebar class="w-200" />
    <div class="flex-1">
      <div id="top">
        <!-- 顶部导航栏 -->
        <navbar class="h-50" />
        <!-- Tabs标签页 -->
        <tabs-view />
      </div>
      <!-- 主页面 -->
      <app-main :style="{ height: appMainHeight + 'px' }" class="app-main m-t-5" />
    </div>
  </div>
  <div v-if="!isLogin || (isLogin && $route.meta.isParentView)" class="h100">
    <router-view />
  </div>
</template>

<script setup>
import sidebar from './components/sidebar.vue';
import navbar from './components/navbar.vue';
import appMain from './components/app-main.vue';
import tabsView from './components/tabs-view.vue';
const { proxy } = getCurrentInstance();
let { isLogin } = toRefs(proxy.$store.user.useUserStore());
let appMainHeight = ref(0);

onMounted(() => {
  calAppMainHeight();

  // 窗口宽高变化时触发 -- tips：window.onresize只能在项目内触发1次
  window.onresize = function windowResize() {
    calAppMainHeight(window.innerHeight);
  };
});

function calAppMainHeight(h) {
  let windowHeight = h || window.innerHeight;
  let top = document.getElementById('top');
  if (top) {
    appMainHeight.value = windowHeight - top.offsetHeight;
  }
}
</script>
<style lang="scss" scoped>
.app-main {
  // height: calc(100vh - 50px); // 满屏 - navbar
}
</style>
