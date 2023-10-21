<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.username" label="账号" @clear="refreshTableData" />
      <base-input v-model="listQuery.nickname" label="昵称" @clear="refreshTableData" />
      <base-input v-model="listQuery.phone" label="手机号" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="sys_user.listPage" :params="listQuery">
        <el-table-column prop="username" align="center" label="用户账号" />
        <el-table-column prop="nickname" label="用户名称" align="center" />
        <!-- <el-table-column prop="sexName" label="性别" align="center" /> -->
        <el-table-column prop="phone" label="手机号码" align="center" />
        <el-table-column label="角色" :show-overflow-tooltip="true" align="center">
          <template #default="scope">
            <el-tag> {{ scope.row.roLeNames }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="头像" prop="avatarUrl" align="center">
          <template #default="scope">
            <span>
              <img :src="scope.row.avatarUrl" alt="" class="img-sm" />
            </span>
          </template>
        </el-table-column>
        <el-table-column label="小程序路径" :show-overflow-tooltip="true" align="center">
          <template #default="scope">
            <el-tag type="warning"> pages/index/index?shareSysUserId={{ scope.row.userId }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <base-btn link @ok="unBindAnchor(scope.row)">解绑</base-btn>
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>
  </base-wrapper>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let listQuery = ref({ isBindMiniUser: true });
async function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
async function unBindAnchor(row) {
  let res = await proxy.$api.sys_user.bindMiniUser({ isBind: false, userId: row.userId, miniUserId: row.miniUserId });
  proxy.submitOk(res.msg);
  refreshTableData();
}
</script>
<style lang="scss" scoped></style>
