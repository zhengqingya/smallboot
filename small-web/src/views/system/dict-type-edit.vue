<template>
  <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="30%">
    <el-form :model="form" label-width="120px">
      <el-form-item label="字典类型名称：" prop="name">
        <el-input v-model="form.name" placeholder="输入字典类型名称" />
      </el-form-item>
      <el-form-item label="字典类型编码：">
        <el-input v-model="form.code" :disabled="form.id" placeholder="输入字典类型编码" />
      </el-form-item>
      <el-form-item label="状态：">
        <base-radio-group v-model="form.status" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleSave">确 定</el-button>
    </template>
  </base-dialog>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let dialogVisible = $ref(false);
let form = $ref({});
let dialogStatus = $ref('');

defineExpose({
  open,
});

function open(type, data) {
  dialogStatus = type;
  if (type === 'add') {
    form = {};
    form.status = 1;
  } else if (type === 'update') {
    form = Object.assign({}, data);
  }
  dialogVisible = true;
}
async function handleSave() {
  let res = await proxy.$api.sys_dict_type[form.id ? 'update' : 'add'](form);
  proxy.$emit('save-succ');
  dialogVisible = false;
  proxy.submitOk(res.msg);
}
</script>
<style lang="scss" scoped></style>
