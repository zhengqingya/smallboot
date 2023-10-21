<template>
  <base-wrapper>
    <base-header>
      <el-input v-model="listQuery.shopId" placeholder="请输入门店ID" style="width: 200px" clearable @clear="refreshTableData"></el-input>
      <el-input v-model="listQuery.shopName" placeholder="请输入门店名称" style="width: 200px" clearable @clear="refreshTableData"></el-input>
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <router-link to="/mall/shop-edit?isAdd=true">
          <el-button type="primary">添加</el-button>
        </router-link>
      </template>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="sms_shop.page" :params="listQuery">
        <el-table-column label="门店ID" prop="shopId" align="center"></el-table-column>
        <el-table-column label="门店名称" prop="shopName" align="center"></el-table-column>
        <!-- <el-table-column label="店铺类型" prop="type" align="center"></el-table-column> -->
        <el-table-column label="是否显示 " prop="isShow" align="center">
          <template #default="scope">
            <base-switch
              v-model="scope.row.isShow"
              api="sms_shop.updateBatchStatus"
              :params="{
                shopIdList: [scope.row.shopId],
                isShow: !scope.row.isShow,
              }"
              :active-action-icon="View"
              :inactive-action-icon="Hide" />
          </template>
        </el-table-column>
        <el-table-column label="堂食状态" prop="snackStatus" align="center">
          <template #default="scope">
            <base-switch
              v-model="scope.row.snackStatus"
              api="sms_shop.updateBatchStatus"
              :params="{
                shopIdList: [scope.row.shopId],
                snackStatus: !scope.row.snackStatus,
              }"
              on-color="#13ce66" />
          </template>
        </el-table-column>
        <el-table-column label="外卖状态" prop="takeoutStatus" align="center">
          <template #default="scope">
            <base-switch
              v-model="scope.row.takeoutStatus"
              api="sms_shop.updateBatchStatus"
              :params="{
                shopIdList: [scope.row.shopId],
                takeoutStatus: !scope.row.takeoutStatus,
              }"
              on-color="rgb(241, 99, 211)" />
          </template>
        </el-table-column>
        <el-table-column label="门店营业状态" prop="openStatus" align="center">
          <template #default="scope">
            <base-switch
              v-model="scope.row.openStatus"
              api="sms_shop.updateBatchStatus"
              :params="{
                shopIdList: [scope.row.shopId],
                openStatus: !scope.row.openStatus,
              }"
              on-color="#E6A23C" />
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作">
          <template #default="scope">
            <router-link :to="`/mall/shop-edit?shopId=` + scope.row.shopId">
              <el-button link>编辑</el-button>
            </router-link>
            <router-link :to="`/mall/shop-edit?isDetail=true&shopId=` + scope.row.shopId">
              <el-button link>详情</el-button>
            </router-link>
            <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
import { Hide, View } from '@element-plus/icons-vue';
let listQuery = $ref({});

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
async function handleDelete(row) {
  let res = await proxy.$api.sms_shop.delete({ shopId: row.shopId });
  refreshTableData();
  proxy.submitOk(res.message);
}
</script>

<style lang="scss" scoped></style>
