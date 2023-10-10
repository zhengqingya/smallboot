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
          <base-tag v-model="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark" align="center" />
      <el-table-column label="创建时间" prop="createTime" align="center" />
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button v-if="scope.row.id !== 1" link @click="handleUpdate(scope.row)">编辑</el-button>
          <base-delete-btn v-if="scope.row.id !== 1" @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="60%">
      <el-form ref="dataFormRef" :model="form" label-width="100px">
        <el-form-item label="套餐名:">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="状态:">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">正常</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单权限:">
          <div style="height: 400px; width: 100%" class="overflow-y-auto">
            <menu-perm-tree v-if="dialogVisible && form.menuTree && form.menuTree.length > 0" v-model="form.menuTree" />
          </div>
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

onMounted(() => {});

async function getMenuTree() {
  let res = await proxy.$api.sys_menu.menuTreeAll();
  return res.data;
}

async function handleAdd() {
  let menuTree = await getMenuTree();
  recurveMenu(menuTree, [], []);
  form = { status: 1, menuTree: menuTree };
  dialogStatus = 'add';
  dialogVisible = true;
}
async function handleUpdate(row) {
  form = Object.assign({}, row);

  let menuTree = await getMenuTree();
  recurveMenu(menuTree, form.menuIdList, form.permissionIdList);
  form.menuTree = menuTree;
  dialogStatus = 'update';
  dialogVisible = true;
}

function recurveMenu(list, menuIdList, permissionIdList) {
  list.forEach((menuItem) => {
    menuItem.isHasPerm = false;
    if (menuIdList.includes(menuItem.menuId)) {
      menuItem.isHasPerm = true;
    }
    if (menuItem.permList.length > 0) {
      menuItem.permList.forEach((permItem) => {
        if (permissionIdList.includes(permItem.id)) {
          permItem.isHasPerm = true;
        }
      });
    }
    if (menuItem.children.length > 0) {
      recurveMenu(menuItem.children, menuIdList, permissionIdList);
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
      let res = await proxy.$api.sys_tenant_package[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
