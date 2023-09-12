<template>
  <base-wraper>
    <base-header>
        <#if queryColumnInfoList??>
            <#list queryColumnInfoList as item>
              <el-input v-model="listQuery.${item.columnNameJavaLower}"
                        placeholder="请输入${item.columnComment}" style="width:200px" clearable
                        @clear="refreshTableData"></el-input>
            </#list>
        </#if>
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="${vueApiName}.page" :params="listQuery">
        <#list columnInfoList as item>
            <#if item.columnTypeJava == "Date">
              <el-table-column label="${item.columnComment}" align="center">
                <template slot-scope="scope">
                  <span>{{scope.row.${item.columnNameJavaLower}|dateTimeFilter}}</span>
                </template>
              </el-table-column>
            <#else>
              <el-table-column label="${item.columnComment}" prop="${item.columnNameJavaLower}"
                               align="center"></el-table-column>
            </#if>
        </#list>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button link @click="handleDetail(scope.row)">详情</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="titleMap[dialogStatus]" width="30%">
      <el-form ref="dataForm" v-if="dialogStatus !== 'detail'" :model="form" :rules="rules" label-width="100px">
          <#list columnInfoList as item>
            <el-form-item label="${item.columnComment}:" prop="${item.columnNameJavaLower}">
              <el-input v-model="form.${item.columnNameJavaLower}"></el-input>
            </el-form-item>
          </#list>
      </el-form>
      <base-table-cell label-width="100px" v-else>
            <#list columnInfoList as item>
                  <base-cell-item label="${item.columnComment}">{{ form.${item.columnNameJavaLower} }}</base-cell-item>
            </#list>
      </base-table-cell>
      <template #footer v-if="dialogStatus !== 'detail'">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </base-dialog>
  </base-wraper>
</template>

<script>
  export default {
    name: '${entity}',
    data() {
      return {
        listQuery: {
          <#list queryColumnInfoList as item>
          ${item.columnNameJavaLower}: undefined, // ${item.columnComment}
          </#list>
        },
        form: {
          <#list columnInfoList as item>
          ${item.columnNameJavaLower}: undefined, // ${item.columnComment}
          </#list>
        },
        dialogVisible: false,
        dialogStatus: "",
        titleMap: {
          add: "添加",
          update: "编辑",
          detail: "详情"
        },
        rules: {  }
      }
    },
    created() {
    },
    methods: {
      refreshTableData() {
        this.$refs.baseTable.refresh();
      },
      handleDetail(row) {
        this.form = Object.assign({}, row)
        this.dialogStatus = 'detail'
        this.dialogVisible = true
      },
      handleAdd() {
        this.form = Object.assign({}, {});
        this.dialogStatus = "add";
        this.dialogVisible = true;
      },
      handleUpdate(row) {
        this.form = Object.assign({}, row);
        this.dialogStatus = "update";
        this.dialogVisible = true;
      },
      async handleDelete(row) {
        let res = await this.$api.${vueApiName}.delete({id: row.id});
        this.refreshTableData();
        this.submitOk(res.message);
      },
      submitForm() {
        this.$refs.dataForm.validate(async valid => {
          if (valid) {
            let res = await this.$api.${vueApiName}[this.form.id ? "update" : "add"](this.form);
            this.submitOk(res.message);
            this.refreshTableData();
            this.dialogVisible = false;
          }
        });
      }
    }
  }
</script>
<style scoped></style>
