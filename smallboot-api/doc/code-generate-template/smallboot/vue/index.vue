<template>
  <base-wrapper>
    <base-header>
        <#if queryColumnInfoList??>
            <#list queryColumnInfoList as item>
              <base-input v-model="listQuery.${item.columnNameJavaLower}" label="${item.columnComment}" @clear="refreshTableData" />
            </#list>
        </#if>
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTableRef" api="${vueApiName}.page" :params="listQuery">
        <#list columnInfoList as item>
            <#if item.columnTypeJava == "DateX">
              <el-table-column label="${item.columnComment}" align="center">
                <template slot-scope="scope">
                  <span>{{scope.row.${item.columnNameJavaLower}|dateTimeFilter}}</span>
                </template>
              </el-table-column>
            <#else>
              <el-table-column label="${item.columnComment}" prop="${item.columnNameJavaLower}" align="center" />
            </#if>
        </#list>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button link @click="handleDetail(scope.row)">详情</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)" />
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="30%">
      <el-form ref="dataFormRef" :inline="true"  v-if="dialogStatus !== 'detail'" :model="dataForm" :rules="rules" label-width="100px">
          <#list columnInfoList as item>
            <el-form-item label="${item.columnComment}:" >
              <el-input v-model="dataForm.${item.columnNameJavaLower}" />
            </el-form-item>
          </#list>
      </el-form>
      <base-cell label-width="120px" v-else>
            <#list columnInfoList as item>
                  <base-cell-item label="${item.columnComment}">{{ dataForm.${item.columnNameJavaLower} }}</base-cell-item>
            </#list>
      </base-cell>
      <template #footer v-if="dialogStatus !== 'detail'">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </base-dialog>
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let listQuery = $ref({});
let dataForm = $ref({});
let dialogVisible = $ref(false);
let dialogStatus = $ref('');
let rules = $ref({});

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleDetail(row) {
  dataForm = Object.assign({}, row);
  dialogStatus = 'detail';
  dialogVisible = true;
}
function handleAdd() {
  dataForm = {  };
  dialogStatus = 'add';
  dialogVisible = true;
}
function handleUpdate(row) {
  dataForm = Object.assign({}, row);
  dialogStatus = 'update';
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.${vueApiName}.delete({id: row.id});
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.${vueApiName}[dataForm.id ? "update" : "add"](dataForm);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
