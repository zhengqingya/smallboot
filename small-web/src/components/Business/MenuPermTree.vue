<template>
  <div class="flex-between-center">
    <el-tree
      ref="menuTreeRef"
      class="overflow-y-auto overflow-x-hidden"
      :data="menuTree"
      :props="{
        children: 'children',
        label: 'name',
      }"
      show-checkbox
      default-expand-all
      check-strictly
      :default-checked-keys="defaultCheckedKeys"
      node-key="id"
      highlight-current
      @node-click="menuNodeClick"
      @check-change="changeCheckMenu" />
  </div>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let menuTree = $ref([]); // 菜单树
let defaultCheckedKeys = $ref([]); //  树菜单默认选中的数据
let currentSelectedMenu = $ref(null); // 当前选中的菜单
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
      checkedKeys.push(item.id);
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
</script>
<style lang="scss" scoped></style>
