<template>
  <base-dialog v-model="dialogVisible" title="系统设置" width="50%" :close-on-click-modal="true">
    <el-radio-group v-model="menuType">
      <el-radio :value="1">element-plus菜单</el-radio>
      <el-radio :value="2">自定义菜单</el-radio>
    </el-radio-group>
    <template #footer>
      <!-- <el-button type="primary" @click="saveForm">确 定</el-button> -->
    </template>
  </base-dialog>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let dialogVisible = $ref(false);
let { menuType } = toRefs(proxy.$store.settings.useSettingsStore());

defineExpose({
  open,
});

function open() {
  dialogVisible = true;
}

function saveForm() {
  proxy.submitOk('操作成功');
  dialogVisible = false;
}
</script>
<style lang="scss" scoped></style>
