<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.name" label="部门名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <el-table :data="dataList" default-expand-all>
      <el-table-column label="ID" prop="id" align="center" />
      <el-table-column label="部门名称" prop="name" align="center" />
      <el-table-column label="排序" prop="sort" align="center" />
      <el-table-column label="负责人id" prop="leaderUserId" align="center" />
      <el-table-column label="状态" align="center">
        <template #default="scope">
          <span>{{ scope.row.status === 1 ? '正常' : '停用' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="过期时间" prop="expireTime" align="center" />
      <el-table-column label="最大用户数" prop="userNum" align="center" />
      <el-table-column label="备注" prop="remark" align="center" />
      <el-table-column label="创建时间" prop="createTime" align="center" />
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button link @click="handleDetail(scope.row)">详情</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </el-table>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="60%">
      <el-form v-if="dialogStatus !== 'detail'" ref="dataFormRef" :inline="true" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="父部门ID:">
          <el-input v-model="form.parentId" />
        </el-form-item>
        <el-form-item label="部门名称:">
          <el-input v-model="form.name" />
        </el-form-item>

        <el-form-item label="负责人:">
          <el-input v-model="form.leaderUserId" />
        </el-form-item>
        <el-form-item label="状态:">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">正常</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="过期时间:">
          <el-date-picker v-model="form.expireTime" type="datetime" placeholder="请选择" format="YYYY-MM-DD hh:mm:ss" value-format="YYYY-MM-DD hh:mm:ss" />
        </el-form-item>
        <el-form-item label="最大用户数:">
          <el-input v-model="form.userNum" />
        </el-form-item>
        <el-form-item label="所属地区:">
          <province-city-area v-model="form.provinceCityAreaList" :disabled="isDetail" />
        </el-form-item>
        <el-form-item label="详细地址:">
          <el-input v-model="form.address" />
        </el-form-item>
        <el-form-item label="排序:">
          <el-input-number v-model="form.sort" :min="1" controls-position="right" />
        </el-form-item>
      </el-form>
      <el-form-item label="备注:">
        <el-input v-model="form.remark" :row="2" type="textarea" />
      </el-form-item>

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
let dataList = $ref([]);

onMounted(() => {
  refreshTableData();
});

async function refreshTableData() {
  let res = await proxy.$api.sys_dept.tree(listQuery);
  dataList = res.data;
}
function handleAdd() {
  form = { status: 1, sort: 100 };
  dialogStatus = 'add';
  dialogVisible = true;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  form.provinceCityAreaList = [form.provinceName, form.cityName, form.areaName];
  dialogStatus = 'update';
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.sys_dept.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      // 省市区
      if (form.provinceCityAreaList) {
        form.provinceName = form.provinceCityAreaList[0];
        if (form.provinceCityAreaList.length > 1) {
          form.cityName = form.provinceCityAreaList[1];
          form.areaName = form.provinceCityAreaList[2];
        }
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
