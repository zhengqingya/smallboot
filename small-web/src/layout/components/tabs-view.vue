<template>
  <div class="app">
    <el-scrollbar>
      <div class="flex w-100">
        <div v-for="item in tabsList" :key="item" class="item m-5" :class="{ active: $route.meta.fullPath === item.meta.fullPath }">
          <div class="flex-between-center p-x-2 w-85 h-20">
            <router-link class="w100" :to="item.meta.fullPath" @click="activeTabs(item)">
              <span class="m-r-3 text-overflow-1">{{ item.meta.title }}</span>
            </router-link>
            <el-icon :size="10" @click="handleClose(item)"> <Close /> </el-icon>
          </div>
        </div>
      </div>
    </el-scrollbar>
  </div>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let { tabsList } = toRefs(proxy.$store.settings.useSettingsStore());
let { activeTabs } = proxy.$store.settings.useSettingsStore();

function handleClose(item) {
  tabsList.value.splice(tabsList.value.indexOf(item), 1);
}
</script>
<style lang="scss" scoped>
.app {
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
  .item {
    border: 1px solid #ebeef5;
    &.active {
      background: $color-primary;
    }
  }
}

a {
  text-decoration: none; // 去掉下换线
  color: black; //文字颜色更改
}
</style>
