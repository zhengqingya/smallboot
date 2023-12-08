<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.key" label="流程标识" @clear="refreshTableData" />
      <base-input v-model="listQuery.name" label="流程名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <router-link :to="{ path: '/workflow/model-edit' }">
          <el-button type="primary">添加</el-button>
        </router-link>
      </template>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="wf_model.page" :params="listQuery">
        <el-table-column label="ID" prop="id" align="center" />
        <el-table-column label="名称" prop="name" align="center" />
        <el-table-column label="标识" prop="key" align="center" />
        <el-table-column label="表单" prop="" align="center">
          <template #default="scope">{{ scope.row.formObj.description }}</template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" align="center" />
        <el-table-column align="center" label="操作">
          <template #default="scope">
            <router-link :to="{ path: '/workflow/model-edit', query: { id: scope.row.id } }">
              <el-button link>编辑</el-button>
            </router-link>
            <base-delete-btn @ok="handleDelete(scope.row)" />
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let listQuery = $ref({});

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}

async function handleDelete(row) {
  let res = await proxy.$api.wf_form.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
</script>

<style lang="scss" scoped></style>
