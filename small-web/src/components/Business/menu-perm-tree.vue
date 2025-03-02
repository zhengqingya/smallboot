<template>
  <div class="flex-between-center h-full w-full">
    <el-scrollbar style="width: 100%">
      <!-- check-strictly=false 父子不关联，如果关联的话会导致父有可能选不中，可以在后端做数据补充父菜单处理 -->
      <el-tree
        ref="menuTreeRef"
        style="min-width: 300px"
        :data="menuTree"
        :props="{
          children: 'children',
          label: 'name',
        }"
        show-checkbox
        default-expand-all
        :check-strictly="true"
        :default-checked-keys="defaultCheckedKeys"
        node-key="id"
        highlight-current
        @node-click="menuNodeClick"
        @check-change="changeCheckMenu" />
    </el-scrollbar>
  </div>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let menuTree = $ref([]); // 菜单树
let defaultCheckedKeys = $ref([]); //  树菜单默认选中的数据
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
    console.log('监听器执行了... ', newValue[0]);
    menuTree = newValue;
    defaultCheckedKeys = recurveGetCheckedKeys([], menuTree);
  },
  {
    immediate: true,
    deep: true,
  },
);

defineExpose({ update, getCheckedKeys });

function update() {
  console.log('更新菜单树数据...');
  defaultCheckedKeys = recurveGetCheckedKeys([], props.modelValue);
}

// 拿到选中的菜单ids -- 用于提交数据
function getCheckedKeys() {
  return proxy.$refs.menuTreeRef.getCheckedKeys();
}

// 拿到选中的菜单ids -- 回显
function recurveGetCheckedKeys(checkedKeys, menuTree) {
  menuTree.forEach((item) => {
    if (item.isHasPerm) {
      checkedKeys.push(item.id);
    }
    if (item.children.length > 0) {
      recurveGetCheckedKeys(checkedKeys, item.children);
    }
  });
  return checkedKeys;
}

// 点击菜单
function menuNodeClick(data) {}

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
