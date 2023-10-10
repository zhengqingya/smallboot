<template>
  <base-wrapper>
    <base-header>
      <base-cascader
        v-model="listQuery.deptId"
        clearable
        style="margin-right: 10px"
        label="归属部门"
        :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
        api="sys_dept.tree" />
      <base-input v-model="listQuery.name" label="职位名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTableRef" api="cms_job.page" :params="listQuery">
      <el-table-column label="ID" prop="id" align="center" />
      <el-table-column label="归属部门" prop="deptName" align="center" />
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
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="60%">
      <el-form ref="dataFormRef" :inline="true" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="归属部门:" style="width: 100%">
          <base-cascader
            v-if="dialogVisible"
            v-model="form.deptId"
            style="width: 100%"
            clearable
            placeholder="请选择"
            :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
            api="sys_dept.tree" />
        </el-form-item>
        <el-form-item label="职位名称:">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="联系人:">
          <el-input v-model="form.contact" />
        </el-form-item>
        <el-form-item label="联系电话:">
          <el-input v-model="form.contactPhone" />
        </el-form-item>
        <el-form-item label="所属地区:">
          <province-city-area v-model="form.provinceCityAreaList" />
        </el-form-item>
        <el-form-item label="详细地址:">
          <el-input v-model="form.address" />
        </el-form-item>
        <el-form-item label="福利标签:" style="width: 100%">
          <base-select v-if="dialogVisible" v-model="form.tagList" tag-type="success" style="width: 100%" multiple clearable :option-props="{ label: 'name', value: 'id' }" api="cms_job_tag.list" />
        </el-form-item>

        <el-form-item label="薪资范围:">
          <el-input-number v-model="form.wageStart" :min="1" controls-position="right" placeholder="最低工资" />
          -
          <el-input-number v-model="form.wageEnd" :min="1" controls-position="right" placeholder="最高工资" />
          元/月
        </el-form-item>
        <el-form-item label="招聘人数:">
          <el-input-number v-model="form.userNum" :min="1" controls-position="right" />
        </el-form-item>
        <el-form-item label="状态:">
          <base-radio-group v-model="form.status" />
        </el-form-item>
        <el-form-item label="排序:">
          <el-input-number v-model="form.sort" :min="1" controls-position="right" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="工作简介:">
          <base-Editor v-model="form.intor" />
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

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleAdd() {
  form = { status: 1, userNum: 1, sort: 100 };
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
