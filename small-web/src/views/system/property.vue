<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.key" clearable label="key" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="add">添加</el-button>
      </template>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="sys_config.listPage" :params="listQuery">
        <el-table-column prop="key" label="属性key" />
        <el-table-column prop="value" label="属性value" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column label="操作" align="center" width="250">
          <template #default="scope">
            <el-button link @click="update(scope.row)">编辑</el-button>
            <base-delete-btn @ok="deleteData(scope.row.id)" />
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="50%">
      <el-form ref="formRef" :model="form" label-width="100px">
        <el-form-item label="属性key：">
          <el-input v-model="form.key" />
        </el-form-item>
        <el-form-item label="属性value：">
          <el-input v-model="form.value" />
        </el-form-item>
        <el-form-item label="备注：">
          <el-input v-model="form.remark" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveForm">确 定</el-button>
      </template>
    </base-dialog>
  </base-wrapper>
</template>
<script setup>
const { proxy } = getCurrentInstance();
// 响应式
const state = reactive({
  form: {},
  dialogVisible: false,
  listLoading: false,
  listQuery: { type: 2 },
  dialogStatus: '',
});
const { form, dialogVisible, listQuery, dialogStatus } = toRefs(state);

async function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function saveForm() {
  proxy.$refs.formRef.validate(async (valid) => {
    if (valid) {
      state.form.type = 2;
      let res = await proxy.$api.sys_config[state.form.id ? 'update' : 'add'](state.form);
      proxy.submitOk(res.msg);
      refreshTableData();
      state.dialogVisible = false;
    }
  });
}
function update(row) {
  state.form = Object.assign({}, row);
  state.dialogVisible = true;
  state.dialogStatus = 'update';
}
function add() {
  state.dialogVisible = true;
  state.dialogStatus = 'add';
  state.form = {};
}
async function deleteData(id) {
  let res = await proxy.$api.sys_config.delete(id);
  proxy.submitOk(res.msg);
  refreshTableData();
}
</script>
<style scoped></style>
