<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.name" label="名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-content>
      <base-table row-key="id" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" :data="dataList" default-expand-all>
        <el-table-column label="ID" prop="id" align="center" />
        <el-table-column label="名称" prop="name" align="center" />
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <base-tag v-model="scope.row.status" />
          </template>
        </el-table-column>

        <el-table-column label="排序" prop="sort" align="center" />

        <el-table-column label="创建时间" prop="createTime" align="center" />
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button type="primary" link @click="handleAdd(scope.row.id)">新增子项</el-button>
            <el-button link type="primary" @click="showDetail(scope.row)">详情</el-button>
            <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
          </template>
        </el-table-column>
      </base-table>
    </base-content>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="40%">
      <el-form ref="appDataFormRef" :inline="!true" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父部门:">
          <base-cascader
            v-if="dialogVisible"
            v-model="form.parentId"
            clearable
            :params="{ excludeDeptId: form.id }"
            placeholder="请选择(为空时标识顶级)"
            :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: false, emitPath: false }"
            api="sys_dept.tree" />
        </el-form-item>
        <el-form-item label="名称:">
          <el-input v-model="form.name" :disabled="isDetail" />
        </el-form-item>
        <el-form-item label="负责人:">
          <base-select v-model="form.leaderUserId" :disabled="isDetail" clearable :option-props="{ label: 'nickname', value: 'userId' }" api="sys_user.list" />
        </el-form-item>
        <el-form-item label="状态:">
          <base-radio-group v-model="form.status" :disabled="isDetail" />
        </el-form-item>

        <div v-if="form.parentId == 0">
          <el-form-item label="所属地区:">
            <province-city-area v-model="form.provinceCityAreaList" :disabled="isDetail" />
          </el-form-item>
          <el-form-item label="详细地址:">
            <el-input v-model="form.address" :disabled="isDetail" />
          </el-form-item>
        </div>
        <el-form-item label="排序:">
          <el-input-number v-model="form.sort" :min="1" :disabled="isDetail" controls-position="right" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form.remark" :rows="2" :disabled="isDetail" type="textarea" />
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
let isDetail = $ref(false);
let rules = $ref({});
let dataList = $ref([]);

onMounted(() => {
  refreshTableData();
});

async function refreshTableData() {
  let res = await proxy.$api.sys_dept.tree(listQuery);
  dataList = res.data;
}
function handleAdd(parentId) {
  form = { parentId: parentId, status: 1, sort: 100 };
  dialogStatus = 'add';
  dialogVisible = true;
  isDetail = false;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  form.provinceCityAreaList = [form.provinceName, form.cityName, form.areaName];
  dialogStatus = 'update';
  dialogVisible = true;
  isDetail = false;
}
function showDetail(row) {
  form = Object.assign({}, row);
  form.provinceCityAreaList = [form.provinceName, form.cityName, form.areaName];
  dialogStatus = 'detail';
  dialogVisible = true;
  isDetail = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.sys_dept.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.appDataFormRef.validate(async (valid) => {
    if (valid) {
      // 省市区
      if (form.provinceCityAreaList) {
        form.provinceName = form.provinceCityAreaList[0];
        if (form.provinceCityAreaList.length > 1) {
          form.cityName = form.provinceCityAreaList[1];
          form.areaName = form.provinceCityAreaList[2];
        }
      }
      if (!form.parentId || !isFinite(form.parentId)) {
        form.parentId = 0;
      }
      let res = await proxy.$api.sys_dept[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
