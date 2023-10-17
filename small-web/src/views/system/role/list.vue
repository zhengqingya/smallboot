<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.name" clearable label="角色名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="add">添加</el-button>
      </template>
    </base-header>

    <el-table row-key="roleId" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" :data="dataList" default-expand-all>
      <el-table-column prop="name" label="角色名" />
      <el-table-column prop="code" label="角色编码">
        <template #default="scope">
          <el-tag v-if="scope.row.isFixed" type="success"> {{ scope.row.code }}</el-tag>
          <el-tag v-else type="info"> {{ scope.row.code }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="code" label="固定角色">
        <template #default="scope">
          <base-tag v-model="scope.row.isFixed" data-type="yes" />
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" />
      <el-table-column label="操作" align="center" width="250">
        <template #default="scope">
          <el-button v-if="!scope.row.isFixed" link @click="update(scope.row)">编辑</el-button>
          <el-button type="primary" link @click="add(scope.row.roleId)">新增子项</el-button>
          <router-link v-if="!scope.row.isFixed || scope.row.code == 'merchant_admin'" :to="{ path: '/system/role-edit', query: { id: scope.row.roleId } }">
            <el-button link>权限</el-button>
          </router-link>
          <base-delete-btn v-if="!scope.row.isFixed" @ok="deleteData(scope.row.roleId)" />
        </template>
      </el-table-column>
    </el-table>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="50%">
      <el-form ref="roleFormRef" :model="roleForm" :rules="rules" label-width="100px">
        <el-form-item label="父角色:">
          <base-cascader
            v-if="dialogVisible"
            v-model="roleForm.parentId"
            clearable
            :params="{ excludeRoleId: roleForm.roleId }"
            placeholder="请选择(为空时标识顶级)"
            :props="{ value: 'roleId', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
            api="sys_role.tree" />
        </el-form-item>
        <el-form-item label="角色名：" prop="name">
          <el-input v-model="roleForm.name" placeholder="请输入角色名" />
        </el-form-item>
        <el-form-item label="角色编码：" prop="code">
          <el-input v-model="roleForm.code" placeholder="请输入角色编码" />
        </el-form-item>
        <el-form-item label="排序：" prop="sort">
          <el-input-number v-model="roleForm.sort" :min="1" controls-position="right" placeholder="请输入排序" />
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
let roleForm = $ref({});
let dialogVisible = $ref(false);
let listQuery = $ref({});
let rules = {
  code: [{ required: true, message: '请输入角色编码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
};
let dialogStatus = $ref('');
let dataList = $ref([]);

onMounted(() => {
  refreshTableData();
});

async function refreshTableData() {
  let res = await proxy.$api.sys_role.tree(listQuery);
  dataList = res.data;
}
function saveForm() {
  proxy.$refs.roleFormRef.validate(async (valid) => {
    if (valid) {
      if (!roleForm.parentId) {
        roleForm.parentId = 0;
      }
      let res = await proxy.$api.sys_role[roleForm.roleId ? 'update' : 'add'](roleForm);
      proxy.submitOk(res.msg);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
function update(row) {
  roleForm = Object.assign({}, row);
  dialogVisible = true;
  dialogStatus = 'update';
}
function add(parentId) {
  roleForm = { parentId: parentId, isFixed: 0, sort: 100 };
  dialogVisible = true;
  dialogStatus = 'add';
}
async function deleteData(id) {
  let res = await proxy.$api.sys_role.delete(id);
  proxy.submitOk(res.msg);
  refreshTableData();
}
</script>
<style scoped></style>
