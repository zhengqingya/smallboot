<template>
  <div
    v-if="isShowMenu"
    class="app"
    :style="{
      'background-color': '#fff',
    }">
    <div v-if="menuType === 1" class="h-full">
      <el-menu
        class="h-full"
        style="padding-top: 16px"
        background-color="#fff"
        text-color="#666666"
        active-text-color="#fff"
        router
        :default-active="$route.meta.fullPath"
        :collapse="false"
        :unique-opened="true"
        :default-openeds="['/xxx']"
        @select="handleSelect">
        <el-scrollbar>
          <div style="font-size: 20px; height: 50px; font-weight: bold" class="flex-center-center text-color-primary">
            <span class="text-center text-overflow-1">{{ userObj.tenantName }}</span>
          </div>
          <sidebar-item :router-list="routerList" />
        </el-scrollbar>
      </el-menu>
    </div>
    <div v-else class="p-x-20 p-y-10">
      <div v-for="item in routerList.filter((e) => e.meta.isShow)" :key="item.path">
        <div class="font-bold cursor-pointer" @click="handleSelect(item.meta.fullPath)">
          <el-icon v-if="item.meta && item.meta.icon" size="12"><component :is="item.meta.icon" /></el-icon>
          {{ item.meta.title }}
        </div>
        <div class="grid-start-center-2 m-t-10 m-b-10">
          <div v-for="secondItem in item.children.filter((e) => e.meta.isShow)" :key="secondItem.path" class="text-color-grey" style="height: 25px">
            <div
              class="cursor-pointer"
              :class="{
                'text-color-primary': $route.meta.fullPath === secondItem.meta.fullPath,
              }"
              @click="handleSelect(secondItem.meta.fullPath)">
              {{ secondItem.meta.title }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import sidebarItem from './sidebar-item.vue';
import { getCurrentInstance, toRefs } from 'vue';
const { proxy } = getCurrentInstance();
let { userObj, routerList, routerMap } = toRefs(proxy.$store.user.useUserStore());
let { activeTabs } = proxy.$store.settings.useSettingsStore();
let { menuType } = toRefs(proxy.$store.settings.useSettingsStore());
let { isShowMenu } = toRefs(proxy.$store.settings.useSettingsStore());

/**
 * 选中菜单时触发
 * @param index 选中菜单项的 index  eg: /system/role （router 以 index 作为 path 进行路由跳转，或 router 属性直接跳转）
 * @param indexPath 选中菜单项的 index path eg: ['/system', '/system/role']
 * @param item 选中菜单项
 * @param routeResult vue-router 的返回值（如果 router 为 true）
 */
function handleSelect(index, indexPath, item, routeResult) {
  // console.log(index, indexPath, item, routeResult);
  let router = routerMap.value[index];
  if (router.children.filter((e) => e.type == 1).length > 0) {
    return;
  }
  if (menuType.value === 2) {
    proxy.$router.push(index);
  }

  activeTabs(router);
}
</script>

<style lang="scss" scoped>
.app {
  min-width: 200px;
  box-shadow: 1px 0px 4px 0px rgba(21, 34, 50, 0.08);
  overflow-x: hidden;
  overflow-y: hidden;
}

@mixin menu-box {
  height: 44px;
  border-radius: 4px;
}

::v-deep(.el-menu) {
  // 一级菜单和二级菜单 -- 背景颜色
  .el-menu-item,
  .el-sub-menu__title {
    @include menu-box;
    &:hover {
      background: #f4f7ff;
    }
  }

  // 当前被选中的菜单
  .el-menu-item.is-active {
    @include menu-box;
    color: #fff !important;
    background: #1e5eff !important;
  }

  // 选中时主菜单样式
  .is-active > .el-sub-menu__title {
    color: #1e5eff;
  }
}
</style>
