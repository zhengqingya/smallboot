<template>
  <!-- {{ route.meta }} -->
  <div class="app flex-between-center" style="padding: 0px 10px">
    <div class="flex-center-center">
      <div style="cursor: pointer; margin-right: 10px" @click="proxy.$store.settings.useSettingsStore().update">
        <el-icon :size="22">
          <component :is="proxy.$store.settings.useSettingsStore().isShowMenu ? 'Fold' : 'Expand'" />
        </el-icon>
      </div>

      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item :to="{ path: '/' }">home</el-breadcrumb-item>
        <el-breadcrumb-item v-for="item in route.meta.breadcrumbItemList" :key="item">
          <span class="text-color-grey">{{ item }}</span>
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <wx-mp-account />

    <div class="flex">
      <choose-tenant style="margin-right: 20px" />
      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
        <div class="flex-center-center">
          <el-avatar class="" :size="32" :src="userObj.avatarUrl" />
          <div class="flex-center-center">
            <span style="margin-left: 6px"> {{ userObj.nickname }} </span>
            <el-icon :size="20" style="width: 20px">
              <ArrowDown />
            </el-icon>
          </div>
        </div>

        <template #dropdown>
          <el-dropdown-menu>
            <router-link to="/">
              <el-dropdown-item>首页</el-dropdown-item>
            </router-link>
            <router-link to="/system/personal-center">
              <el-dropdown-item>个人中心</el-dropdown-item>
            </router-link>
            <!-- <el-dropdown-item> <a target="_blank" href="https://gitee.com/zhengqingya"> Gitee项目 </a> </el-dropdown-item> -->
            <el-dropdown-item @click="handleSettings">系统设置</el-dropdown-item>
            <el-dropdown-item divided @click="logout">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <settings ref="settingsRef" />
  </div>
</template>
<script setup>
import { useRoute } from 'vue-router';
const route = useRoute();
import { ArrowRight } from '@element-plus/icons-vue';
import { getCurrentInstance, toRefs } from 'vue';
const { proxy } = getCurrentInstance();
import settings from './settings.vue';

let useUserStore = proxy.$store.user.useUserStore();
let { logout } = useUserStore;
let { userObj } = toRefs(useUserStore);

function handleSettings() {
  proxy.$refs.settingsRef.open();
}
</script>
<style lang="scss" scoped>
.app {
  // -webkit-box-shadow: 0 1px 4px rgba(0, 0, 0, 0.2);
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
}
</style>
