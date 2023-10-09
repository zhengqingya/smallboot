<template>
  <div class="flex-between-center">
    <el-tree
      ref="menuTreeRef"
      class="overflow-y-auto overflow-x-hidden"
      :data="menuTree"
      :props="{
        children: 'children',
        label: 'title',
      }"
      show-checkbox
      default-expand-all
      check-strictly
      :default-checked-keys="defaultCheckedKeys"
      node-key="menuId"
      highlight-current
      @node-click="menuNodeClick"
      @check-change="changeCheckMenu" />
    <div v-if="props.roleId" class="flex-1 m-l-20">
      <base-no-data v-if="!currentSelectedMenu">tips:点击左侧菜单可查看按钮权限</base-no-data>
      <base-no-data v-else-if="currentSelectedMenu.permList.length === 0">该菜单暂未分配按钮权限</base-no-data>
      <el-card v-else>
        <template #header>
          <div class="flex-between-center">
            <h3>编辑页面按钮权限</h3>
            <el-button link @click="savePermIds">保存</el-button>
          </div>
        </template>
        <el-checkbox v-model="isCheckAllPerm" @change="changeCheckBoxPerm">全选</el-checkbox>
        <el-checkbox-group v-model="currentSelectedPermIdList" @change="changeCheckedPerm">
          <el-checkbox v-for="(item, index) in currentSelectedMenu.permList" :key="index" :label="item.id">{{ item.name }} </el-checkbox>
        </el-checkbox-group>
      </el-card>
    </div>
  </div>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let menuTree = $ref([]); // 菜单树
let defaultCheckedKeys = $ref([]); //  树菜单默认选中的数据
let currentSelectedMenu = $ref(null); // 当前选中的菜单
let currentSelectedPermIdList = $ref([]); // 当前选中的按钮/url权限
let isCheckAllPerm = $ref(false); //  是否选中所有权限
let isInitCount = $ref(0);

const props = defineProps({
  modelValue: { type: Object, required: false, default: () => {} },
  roleId: { type: String, default: null },
});

onUpdated(() => {});

watch(
  () => props.modelValue,
  (newValue) => {
    // 防止初始化的时候，监听器执行两次
    if (isInitCount > 2) {
      return;
    }
    isInitCount++;
    // console.log('监听器执行了... ', newValue[0]);
    menuTree = newValue;
    defaultCheckedKeys = getCheckedKeys([], menuTree);
  },
  {
    immediate: true,
    deep: true,
  },
);

// 拿到选中的菜单ids
function getCheckedKeys(checkedKeys, menuTree) {
  menuTree.forEach((item) => {
    if (item.isHasPerm) {
      checkedKeys.push(item.menuId);
    }
    if (item.children.length > 0) {
      getCheckedKeys(checkedKeys, item.children);
    }
  });
  return checkedKeys;
}

// 点击菜单
function menuNodeClick(data) {
  currentSelectedMenu = data;
  let selectedPerm = currentSelectedMenu.permList.filter((e) => e.isHasPerm);
  isCheckAllPerm = currentSelectedMenu.permList.length === selectedPerm.length;
  currentSelectedPermIdList = selectedPerm.map((item) => item.id);
}

/**
 * 监听菜单选中
 * @param nodeData 节点数据
 * @param isChecked 是否被选中
 */
function changeCheckMenu(nodeData, isChecked) {
  nodeData.isHasPerm = isChecked;
  // console.log('111', nodeData, isChecked);
}

// 监听按钮权限 -- 全选
function changeCheckBoxPerm(isAllCheck) {
  currentSelectedPermIdList = isAllCheck ? currentSelectedMenu.permList.map((item) => item.id) : [];
  currentSelectedMenu.permList.forEach((item) => {
    // 按钮权限
    item.isHasPerm = isAllCheck;
  });
}
// 监听按钮权限 -- 改变
function changeCheckedPerm(selectedData) {
  isCheckAllPerm = selectedData.length === currentSelectedMenu.permList.length;
  currentSelectedMenu.permList.forEach((item) => {
    // 按钮权限
    item.isHasPerm = selectedData.includes(item.id);
  });
}

// 保存按钮权限
async function savePermIds() {
  if (!props.roleId) {
    proxy.submitFail('角色ID丢失！');
    return;
  }
  let res = await proxy.$api.sys_perm.saveRoleRePermIds({
    roleId: props.roleId,
    menuId: currentSelectedMenu.menuId,
    permissionIdList: currentSelectedPermIdList,
  });
  proxy.submitOk(res.msg);
}
</script>
<style lang="scss" scoped></style>
