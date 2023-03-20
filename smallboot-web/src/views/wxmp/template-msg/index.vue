<template>
  <base-wraper>
    <base-header>
      <el-input v-model="listQuery.title" placeholder="请输入模板标题" style="width: 200px" clearable @clear="refreshTableData"></el-input>
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleSync">同步公众号模板数据</el-button>
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="wx_mp_template_msg.page" :params="listQuery">
      <el-table-column label="AppID" prop="appId" align="center"></el-table-column>
      <el-table-column label="模板ID" prop="tplId" align="center"></el-table-column>
      <el-table-column label="模板标题" prop="title" align="center"></el-table-column>
      <el-table-column label="模板内容" prop="content" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" label="操作">
        <template v-slot="scope">
          <el-button link @click="handleDetail(scope.row)">详情</el-button>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="titleMap[dialogStatus]" width="50%">
      <base-table-cell label-width="100px">
        <base-cell-item label="AppID11">{{ form.appId }}</base-cell-item>
        <base-cell-item label="模板ID">{{ form.tplId }}</base-cell-item>
        <base-cell-item label="模板标题">{{ form.title }}</base-cell-item>
        <base-cell-item label="模板内容">{{ form.content }}</base-cell-item>
      </base-table-cell>
      <template #footer v-if="dialogStatus !== 'detail'">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </template>
    </base-dialog>
  </base-wraper>
</template>

<script>
export default {
  name: 'WxMpTemplateMsg',
  data() {
    return {
      listQuery: {},
      form: {},
      dialogVisible: false,
      dialogStatus: '',
      titleMap: {
        detail: '详情',
      },
    }
  },
  created() {},
  methods: {
    refreshTableData() {
      this.$refs.baseTable.refresh()
    },
    handleDetail(row) {
      this.form = Object.assign({}, row)
      this.dialogStatus = 'detail'
      this.dialogVisible = true
    },
    async handleSync() {
      let res = await this.$api.wx_mp_template_msg.sync()
      this.submitOk(res.message)
      this.refreshTableData()
    },
  },
}
</script>
<style scoped></style>
