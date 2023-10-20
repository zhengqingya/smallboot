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
      <!-- <base-input v-model="listQuery.jobId" label="职位id" @clear="refreshTableData" /> -->
      <base-select
        v-model="listQuery.status"
        :data-list="[
          { id: 1, name: '待录取' },
          { id: 2, name: '录取' },
          { id: 3, name: '拒绝' },
        ]"
        style="margin-right: 10px"
        label="状态"
        tag-type="success"
        clearable
        :option-props="{ label: 'name', value: 'id' }"
        @clear="refreshTableData" />
      <base-input v-model="listQuery.contact" label="联系人" @clear="refreshTableData" />
      <base-input v-model="listQuery.contactPhone" label="联系电话" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <!-- <el-button type="primary" @click="handleAdd">添加</el-button> -->
      </template>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="cms_job_apply.page" :params="listQuery">
        <el-table-column label="ID" prop="id" align="center" />
        <!-- <el-table-column label="归属商户" prop="merchantName" align="center" /> -->
        <el-table-column label="归属企业" prop="deptName" align="center" />
        <el-table-column label="职位名称" prop="jobObj.name" align="center" />
        <el-table-column label="状态" prop="statusName" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.status == 1" type="success"> {{ scope.row.statusName }}</el-tag>
            <el-tag v-else-if="scope.row.status == 2" type="warning"> {{ scope.row.statusName }}</el-tag>
            <el-tag v-else type="info"> {{ scope.row.statusName }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="联系人" prop="contact" align="center" />
        <el-table-column label="联系电话" prop="contactPhone" align="center" />
        <el-table-column label="备注" prop="remark" align="center" />
        <el-table-column label="创建时间" prop="createTime" align="center" />
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <!-- <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button link @click="handleDetail(scope.row)">详情</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn> -->
            <el-button v-if="scope.row.status == 1" link @click="handleUpdate(scope.row)">操作</el-button>
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="40%">
      <el-form v-if="dialogStatus !== 'detail'" ref="dataFormRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="职位:">
          <base-select v-if="dialogVisible" v-model="form.jobId" disabled style="width: 100%" :option-props="{ label: 'name', value: 'id' }" api="cms_job.list" />
        </el-form-item>
        <el-form-item label="状态:">
          <el-input v-model="form.statusName" disabled />
        </el-form-item>
        <el-form-item label="联系人:">
          <el-input v-model="form.contact" disabled />
        </el-form-item>
        <el-form-item label="联系电话:">
          <el-input v-model="form.contactPhone" disabled />
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form.remark" disabled />
        </el-form-item>
      </el-form>
      <template v-if="dialogStatus !== 'detail'" #footer>
        <el-button @click="dialogVisible = false">返回</el-button>
        <el-button type="warning" @click="submitForm(3)">拒绝</el-button>
        <el-button type="primary" @click="submitForm(2)">录取</el-button>
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
function handleUpdate(row) {
  form = Object.assign({}, row);
  dialogStatus = 'update';
  dialogVisible = true;
}
function submitForm(status) {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      form.status = status;
      let res = await proxy.$api.cms_job_apply[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
