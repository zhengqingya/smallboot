<template>
  <!-- <h1>{{ route.meta }}</h1> -->
  <div v-if="isLogin && !$route.meta.isParentView" class="app-box">
    <!-- 侧边栏菜单 -->
    <sidebar class="sidebar-box" />

    <div class="content-box">
      <div id="top">
        <!-- 顶部导航栏 -->
        <navbar style="height: 50px" />
        <!-- Tabs标签页 -->
        <tabs-view style="height: 35px" />
      </div>

      <!-- 主页面 -->
      <app-main class="main-box" />
    </div>
  </div>

  <div v-if="!isLogin || (isLogin && $route.meta.isParentView)" class="h-full">
    <app-main class="main-box" />
  </div>

  <im-chat v-if="isLogin" />
</template>

<script setup>
import sidebar from './components/sidebar.vue';
import navbar from './components/navbar.vue';
import appMain from './components/app-main.vue';
import tabsView from './components/tabs-view.vue';
const { proxy } = getCurrentInstance();
let { isLogin } = toRefs(proxy.$store.user.useUserStore());

onMounted(() => {
  // 窗口宽高变化时触发 -- tips：window.onresize只能在项目内触发1次
  window.onresize = function windowResize() {
    console.log('windowResize');
  };
});

// 注册一个回调函数，在组件因为响应式状态变更而更新其 DOM 树之后调用。
onUpdated(() => {
  console.log('onUpdated...');
});
</script>
<style lang="scss" scoped>
.app-box {
  height: 100%;
  width: 100%;
  display: flex;

  .sidebar-box {
    min-width: 200px;
  }

  .content-box {
    flex: 1;
    display: flex;
    flex-direction: column;

    min-width: 0px;
    height: 100%;

    .main-box {
      flex: 1;
      min-height: 0;
      // min-height: calc(100vh - 50px); // 满屏 - navbar
    }
  }
}
</style>
