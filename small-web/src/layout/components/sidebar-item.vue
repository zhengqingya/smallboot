<template>
  <div v-for="item in routerList" :key="item.path">
    <div v-if="item.meta.isShow && (item.children.filter((e) => e.type == 1).length == 0 || item.children.filter((e) => e.type == 1 && e.meta.isShow).length > 0)">
      <!-- 仅一级菜单 -->
      <el-menu-item v-if="item.meta.isShow && item.children.filter((e) => e.type == 1).length == 0" :index="item.meta.fullPath">
        <el-icon v-if="item.meta && item.meta.icon"><component :is="item.meta.icon" /></el-icon>
        <div v-else style="width: 30px"></div>
        <template #title>
          <span class="font-bold">{{ item.meta.title }}</span>
        </template>
      </el-menu-item>
      <!-- 含二级菜单 -->
      <div v-else>
        <el-sub-menu v-if="item.meta.isShow" :index="item.meta.fullPath">
          <template #title>
            <el-icon v-if="item.meta && item.meta.icon"><component :is="item.meta.icon" /></el-icon>
            <div v-else style="width: 30px"></div>
            <span class="font-bold">{{ item.meta.title }}</span>
          </template>
          <!-- 递归 -->
          <sidebarItem :router-list="item.children" />
        </el-sub-menu>
      </div>
    </div>
  </div>
</template>
<script setup>
defineProps({
  routerList: {
    type: Array,
    default: () => [],
  },
});
</script>
<style lang="scss" scoped></style>
