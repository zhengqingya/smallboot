<template>
  <base-wrapper>
    <base-header>
      <base-cascader
        v-model="listQuery.deptId"
        clearable
        label="归属企业"
        style="margin-right: 10px"
        :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
        api="sys_dept.tree" />
      <base-select v-model="listQuery.categoryId" label="职位分类" style="margin-right: 10px" clearable :option-props="{ label: 'name', value: 'id' }" api="cms_job_category.list" />
      <base-input v-model="listQuery.name" label="职位名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="cms_job.page" :params="listQuery">
        <el-table-column label="ID" prop="id" align="center" />
        <!-- <el-table-column label="归属商户" prop="merchantName" align="center" /> -->
        <el-table-column label="归属企业" prop="deptName" align="center" />
        <el-table-column label="职位分类" prop="categoryName" align="center" />
        <el-table-column label="职位名称" prop="name" align="center" />
        <el-table-column label="状态" align="center">
          <template #default="scope">
            <base-tag v-model="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="招聘人数" prop="userNum" align="center" />
        <el-table-column label="薪资范围" align="center">
          <template #default="scope">
            <span>{{ scope.row.wageStart }}-{{ scope.row.wageEnd }} 元/月</span>
          </template>
        </el-table-column>
        <el-table-column label="排序" prop="sort" align="center" />
        <el-table-column label="创建时间" prop="createTime" align="center" />
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button link @click="handleDetail(scope.row)">详情</el-button>
            <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="60%">
      <el-form ref="dataFormRef" :inline="true" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="归属企业:">
          <base-cascader
            v-if="dialogVisible"
            v-model="form.deptId"
            clearable
            style="width: 100%"
            placeholder="请选择"
            :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
            api="sys_dept.tree" />
        </el-form-item>
        <el-form-item label="职位分类:" prop="categoryId">
          <base-select v-if="dialogVisible" v-model="form.categoryId" :disabled="isDetail" style="width: 100%" clearable :option-props="{ label: 'name', value: 'id' }" api="cms_job_category.list" />
        </el-form-item>
        <el-form-item label="职位名称:">
          <el-input v-model="form.name" :disabled="isDetail" />
        </el-form-item>
        <el-form-item label="联系人:">
          <el-input v-model="form.contact" :disabled="isDetail" />
        </el-form-item>
        <el-form-item label="联系电话:">
          <el-input v-model="form.contactPhone" :disabled="isDetail" />
        </el-form-item>
        <el-form-item label="所属地区:">
          <province-city-area v-model="form.provinceCityAreaList" :disabled="isDetail" />
        </el-form-item>
        <el-form-item label="详细地址:">
          <el-input v-model="form.address" :disabled="isDetail" />
        </el-form-item>
        <el-form-item label="福利标签:" style="width: 100%">
          <base-select
            v-if="dialogVisible"
            v-model="form.tagList"
            :disabled="isDetail"
            tag-type="success"
            style="width: 100%"
            multiple
            clearable
            :option-props="{ label: 'name', value: 'id' }"
            api="cms_job_tag.list" />
        </el-form-item>
        <el-form-item label="薪资范围:">
          <el-input-number v-model="form.wageStart" :disabled="isDetail" :min="1" controls-position="right" placeholder="最低工资" />
          -
          <el-input-number v-model="form.wageEnd" :disabled="isDetail" :min="1" controls-position="right" placeholder="最高工资" />
          元/月
        </el-form-item>
        <el-form-item label="招聘人数:">
          <el-input-number v-model="form.userNum" :disabled="isDetail" :min="1" controls-position="right" />
        </el-form-item>
        <el-form-item label="状态:">
          <base-radio-group v-model="form.status" :disabled="isDetail" />
        </el-form-item>
        <el-form-item label="排序:">
          <el-input-number v-model="form.sort" :disabled="isDetail" :min="1" controls-position="right" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="工作简介:" style="width: 100%">
          <span v-if="isDetail" v-html="form.intro" />
          <base-Editor v-else v-model="form.intro" />
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
let rules = $ref({});
let isDetail = $ref(false);

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleAdd() {
  form = { status: 1, userNum: 1, sort: 100 };
  dialogStatus = 'add';
  isDetail = false;
  dialogVisible = true;
}
function handleDetail(row) {
  form = Object.assign({}, row);
  form.provinceCityAreaList = [form.provinceName, form.cityName, form.areaName];
  dialogStatus = 'detail';
  isDetail = true;
  dialogVisible = true;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  form.provinceCityAreaList = [form.provinceName, form.cityName, form.areaName];
  dialogStatus = 'update';
  isDetail = false;
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.cms_job.delete({ id: row.id });
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
      let res = await proxy.$api.cms_job[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
