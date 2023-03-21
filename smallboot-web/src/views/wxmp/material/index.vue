<template>
  <base-wraper>
    <base-header>
      <el-select v-model="listQuery.type" placeholder="请选择">
        <el-option v-for="item in materialTypeList" :key="item.value" :label="item.name" :value="item.value" />
      </el-select>
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <!-- <el-button type="primary" @click="handleAdd">添加</el-button> -->
      </template>
    </base-header>

    <base-table-p ref="baseTable" api="wx_mp_material.page" :params="listQuery">
      <el-table-column label="mediaId" prop="mediaId" align="center"></el-table-column>
      <el-table-column label="名称" prop="name" align="center"></el-table-column>
      <el-table-column label="更新时间" prop="updateTime" align="center"></el-table-column>
      <el-table-column label="url" prop="url" align="center">
        <template v-slot="scope">
          <el-image style="width: 100px; height: 100px" :src="scope.row.url" />
        </template>
      </el-table-column>
    </base-table-p>
  </base-wraper>
</template>

<script>
export default {
  name: 'WxMpmaterial',
  data() {
    return {
      listQuery: {
        type: 'image',
      },
      form: {},
      materialTypeList: [
        // { value: 'news', name: '图文' },
        { value: 'voice', name: '语音' },
        { value: 'image', name: '图片' },
        { value: 'video', name: '视频' },
      ],
      dialogVisible: false,
      dialogStatus: '',
      titleMap: {
        add: '添加',
        update: '编辑',
        detail: '详情',
      },
      rules: {},
    }
  },
  created() {},
  methods: {
    refreshTableData() {
      this.$refs.baseTable.refresh()
    },
    handleAdd() {
      this.form = Object.assign({}, {})
      this.dialogStatus = 'add'
      this.dialogVisible = true
    },
  },
}
</script>
<style scoped></style>
