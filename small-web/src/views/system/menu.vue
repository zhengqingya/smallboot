<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.name" label="名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd()">添加</el-button>
      </template>
    </base-header>

    <base-content>
      <base-table row-key="id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" :data="dataList" :default-expand-all="false">
        <el-table-column label="ID" prop="id" align="center" />
        <el-table-column label="名称" align="left">
          <template #default="scope">
            <div class="flex-start-center">
              <el-icon size="15">
                <component :is="scope.row.icon" v-if="scope.row.icon" />
              </el-icon>
              <span style="margin-left: 10px">{{ scope.row.name }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="访问路径" prop="path" align="left" />
        <el-table-column label="组件名" prop="component" align="left" />
        <el-table-column label="按钮权限" prop="btnPerm" align="left" />
        <el-table-column label="排序" prop="sort" align="center" />
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button type="primary" link @click="handleAdd(scope.row)">新增子项</el-button>
            <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
          </template>
        </el-table-column>
      </base-table>
    </base-content>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="30%">
      <el-form ref="dataFormRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="父菜单:">
          <base-cascader
            v-if="dialogVisible"
            v-model="form.parentId"
            is-full
            clearable
            :params="{ excludeMenuId: form.id }"
            placeholder="请选择(为空时标识顶级)"
            :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
            api="sys_menu.tree" />
        </el-form-item>
        <el-form-item label="名称:" prop="name">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="类型:">
          <el-radio-group v-model="form.type">
            <el-radio :value="1">菜单</el-radio>
            <el-radio :value="2">按钮</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="访问路径:" prop="path">
          <el-input v-model="form.path" placeholder="eg: 一级菜单（/sys） 二级菜单（user）" />
        </el-form-item>
        <el-form-item v-if="form.type == 1" label="组件:" prop="component">
          <el-input v-model="form.component" placeholder="请输入菜单对应的文件路径 eg: sys/user" />
        </el-form-item>
        <el-form-item v-if="form.type == 2" label="按钮权限标识：">
          <el-input v-model="form.btnPerm" placeholder="sys:user:add" />
        </el-form-item>
        <el-form-item v-if="form.type == 1" label="图标:">
          <el-select v-model="form.icon" placeholder="请选择图标" filterable clearable allow-create>
            <el-option v-for="item in elIconList" :key="item.name" :value="item.name" :label="item.name">
              <span style="float: left; color: #8492a6; font-size: 12px">{{ item.name }}</span>
              <div style="float: right">
                <el-icon size="16">
                  <component :is="item.name" />
                </el-icon>
              </div>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="form.type == 1" label="重定向路径:">
          <el-input v-model="form.redirect" placeholder="输入重定向路径" />
        </el-form-item>
        <el-form-item v-if="form.type == 1" label="是否显示:">
          <base-radio-group v-model="form.isShow" data-type="yes" />
        </el-form-item>
        <el-form-item v-if="form.type == 1" label="是否显示面包屑:">
          <base-radio-group v-model="form.isShowBreadcrumb" data-type="yes" />
        </el-form-item>
        <el-form-item label="排序:">
          <el-input-number v-model="form.sort" :min="1" controls-position="right" placeholder="请输入排序" />
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
let rules = {
  name: [{ required: true, message: '名称不得为空', trigger: 'blur' }],
  path: [{ required: true, message: '访问路径不得为空', trigger: 'blur' }],
  // component: [{ required: true, message: '菜单文件路径不得为空', trigger: 'blur' }],
};
let dataList = $ref([]);
let elIconList = $ref([]);

onMounted(() => {
  refreshTableData();
  getIconList();
});

async function refreshTableData() {
  let res = await proxy.$api.sys_menu.tree(listQuery);
  dataList = res.data;
}
async function getIconList() {
  let res = await proxy.$api.sys_dict.listFromCacheByCode('element_icon');
  elIconList = res.data;
}
function handleAdd(row) {
  form = {
    parentId: row ? row.id : 0,
    status: 1,
    type: 1,
    sort: row ? row.children.length + 1 : dataList.length + 1,
    isShow: true, // 是否显示
    isShowBreadcrumb: true, // 面包屑是否显示
  };
  dialogStatus = 'add';
  dialogVisible = true;
}

function handleUpdate(row) {
  form = Object.assign({}, row);
  dialogStatus = 'update';
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.sys_menu.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      if (!form.parentId || !isFinite(form.parentId)) {
        form.parentId = 0;
      }
      let res = await proxy.$api.sys_menu[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
