<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.apiMethod" label="请求方法" @clear="refreshTableData" />
      <base-input v-model="listQuery.requestUrl" label="请求url" @clear="refreshTableData" />
      <!-- <base-input v-model="listQuery.apiMethodName" label="请求方法名" @clear="refreshTableData" /> -->
      <base-input v-model="listQuery.operationName" label="操作人名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <!-- <el-button type="primary" @click="handleAdd">添加</el-button> -->
      </template>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="sys_log.page" :params="listQuery">
        <el-table-column label="ID" prop="id" align="center" />
        <!-- <el-table-column label="类型(1:操作日志 2:登录日志)" prop="type" align="center" /> -->
        <!-- <el-table-column label="请求方法" prop="apiMethod" width="500px" align="center" /> -->
        <el-table-column label="请求方法名" prop="apiMethodName" width="300px" align="left" />
        <!-- <el-table-column label="请求头参数" prop="apiHeader" align="center" /> -->
        <el-table-column label="操作人名称" prop="operationName" align="center" />
        <el-table-column label="请求IP" prop="requestIp" align="center" />
        <el-table-column label="请求url" prop="requestUrl" width="350px" align="left" />
        <!-- <el-table-column label="请求方式" prop="requestHttpMethod" align="center" /> -->
        <!-- <el-table-column label="请求参数" prop="requestParams" align="center" /> -->
        <!-- <el-table-column label="服务器环境" prop="env" align="center" /> -->
        <el-table-column label="执行时间" prop="time" align="center">
          <template #default="scope">
            <span>{{ scope.row.time }}毫秒</span>
          </template>
        </el-table-column>
        <el-table-column label="操作时间" prop="createTime" align="center" />
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <!-- <el-button link @click="handleUpdate(scope.row)">编辑</el-button> -->
            <el-button link @click="handleDetail(scope.row)">详情</el-button>
            <!-- <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn> -->
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="50%">
      <base-cell label-width="150px">
        <base-cell-item label="主键ID">{{ form.id }}</base-cell-item>
        <!-- <base-cell-item label="类型(1:操作日志 2:登录日志)">{{ form.type }}</base-cell-item> -->
        <base-cell-item label="请求方法">{{ form.apiMethod }}</base-cell-item>
        <base-cell-item label="请求方法名">{{ form.apiMethodName }}</base-cell-item>
        <!-- <base-cell-item label="请求头参数">{{ form.apiHeader }}</base-cell-item> -->
        <base-cell-item label="操作人名称">{{ form.operationName }}</base-cell-item>
        <base-cell-item label="请求IP">{{ form.requestIp }}</base-cell-item>
        <base-cell-item label="请求url">{{ form.requestUrl }}</base-cell-item>
        <base-cell-item label="请求方式">{{ form.requestHttpMethod }}</base-cell-item>
        <base-cell-item label="请求参数">
          <el-scrollbar>{{ form.requestParams }} </el-scrollbar>
        </base-cell-item>
        <base-cell-item label="服务器环境">{{ form.env }}</base-cell-item>
        <base-cell-item label="执行时间(单位：毫秒)">{{ form.time }}</base-cell-item>
        <base-cell-item label="操作时间">{{ form.createTime }}</base-cell-item>
      </base-cell>
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

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleDetail(row) {
  form = Object.assign({}, row);
  dialogStatus = 'detail';
  dialogVisible = true;
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.sys_log[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
