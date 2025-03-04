<template>
  <base-dialog v-bind="$attrs">
    <base-header>
      <base-input v-model="listQuery.username" label="账号" @clear="refreshTableData" />
      <base-input v-model="listQuery.nickname" label="昵称" @clear="refreshTableData" />
      <base-input v-model="listQuery.phone" label="手机号" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
    </base-header>

    <base-table-p ref="baseTableRef" api="sys_user.listPage" :params="listQuery" highlight-current-row>
      <el-table-column prop="username" align="center" label="用户账号" />
      <el-table-column prop="nickname" label="用户名称" align="center" />
      <!-- <el-table-column prop="sexName" label="性别" align="center" /> -->
      <el-table-column prop="phone" label="手机号码" align="center" />
      <el-table-column label="角色" :show-overflow-tooltip="true" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.roleNames"> {{ scope.row.roleNames }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="头像" prop="avatarUrl" align="center">
        <template #default="scope">
          <span>
            <img :src="scope.row.avatarUrl" alt="" class="img-sm" />
          </span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230">
        <template #default="scope">
          <el-button link type="warning" @click="chooseUser(scope.row)">选择</el-button>
        </template>
      </el-table-column>
    </base-table-p>
  </base-dialog>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let listQuery = ref({});
async function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
async function chooseUser(row) {
  proxy.$emit('choose', row.userId);
}
</script>
<style lang="scss" scoped></style>
