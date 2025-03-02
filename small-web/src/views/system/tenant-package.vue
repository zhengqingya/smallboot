<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.name" label="套餐名" clearable @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData" v-has-perm="'sys:tenant-package:page'">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd" v-has-perm="'sys:tenant-package:add'">添加</el-button>
      </template>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="sys_tenant_package.page" :params="listQuery">
        <el-table-column label="ID" prop="id" align="center" />
        <el-table-column label="套餐名" prop="name" align="center" />
        <el-table-column label="状态" prop="status" align="center">
          <template #default="scope">
            <base-tag v-model="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="排序" prop="sort" align="center" />
        <el-table-column label="备注" prop="remark" align="center" />
        <el-table-column label="创建时间" prop="createTime" align="center" />
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <el-button v-if="scope.row.id !== 1" link @click="handleUpdate(scope.row)" v-has-perm="'sys:tenant-package:edit'">编辑</el-button>
            <base-delete-btn v-if="scope.row.id !== 1" @ok="handleDelete(scope.row)" v-has-perm="'sys:tenant-package:delete'"></base-delete-btn>
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="500px">
      <el-form ref="dataFormRef" :model="form" label-width="100px">
        <el-form-item label="套餐名:">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="状态:">
          <el-radio-group v-model="form.status">
            <el-radio :value="1">正常</el-radio>
            <el-radio :value="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单权限:">
          <div style="height: 400px; width: 100%">
            <menu-perm-tree v-if="dialogVisible && form.menuTree && form.menuTree.length > 0" ref="menuTreeRef" v-model="form.menuTree" />
          </div>
        </el-form-item>
        <el-form-item label="排序:">
          <el-input-number v-model="form.sort" :min="1" controls-position="right" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form.remark" :rows="2" type="textarea" />
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

onMounted(() => {});

async function getMenuTree() {
  let res = await proxy.$api.sys_menu.tree({ isOnlySystemAdminRePerm: true });
  return res.data;
}

async function handleAdd() {
  let menuTree = await getMenuTree();
  recurveMenu(menuTree, [], []);
  form = { sort: 100, status: 1, menuTree: menuTree };
  dialogStatus = 'add';
  dialogVisible = true;
}
async function handleUpdate(row) {
  form = Object.assign({}, row);

  let menuTree = await getMenuTree();
  recurveMenu(menuTree, form.menuIdList);
  form.menuTree = menuTree;
  dialogStatus = 'update';
  dialogVisible = true;
}

function recurveMenu(list, menuIdList) {
  list.forEach((menuItem) => {
    menuItem.isHasPerm = false;
    if (menuIdList.includes(menuItem.id)) {
      menuItem.isHasPerm = true;
    }
    if (menuItem.children.length > 0) {
      recurveMenu(menuItem.children, menuIdList);
    }
  });
}

async function handleDelete(row) {
  let res = await proxy.$api.sys_tenant_package.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let selectMenuIdList = proxy.$refs.menuTreeRef.getCheckedKeys();
      form.menuIdList = selectMenuIdList;
      let res = await proxy.$api.sys_tenant_package[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
