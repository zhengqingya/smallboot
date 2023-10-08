<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.name" label="租户名" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTableRef" api="sys_tenant.page" :params="listQuery">
      <el-table-column label="ID" prop="id" align="center" />
      <el-table-column label="租户名" prop="name" align="center" />
      <el-table-column label="联系人" prop="contactName" align="center" />
      <el-table-column label="联系人手机号" prop="contactPhone" align="center" />
      <el-table-column label="状态" prop="status" align="center">
        <template #default="scope">
          <span>{{ scope.row.status ? '开启' : '停用' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="过期时间" prop="expireTime" align="center" />
      <el-table-column label="账号数量" prop="accountCount" align="center" />
      <el-table-column label="租户套餐" prop="packageName" align="center" />
      <el-table-column label="创建时间" prop="createTime" align="center" />
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="30%">
      <el-form ref="dataFormRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="租户名:">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="联系人:">
          <el-input v-model="form.contactName" />
        </el-form-item>
        <el-form-item label="联系人手机号:">
          <el-input v-model="form.contactPhone" />
        </el-form-item>
        <el-form-item label="租户状态:">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">开启</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="过期时间:">
          <el-date-picker v-model="form.expireTime" type="datetime" placeholder="请选择" format="YYYY-MM-DD hh:mm:ss" value-format="YYYY-MM-DD hh:mm:ss" />
        </el-form-item>
        <el-form-item label="账号数量:">
          <el-input v-model="form.accountCount" />
        </el-form-item>
        <el-form-item label="租户套餐ID:">
          <base-select v-model="form.packageId" :option-props="{ label: 'name', value: 'id' }" api="sys_tenant_package.list" />
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
let rules = $ref({});

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleDetail(row) {
  form = Object.assign({}, row);
  dialogStatus = 'detail';
  dialogVisible = true;
}
function handleAdd() {
  form = {};
  dialogStatus = 'add';
  dialogVisible = true;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  dialogStatus = 'update';
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.sys_tenant.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.sys_tenant[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
