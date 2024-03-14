<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.apiMethodName" clearable placeholder="请求方法名" @clear="refreshTableData" />
      <base-input v-model="listQuery.requestParams" clearable placeholder="请求参数" @clear="refreshTableData" />
      <base-input v-model="listQuery.requestUrl" clearable placeholder="请求url" @clear="refreshTableData" />
      <base-input v-model="listQuery.operationName" clearable placeholder="操作人名称" @clear="refreshTableData" />

      <el-select v-model="listQuery.status" placeholder="状态" clearable style="width: 144px; margin-right: 16px" @clear="refreshTableData" @change="refreshTableData">
        <template #prefix>
          <el-icon> <Guide /> </el-icon>
        </template>
        <el-option
          v-for="item in [
            { id: 1, name: '正常' },
            { id: 0, name: '异常' },
          ]"
          :key="item.id"
          :label="item.name"
          :value="item.id">
          <slot name="option" :data="item" />
        </el-option>
      </el-select>

      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <!-- <el-button type="primary" @click="handleAdd">添加</el-button> -->
        <!-- <base-btn-ok @ok="deleteDataBeforeDay">
          <span style="color: #f36161">清理3天前的日志</span>
        </base-btn-ok> -->
      </template>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="sys_log.page" :params="listQuery">
        <el-table-column label="ID" prop="id" align="left" width="90px" />
        <!-- <el-table-column label="类型(1:操作日志 2:登录日志)" prop="type" align="left" /> -->
        <!-- <el-table-column label="请求方法" prop="apiMethod" width="500px" align="left" /> -->
        <el-table-column label="请求方法名" prop="apiMethodName" width="350px" align="left" />
        <!-- <el-table-column label="请求头参数" prop="apiHeader" align="left" /> -->
        <el-table-column label="操作人名称" prop="operationName" align="left" width="120px" />
        <el-table-column label="请求IP" prop="requestIp" align="left" width="150px" />
        <el-table-column label="请求url" prop="requestUrl" width="350px" align="left" />
        <!-- <el-table-column label="请求方式" prop="requestHttpMethod" align="left" /> -->
        <!-- <el-table-column label="请求参数" prop="requestParams" align="left" /> -->
        <!-- <el-table-column label="服务器环境" prop="env" align="left" /> -->
        <el-table-column label="执行时间" prop="time" align="left" width="100px">
          <template #default="scope">
            <span>{{ scope.row.time }}毫秒</span>
          </template>
        </el-table-column>
        <el-table-column label="操作时间" prop="createTime" align="left" width="180px" />
        <el-table-column label="状态" align="left">
          <template #default="scope">
            <el-tooltip :content="scope.row.responseResult">
              <el-tag :type="scope.row.status == 1 ? 'success' : 'danger'"> {{ scope.row.status == 1 ? '正常' : '异常' }} </el-tag>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column align="left" label="操作">
          <template #default="scope">
            <!-- <el-button link @click="handleUpdate(scope.row)">编辑</el-button> -->
            <el-button link @click="handleDetail(scope.row)">详情</el-button>
            <!-- <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn> -->
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>

    <base-dialog v-model="dialogVisible" :close-on-click-modal="true" :title="dialogTitleObj[dialogStatus]" width="658px">
      <base-cell label-width="80px">
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
          <el-scrollbar>
            {{ form.requestParams }}
          </el-scrollbar>
        </base-cell-item>
        <base-cell-item label="服务器环境">{{ form.env }}</base-cell-item>
        <base-cell-item label="执行时间">{{ form.time }}毫秒</base-cell-item>
        <base-cell-item label="操作时间">{{ form.createTime }}</base-cell-item>
        <base-cell-item label="状态">
          <el-tag :type="form.status == 1 ? 'primary' : 'danger'">{{ form.status == 1 ? '正常' : '异常' }}</el-tag>
        </base-cell-item>
        <base-cell-item label="响应结果">
          <el-scrollbar>
            {{ form.responseResult }}
          </el-scrollbar>
        </base-cell-item>
      </base-cell>
      <template #footer>
        <el-button @click="dialogVisible = false">关闭</el-button>
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
async function deleteDataBeforeDay() {
  let res = await proxy.$api.sys_log.deleteDataBeforeDay({ day: 3 });
  proxy.submitOk(res.message);
  refreshTableData();
}
</script>

<style lang="scss" scoped></style>
