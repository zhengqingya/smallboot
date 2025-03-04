<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.name" clearable label="角色名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData" v-has-perm="'sys:role:page'">查询</el-button>
      <template #right>
        <el-button type="primary" @click="add(0)" v-has-perm="'sys:role:add'">添加</el-button>
      </template>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="sys_role.page" :params="listQuery">
        <el-table-column prop="name" label="角色名" align="center" />
        <el-table-column prop="code" label="角色编码" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.isFixed" type="success"> {{ scope.row.code }}</el-tag>
            <el-tag v-else type="info"> {{ scope.row.code }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="code" align="center" label="固定角色">
          <template #default="scope">
            <base-tag v-model="scope.row.isFixed" data-type="yes" />
          </template>
        </el-table-column>
        <el-table-column prop="code" align="center" label="同步更新所有租户角色权限">
          <template #default="scope">
            <base-tag v-model="scope.row.isRefreshAllTenant" data-type="yes" />
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" align="center" />
        <el-table-column label="操作" align="center" width="230">
          <template #default="scope">
            <!--  固定角色=系统管理员 时 只有超级管理员才能编辑 -->
            <el-button v-if="isHasOperatePerm(scope.row)" link @click="update(scope.row)" v-has-perm="'sys:role:edit'">编辑</el-button>
            <!-- <el-button v-if="isHasOperatePerm(scope.row)" type="primary" link @click="add(scope.row.roleId)" v-has-perm="'sys:role:edit'">新增子项</el-button> -->
            <router-link v-if="isHasOperatePerm(scope.row)" :to="{ path: '/system/role-edit', query: { id: scope.row.roleId } }">
              <el-button link v-has-perm="'sys:role:perm'">权限</el-button>
            </router-link>
            <base-delete-btn v-has-perm="'sys:role:delete'" v-if="isHasOperatePerm(scope.row)" @ok="deleteData(scope.row.roleId)" />
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="500px">
      <el-form ref="roleFormRef" :model="roleForm" :rules="rules" label-width="100px">
        <el-form-item label="父角色:">
          <base-cascader
            v-if="dialogVisible"
            v-model="roleForm.parentId"
            clearable
            is-full
            :params="{ excludeRoleId: roleForm.roleId }"
            placeholder="请选择(为空时标识顶级)"
            :props="{ value: 'roleId', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
            api="sys_role.tree" />
        </el-form-item>
        <el-form-item label="角色名：" prop="name">
          <el-input v-model="roleForm.name" placeholder="请输入角色名" />
        </el-form-item>
        <el-form-item label="角色编码：" prop="code">
          <el-input v-model="roleForm.code" :disabled="roleForm.roleId != null" placeholder="请输入角色编码" />
        </el-form-item>
        <div v-if="roleForm.parentId == 0">
          <el-form-item label="固定角色:">
            <base-radio-group v-model="roleForm.isFixed" data-type="yes" />
          </el-form-item>
          <el-form-item label="同步更新权限:">
            <base-radio-group v-model="roleForm.isRefreshAllTenant" data-type="yes" />
          </el-form-item>
        </div>
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
let { userObj } = toRefs(proxy.$store.user.useUserStore());
let roleForm = $ref({});
let dialogVisible = $ref(false);
let listQuery = $ref({ excludeRoleCodeList: 'tenant_admin' });
let rules = {
  code: [{ required: true, message: '请输入角色编码', trigger: 'blur' }],
  name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
};
let dialogStatus = $ref('');

onMounted(() => {
  refreshTableData();
});

async function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function saveForm() {
  proxy.$refs.roleFormRef.validate(async (valid) => {
    if (valid) {
      if (!roleForm.parentId || !isFinite(roleForm.parentId)) {
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
  roleForm = { parentId: parentId, isFixed: false, status: 1, isRefreshAllTenant: false, sort: 100 };
  dialogVisible = true;
  dialogStatus = 'add';
}
async function deleteData(id) {
  let res = await proxy.$api.sys_role.delete(id);
  proxy.submitOk(res.msg);
  refreshTableData();
}
// 判断下是否有操作权限
function isHasOperatePerm(row) {
  let currentUserId = userObj.value.userId;
  return !row.isFixed || currentUserId == 1 || (!['super_admin', 'system_admin', 'tenant_admin'].includes(row.code) && currentUserId == 2);
}
</script>
<style scoped></style>
