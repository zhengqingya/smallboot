<template>
  <base-wrapper>
    <base-header>
      <base-cascader
        v-model="listQuery.deptId"
        style="margin-right: 10px"
        clearable
        label="部门"
        :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
        api="sys_dept.tree" />
      <base-input v-model="listQuery.name" label="岗位名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTableRef" api="sys_post.page" :params="listQuery">
      <el-table-column label="ID" prop="id" align="center" />
      <el-table-column label="归属部门" prop="deptName" align="center" />
      <el-table-column label="岗位名称" prop="name" align="center" />
      <el-table-column label="岗位编码" prop="code" align="center" />
      <el-table-column label="排序" prop="sort" align="center" />
      <el-table-column label="状态" align="center">
        <template #default="scope">
          <base-tag v-model="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark" align="center" />
      <el-table-column label="创建时间" prop="createTime" align="center" />
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="30%">
      <el-form ref="dataFormRef" :model="form" label-width="100px">
        <el-form-item label="归属部门:">
          <base-cascader v-model="form.deptId" style="width: 100%" clearable :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }" api="sys_dept.tree" />
        </el-form-item>
        <el-form-item label="岗位名称:">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="岗位编码:">
          <el-input v-model="form.code" />
        </el-form-item>
        <el-form-item label="状态:">
          <base-radio-group v-model="form.status" />
        </el-form-item>
        <el-form-item label="排序:">
          <el-input-number v-model="form.sort" :min="1" controls-position="right" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form.remark" :row="2" type="textarea" />
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
  form = { status: 1, sort: 100 };
  dialogStatus = 'add';
  dialogVisible = true;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  dialogStatus = 'update';
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.sys_post.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.sys_post[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
