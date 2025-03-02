<template>
  <base-wrapper>
    <base-header>
      <base-cascader
        v-model="listQuery.menuId"
        style="margin-right: 10px"
        clearable
        placeholder="请选择菜单"
        :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: false, emitPath: false }"
        :data-list="menuList" />
      <base-input v-model="listQuery.scopeName" label="权限名称" @clear="refreshTableData" />
      <base-select
        v-model="listQuery.scopeType"
        style="margin-right: 10px"
        clearable
        label="规则类型"
        :option-props="{ label: 'label', value: 'value' }"
        :data-list="typeList"
        @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-content>
      <el-table
        ref="baseTableRef"
        border
        :header-cell-style="{ background: '#13C3C3', color: '#fff' }"
        row-key="customId"
        :row-class-name="tableRowClassName"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        :data="tableDataList"
        default-expand-all>
        <!-- <el-table-column label="ID" prop="id" align="center" /> -->
        <el-table-column label="菜单" align="left" prop="menuFullName" width="300px" />
        <el-table-column label="权限名称" prop="scopeName" align="center" />
        <el-table-column label="权限字段" prop="scopeColumn" align="center" />
        <!-- <el-table-column label="可见字段" prop="scopeVisibleField" align="center" /> -->
        <!-- <el-table-column label="全权限类名" prop="scopeClass" align="center" /> -->
        <el-table-column label="规则类型" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.scopeType">{{ typeList.find((e) => e.value == scope.row.scopeType).label }}</el-tag>
          </template>
        </el-table-column>
        <!-- <el-table-column label="规则值" prop="scopeValue" align="center" /> -->
        <!-- <el-table-column label="备注" prop="remark" align="center" /> -->
        <!-- <el-table-column label="创建时间" prop="createTime" align="center" /> -->
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <div v-if="scope.row.children.length == 0">
              <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
              <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </base-content>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="50%">
      <el-form v-if="dialogStatus !== 'detail'" ref="dataFormRef" :model="form" label-width="90px">
        <el-form-item label="菜单:">
          <base-cascader
            v-model="form.menuId"
            style="width: 100%"
            clearable
            :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: false, emitPath: false }"
            :data-list="menuList" />
        </el-form-item>
        <el-form-item label="名称:">
          <el-input v-model="form.scopeName" />
        </el-form-item>
        <el-form-item label="规则类型:">
          <base-select v-model="form.scopeType" style="width: 100%" clearable :option-props="{ label: 'label', value: 'value' }" :data-list="typeList" />
        </el-form-item>
        <el-form-item label="权限字段:">
          <el-input v-model="form.scopeColumn" placeholder="eg: dept_id" />
        </el-form-item>
        <el-form-item label="可见字段:">
          <el-input v-model="form.scopeVisibleField" placeholder="* 标识所有" />
        </el-form-item>
        <el-form-item label="全权限类名:">
          <el-input v-model="form.scopeClass" :rows="5" type="textarea" placeholder="mapper class 全限定类名 （多个用英文逗号分隔）" />
        </el-form-item>
        <el-form-item v-if="form.scopeType == 10" label="规则值:">
          <el-input v-model="form.scopeValue" :rows="2" type="textarea" />
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form.remark" :rows="2" type="textarea" />
        </el-form-item>
      </el-form>
      <template v-if="dialogStatus !== 'detail'" #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </base-dialog>
  </base-wrapper>
</template>

<script setup>
import { nextTick } from 'vue';

const { proxy } = getCurrentInstance();
let listQuery = $ref({});
let form = $ref({});
let dialogVisible = $ref(false);
let dialogStatus = $ref('');
let typeList = $ref([
  { label: '全部可见', value: 1 },
  { label: '本人可见', value: 2 },
  { label: '所在部门可见', value: 3 },
  { label: '所在部门及子级可见', value: 4 },
  { label: '所在角色', value: 5 },
  { label: '所在角色以及下级角色', value: 6 },
  { label: '自定义', value: 10 },
]);
let tableDataList = $ref([]);
let menuList = $ref([]);

onMounted(() => {
  refreshTableData();
  menuTree();
});

async function refreshTableData() {
  let res = await proxy.$api.sys_scope_data.tree(listQuery);
  tableDataList = res.data;
}
async function menuTree() {
  let res = await proxy.$api.sys_menu.tree({ type: 1 });
  menuList = res.data;
}

function handleAdd() {
  form = { scopeType: 1, scopeVisibleField: '*' };
  dialogStatus = 'add';
  dialogVisible = true;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  dialogStatus = 'update';
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.sys_scope_data.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.sys_scope_data[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}

function tableRowClassName({ row, rowIndex }) {
  if (row.children.length === 0) {
    return 'success-row';
  }
  return '';
}
</script>

<!-- 不要加 scoped 否则表格行样式会失效 -->
<style lang="scss">
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>
