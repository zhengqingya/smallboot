<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.deptId" label="部门id" @clear="refreshTableData" />
      <base-input v-model="listQuery.name" label="名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTableRef" api="cms_job_category.page" :params="listQuery">
      <el-table-column label="主键ID" prop="id" align="center" />
      <el-table-column label="租户ID" prop="tenantId" align="center" />
      <el-table-column label="部门id" prop="deptId" align="center" />
      <el-table-column label="名称" prop="name" align="center" />
      <el-table-column label="排序" prop="sort" align="center" />
      <el-table-column label="状态(0:停用 1:正常)" prop="status" align="center" />
      <el-table-column label="备注" prop="remark" align="center" />
      <el-table-column label="创建时间" align="center">
        <template #default="scope">
          <span>{{ scope.row.createTime | dateTimeFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="修改时间" align="center">
        <template #default="scope">
          <span>{{ scope.row.updateTime | dateTimeFilter }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建人" prop="createBy" align="center" />
      <el-table-column label="修改人" prop="updateBy" align="center" />
      <el-table-column label="是否删除(0->否,1->是)" prop="isDeleted" align="center" />
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button link @click="handleDetail(scope.row)">详情</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="30%">
      <el-form v-if="dialogStatus !== 'detail'" ref="dataFormRef" :inline="true" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="主键ID:">
          <el-input v-model="form.id" />
        </el-form-item>
        <el-form-item label="租户ID:">
          <el-input v-model="form.tenantId" />
        </el-form-item>
        <el-form-item label="部门id:">
          <el-input v-model="form.deptId" />
        </el-form-item>
        <el-form-item label="名称:">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="排序:">
          <el-input v-model="form.sort" />
        </el-form-item>
        <el-form-item label="状态(0:停用 1:正常):">
          <el-input v-model="form.status" />
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form.remark" />
        </el-form-item>
        <el-form-item label="创建时间:">
          <el-input v-model="form.createTime" />
        </el-form-item>
        <el-form-item label="修改时间:">
          <el-input v-model="form.updateTime" />
        </el-form-item>
        <el-form-item label="创建人:">
          <el-input v-model="form.createBy" />
        </el-form-item>
        <el-form-item label="修改人:">
          <el-input v-model="form.updateBy" />
        </el-form-item>
        <el-form-item label="是否删除(0->否,1->是):">
          <el-input v-model="form.isDeleted" />
        </el-form-item>
      </el-form>
      <base-cell v-else label-width="100px">
        <base-cell-item label="主键ID">{{ form.id }}</base-cell-item>
        <base-cell-item label="租户ID">{{ form.tenantId }}</base-cell-item>
        <base-cell-item label="部门id">{{ form.deptId }}</base-cell-item>
        <base-cell-item label="名称">{{ form.name }}</base-cell-item>
        <base-cell-item label="排序">{{ form.sort }}</base-cell-item>
        <base-cell-item label="状态(0:停用 1:正常)">{{ form.status }}</base-cell-item>
        <base-cell-item label="备注">{{ form.remark }}</base-cell-item>
        <base-cell-item label="创建时间">{{ form.createTime }}</base-cell-item>
        <base-cell-item label="修改时间">{{ form.updateTime }}</base-cell-item>
        <base-cell-item label="创建人">{{ form.createBy }}</base-cell-item>
        <base-cell-item label="修改人">{{ form.updateBy }}</base-cell-item>
        <base-cell-item label="是否删除(0->否,1->是)">{{ form.isDeleted }}</base-cell-item>
      </base-cell>
      <template v-if="dialogStatus !== 'detail'" #footer>
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
  let res = await proxy.$api.cms_job_category.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.cms_job_category[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
