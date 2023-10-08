<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.name" label="套餐名" style="width: 200px" clearable @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTableRef" api="sys_tenant_package.page" :params="listQuery">
      <el-table-column label="ID" prop="id" align="center" />
      <el-table-column label="套餐名" prop="name" align="center" />
      <el-table-column label="状态" prop="status" align="center">
        <template #default="scope">
          <span>{{ scope.row.status ? '开启' : '禁用' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark" align="center" />
      <el-table-column label="创建时间" prop="createTime" align="center" />
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button link @click="handleDetail(scope.row)">详情</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="30%">
      <el-form ref="dataFormRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="套餐名:">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="状态:">
          <el-radio-group v-model="form.status">
            <el-radio :label="true">开启</el-radio>
            <el-radio :label="false">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单权限:">
          <el-input v-model="form.menuIdList" />
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form.remark" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </base-dialog>
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let listQuery = $ref({});
let form = $ref({});
let dialogVisible = $ref(false);
let dialogStatus = $ref('');

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleAdd() {
  form = { status: true };
  dialogStatus = 'add';
  dialogVisible = true;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  dialogStatus = 'update';
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.sys_tenant_package.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.sys_tenant_package[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
