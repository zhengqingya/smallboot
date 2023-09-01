<template>
  <div class="app">
    <el-scrollbar>
      <base-right-click class="flex w-100">
        <div v-for="item in tabsList" :key="item" class="item m-5" :class="{ active: $route.meta.fullPath === item.meta.fullPath }">
          <div class="flex-between-center p-x-2 w-85 h-20" @click.right="handleRightClick(item, $event)">
            <router-link class="w100" :to="item.meta.fullPath" @click="activeTabs(item)">
              <span class="m-r-3 text-overflow-1">{{ item.meta.title }}</span>
            </router-link>
            <el-icon :size="10" @click="handleClose(item)"> <Close /> </el-icon>
          </div>
        </div>

        <template #right-show="{ isShow }">
          <div v-show="isShow" class="right-menu flex-column border-radius-5 bg-color-white w-85">
            <div class="option" @click="handleCloseCurrent">
              <el-icon :size="10"> <Close /> </el-icon><span> 关闭当前</span>
            </div>
            <div class="option" @click="handleCloseAll">
              <el-icon :size="10"> <Close /> </el-icon><span> 关闭所有</span>
            </div>
          </div>
        </template>
      </base-right-click>
    </el-scrollbar>
  </div>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let { tabsList } = toRefs(proxy.$store.settings.useSettingsStore());
let { activeTabs } = proxy.$store.settings.useSettingsStore();
let chooseItem = $ref(null);

function handleClose(item) {
  tabsList.value.splice(tabsList.value.indexOf(item), 1);
}

function handleRightClick(item) {
  chooseItem = item;
}

function handleCloseCurrent() {
  handleClose(chooseItem);
}

function handleCloseAll() {
  tabsList.value = [];
}
</script>
<style lang="scss" scoped>
.app {
  position: relative;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.2);
  .item {
    border: 1px solid #ebeef5;
    &.active {
      background: $color-primary;
    }
  }

  .right-menu {
    .option {
      text-align: center;
      padding: 5px 10px;
      cursor: pointer;
      &:hover {
        background: #eee;
      }
    }
  }
}

a {
  text-decoration: none; // 去掉下换线
  color: black; //文字颜色更改
}
</style>
