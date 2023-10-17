<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.name" label="名称" @clear="refreshTableData" />
      <base-select
        v-model="listQuery.appType"
        :data-list="appTypeList"
        style="margin-right: 10px"
        label="小程序类型"
        tag-type="success"
        clearable
        :option-props="{ label: 'name', value: 'id' }"
        @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <el-table row-key="id" border :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" :data="dataList" default-expand-all>
      <el-table-column label="ID" prop="id" align="center" />
      <el-table-column label="名称" prop="name" align="center" />
      <el-table-column label="状态" align="center">
        <template #default="scope">
          <base-tag v-model="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="小程序类型" prop="appType" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.appType == 1" type="success"> {{ appTypeList.find((e) => e.id == scope.row.appType).name }}</el-tag>
          <el-tag v-if="scope.row.appType === 2"> {{ appTypeList.find((e) => e.id == scope.row.appType).name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="过期时间" prop="expireTime" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.expireTime" type="warning"> {{ scope.row.expireTime }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="最大用户数" prop="userNum" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.userNum" type="success"> {{ scope.row.userNum }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="排序" prop="sort" align="center" />
      <el-table-column label="创建时间" prop="createTime" align="center" />
      <el-table-column align="center" label="操作">
        <template #default="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button link type="primary" @click="showDetail(scope.row)">详情</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </el-table>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="60%" top="0px">
      <el-form ref="dataFormRef" :inline="!true" :model="form" :rules="rules" label-width="150px">
        <!-- <el-form-item label="父部门:">
          <base-cascader
            v-if="dialogVisible"
            v-model="form.parentId"
            clearable
            :params="{ excludeDeptId: form.id }"
            placeholder="请选择(为空时标识顶级)"
            :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
            api="sys_dept.tree" />
        </el-form-item> -->
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
          <el-form-item label="小程序类型:">
            <base-select v-model="form.appType" :disabled="isDetail" :data-list="appTypeList" style="margin-right: 10px" tag-type="success" clearable :option-props="{ label: 'name', value: 'id' }" />
          </el-form-item>
          <el-form-item label="过期时间:">
            <el-date-picker v-model="form.expireTime" :disabled="isDetail" type="datetime" placeholder="请选择" format="YYYY-MM-DD hh:mm:ss" value-format="YYYY-MM-DD hh:mm:ss" />
          </el-form-item>
          <el-form-item label="最大员工数:">
            <el-input-number v-model="form.userNum" :disabled="isDetail" :min="1" controls-position="right" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="最大发布数:">
            <el-input-number v-model="form.jobNum" :disabled="isDetail" :min="1" controls-position="right" placeholder="请输入" />
          </el-form-item>
          <el-form-item label="抖音AppID:">
            <el-input v-model="form.appId" :disabled="isDetail" placeholder="从抖音开放平台中获取" />
          </el-form-item>
          <el-form-item label="抖音AppSecret:">
            <el-input v-model="form.appSecret" :disabled="isDetail" placeholder="从抖音开放平台中获取" />
          </el-form-item>
          <el-form-item label="小程序首页头部名称:">
            <el-input v-model="form.appIndexTitle" :disabled="isDetail" placeholder="不填则默认为: 首页（只在发布小程序后才生效）" />
          </el-form-item>
        </div>
        <el-form-item label="排序:">
          <el-input-number v-model="form.sort" :min="1" :disabled="isDetail" controls-position="right" placeholder="请输入排序" />
        </el-form-item>
      </el-form>
      <el-form-item label="备注:">
        <el-input v-model="form.remark" :row="2" :disabled="isDetail" type="textarea" />
      </el-form-item>
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
let appTypeList = $ref([
  { id: 1, name: '共享版' },
  { id: 2, name: '独立版' },
]);

onMounted(() => {
  refreshTableData();
});

async function refreshTableData() {
  let res = await proxy.$api.sys_dept.tree(listQuery);
  dataList = res.data;
}
function handleAdd() {
  form = { status: 1, sort: 100, parentId: 0, appType: 2 };
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
      if (!form.parentId) {
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
