<template>
  <el-menu v-if="type === 1" class="menu" router :default-active="$route.meta.fullPath" :collapse="false" :unique-opened="false" @select="handleSelect">
    <el-scrollbar>
      <sidebar-item :router-list="routerList" />
    </el-scrollbar>
  </el-menu>
  <div v-else class="menu p-20">
    <div v-for="item in routerList" :key="item.path">
      <div v-if="item.meta.isShow">
        <h2 @click="handleSelect(item.meta.fullPath)">
          <el-icon v-if="item.meta && item.meta.icon"><component :is="item.meta.icon" /></el-icon> <span>{{ item.meta.title }}</span>
        </h2>

        <div v-for="secondItem in item.children" :key="secondItem.path">
          <div v-if="secondItem.meta.isShow" @click="handleSelect(secondItem.meta.fullPath)">
            <span>{{ secondItem.meta.title }}</span>
          </div>
        </div>
      </div>
    </div>

    <div class="grid-2 second-menu bg-color-red h-full" style="height: 500px">
      <div v-for="item in 20" :key="item" class="bg-color-primary">
        {{ item }}
      </div>
    </div>
  </div>
</template>

<script setup>
import sidebarItem from './sidebar-item.vue';
import { getCurrentInstance, toRefs } from 'vue';
const { proxy } = getCurrentInstance();
let { routerList, routerMap } = toRefs(proxy.$store.user.useUserStore());
let { activeTabs } = proxy.$store.settings.useSettingsStore();
let type = 1; // 1:element-plus菜单 2:自定义菜单

/**
 * 选中菜单时触发
 * @param index 选中菜单项的 index  eg: /system/role （router 以 index 作为 path 进行路由跳转，或 router 属性直接跳转）
 * @param indexPath 选中菜单项的 index path eg: ['/system', '/system/role']
 * @param item 选中菜单项
 * @param routeResult vue-router 的返回值（如果 router 为 true）
 */
function handleSelect(index, indexPath, item, routeResult) {
  // console.log(index, indexPath, item, routeResult);
  if (type === 2) {
    proxy.$router.push(index);
  }
  activeTabs(routerMap.value[index]);
}
</script>

<style lang="scss" scoped>
.menu {
  box-shadow: 1px 0 5px rgba(0, 0, 0, 0.2);
}
</style>
