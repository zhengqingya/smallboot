<template>
  <base-wrapper>
    <base-header>
      <el-input v-model="listQuery.shopId" placeholder="请输入门店ID" style="width: 200px" clearable @clear="refreshTableData"></el-input>
      <el-input v-model="listQuery.shopName" placeholder="请输入门店名称" style="width: 200px" clearable @clear="refreshTableData"></el-input>
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <router-link to="/mall/shop-edit?isAdd=true">
          <el-button type="primary" @click="handleAdd">添加</el-button>
        </router-link>
      </template>
    </base-header>

    <base-table-p ref="baseTableRef" api="sms_shop.page" :params="listQuery">
      <el-table-column label="门店ID" prop="shopId" align="center"></el-table-column>
      <el-table-column label="租户ID" prop="tenantId" align="center"></el-table-column>
      <el-table-column label="门店名称" prop="shopName" align="center"></el-table-column>
      <el-table-column label="店铺类型" prop="type" align="center"></el-table-column>
      <el-table-column label="是否显示 " prop="isShow" align="center">
        <template #default="scope">
          <span>{{ scope.row.isShow ? '是' : '否' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="堂食状态" prop="snackStatus" align="center">
        <template #default="scope">
          <span>{{ scope.row.snackStatus ? '开启' : '关闭' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="外卖状态" prop="takeoutStatus" align="center">
        <template #default="scope">
          <span>{{ scope.row.takeoutStatus ? '开启' : '关闭' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="门店营业状态" prop="openStatus" align="center">
        <template #default="scope">
          <span>{{ scope.row.openStatus ? '营业中' : '未营业' }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作">
        <template #default="scope">
          <router-link :to="`/mall/shop-edit?shopId=` + scope.row.shopId">
            <el-button link>编辑</el-button>
          </router-link>
          <el-button link @click="handleDetail(scope.row)">详情</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </base-table-p>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="30%">
      <el-form v-if="dialogStatus !== 'detail'" ref="dataFormRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="门店ID:" prop="shopId">
          <el-input v-model="form.shopId"></el-input>
        </el-form-item>
        <el-form-item label="租户ID:" prop="tenantId">
          <el-input v-model="form.tenantId"></el-input>
        </el-form-item>
        <el-form-item label="门店名称:" prop="shopName">
          <el-input v-model="form.shopName"></el-input>
        </el-form-item>
        <el-form-item label="省编码:" prop="provinceCode">
          <el-input v-model="form.provinceCode"></el-input>
        </el-form-item>
        <el-form-item label="市编码:" prop="cityCode">
          <el-input v-model="form.cityCode"></el-input>
        </el-form-item>
        <el-form-item label="区编码:" prop="areaCode">
          <el-input v-model="form.areaCode"></el-input>
        </el-form-item>
        <el-form-item label="省名称:" prop="provinceName">
          <el-input v-model="form.provinceName"></el-input>
        </el-form-item>
        <el-form-item label="市名称:" prop="cityName">
          <el-input v-model="form.cityName"></el-input>
        </el-form-item>
        <el-form-item label="区名称:" prop="areaName">
          <el-input v-model="form.areaName"></el-input>
        </el-form-item>
        <el-form-item label="门店详细地址:" prop="address">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="店铺类型（1:餐饮 2:电商 3:教育）:" prop="type">
          <el-input v-model="form.type"></el-input>
        </el-form-item>
        <el-form-item label="联系人:" prop="contactName">
          <el-input v-model="form.contactName"></el-input>
        </el-form-item>
        <el-form-item label="联系手机号:" prop="contactPhone">
          <el-input v-model="form.contactPhone"></el-input>
        </el-form-item>
        <el-form-item label="门店坐标-经度:" prop="longitude">
          <el-input v-model="form.longitude"></el-input>
        </el-form-item>
        <el-form-item label="门店坐标-纬度:" prop="latitude">
          <el-input v-model="form.latitude"></el-input>
        </el-form-item>
        <el-form-item label="外卖配送费（满?分,配送费?分）:" prop="deliverFeeJson">
          <el-input v-model="form.deliverFeeJson"></el-input>
        </el-form-item>
        <el-form-item label="外卖配送距离（单位：米）:" prop="deliverDistance">
          <el-input v-model="form.deliverDistance"></el-input>
        </el-form-item>
        <el-form-item label="外卖配送范围:" prop="deliverScopeJson">
          <el-input v-model="form.deliverScopeJson"></el-input>
        </el-form-item>
        <el-form-item label="是否显示（1：是 0：否）:" prop="isShow">
          <el-input v-model="form.isShow"></el-input>
        </el-form-item>
        <el-form-item label="堂食状态（1：开启 0：关闭）:" prop="snackStatus">
          <el-input v-model="form.snackStatus"></el-input>
        </el-form-item>
        <el-form-item label="外卖状态（1：开启 0：关闭）:" prop="takeoutStatus">
          <el-input v-model="form.takeoutStatus"></el-input>
        </el-form-item>
        <el-form-item label="门店营业状态（1：营业中 0：未营业）:" prop="openStatus">
          <el-input v-model="form.openStatus"></el-input>
        </el-form-item>
        <el-form-item label="营业时间:" prop="openTimeJson">
          <el-input v-model="form.openTimeJson"></el-input>
        </el-form-item>
        <el-form-item label="一天中开始营业时间点:" prop="startTime">
          <el-input v-model="form.startTime"></el-input>
        </el-form-item>
        <el-form-item label="一天中结束营业时间点:" prop="endTime">
          <el-input v-model="form.endTime"></el-input>
        </el-form-item>
        <el-form-item label="创建时间:" prop="createTime">
          <el-input v-model="form.createTime"></el-input>
        </el-form-item>
        <el-form-item label="更新时间:" prop="updateTime">
          <el-input v-model="form.updateTime"></el-input>
        </el-form-item>
        <el-form-item label="创建人id:" prop="createBy">
          <el-input v-model="form.createBy"></el-input>
        </el-form-item>
        <el-form-item label="更新人id:" prop="updateBy">
          <el-input v-model="form.updateBy"></el-input>
        </el-form-item>
        <el-form-item label="是否删除(0->否,1->是):" prop="isDeleted">
          <el-input v-model="form.isDeleted"></el-input>
        </el-form-item>
      </el-form>
      <base-cell v-else label-width="100px">
        <base-cell-item label="门店ID">{{ form.shopId }}</base-cell-item>
        <base-cell-item label="租户ID">{{ form.tenantId }}</base-cell-item>
        <base-cell-item label="门店名称">{{ form.shopName }}</base-cell-item>
        <base-cell-item label="省编码">{{ form.provinceCode }}</base-cell-item>
        <base-cell-item label="市编码">{{ form.cityCode }}</base-cell-item>
        <base-cell-item label="区编码">{{ form.areaCode }}</base-cell-item>
        <base-cell-item label="省名称">{{ form.provinceName }}</base-cell-item>
        <base-cell-item label="市名称">{{ form.cityName }}</base-cell-item>
        <base-cell-item label="区名称">{{ form.areaName }}</base-cell-item>
        <base-cell-item label="门店详细地址">{{ form.address }}</base-cell-item>
        <base-cell-item label="店铺类型（1:餐饮 2:电商 3:教育）">{{ form.type }}</base-cell-item>
        <base-cell-item label="联系人">{{ form.contactName }}</base-cell-item>
        <base-cell-item label="联系手机号">{{ form.contactPhone }}</base-cell-item>
        <base-cell-item label="门店坐标-经度">{{ form.longitude }}</base-cell-item>
        <base-cell-item label="门店坐标-纬度">{{ form.latitude }}</base-cell-item>
        <base-cell-item label="外卖配送费（满?分,配送费?分）">{{ form.deliverFeeJson }}</base-cell-item>
        <base-cell-item label="外卖配送距离（单位：米）">{{ form.deliverDistance }}</base-cell-item>
        <base-cell-item label="外卖配送范围">{{ form.deliverScopeJson }}</base-cell-item>
        <base-cell-item label="是否显示（1：是 0：否）">{{ form.isShow }}</base-cell-item>
        <base-cell-item label="堂食状态（1：开启 0：关闭）">{{ form.snackStatus }}</base-cell-item>
        <base-cell-item label="外卖状态（1：开启 0：关闭）">{{ form.takeoutStatus }}</base-cell-item>
        <base-cell-item label="门店营业状态（1：营业中 0：未营业）">{{ form.openStatus }}</base-cell-item>
        <base-cell-item label="营业时间">{{ form.openTimeJson }}</base-cell-item>
        <base-cell-item label="一天中开始营业时间点">{{ form.startTime }}</base-cell-item>
        <base-cell-item label="一天中结束营业时间点">{{ form.endTime }}</base-cell-item>
        <base-cell-item label="创建时间">{{ form.createTime }}</base-cell-item>
        <base-cell-item label="更新时间">{{ form.updateTime }}</base-cell-item>
        <base-cell-item label="创建人id">{{ form.createBy }}</base-cell-item>
        <base-cell-item label="更新人id">{{ form.updateBy }}</base-cell-item>
        <base-cell-item label="是否删除(0->否,1->是)">{{ form.isDeleted }}</base-cell-item>
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
function handleAdd() {
  form = {};
  dialogStatus = 'add';
  dialogVisible = true;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  dialogStatus = 'update';
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.sms_shop.delete({ shopId: row.shopId });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.sms_shop[form.shopId ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}
</script>

<style lang="scss" scoped></style>
