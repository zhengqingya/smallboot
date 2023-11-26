<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.id" label="商户ID" @clear="refreshTableData" />
      <base-input v-model="listQuery.name" label="名称" @clear="refreshTableData" />
      <base-select
        v-model="listQuery.type"
        :data-list="typeList"
        style="margin-right: 10px"
        label="商户类型"
        tag-type="success"
        clearable
        :option-props="{ label: 'name', value: 'id' }"
        @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <base-content>
      <base-table-p ref="baseTableRef" api="sys_merchant.page" :params="listQuery">
        <el-table-column label="商户ID" prop="id" align="center" />
        <el-table-column label="名称" prop="name" align="center" />
        <el-table-column label="抖音AppID" prop="appId" width="150px" align="center" />
        <el-table-column label="抖音AppSecret" prop="appSecret" width="150px" align="center" />
        <el-table-column label="状态" prop="status" align="center">
          <template #default="scope">
            <base-tag v-model="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="过期时间" prop="expireTime" align="center" />
        <el-table-column label="商户类型" prop="type" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.type == 1" type="success">共享小程序</el-tag>
            <el-tag v-else-if="scope.row.type == 2" type="info">独立小程序</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="用户数" prop="userNum" align="center" />
        <el-table-column label="发布数" prop="jobNum" align="center" />
        <el-table-column label="排序" prop="sort" align="center" />
        <el-table-column label="小程序首页名称" prop="appIndexTitle" align="center" />
        <el-table-column label="备注" prop="remark" align="center" />
        <!-- <el-table-column label="小程序状态" prop="appStatus" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.appStatus == 20">提审中</el-tag>
          <el-tag v-else-if="scope.row.appStatus == 31" type="warning">审核通过</el-tag>
          <el-tag v-else-if="scope.row.appStatus == 32" type="danger">审核不通过</el-tag>
          <el-tag v-else-if="scope.row.appStatus == 50" type="info">发布中</el-tag>
          <el-tag v-else-if="scope.row.appStatus == 51" type="success">已发布</el-tag>
        </template>
      </el-table-column> -->
        <el-table-column align="center" width="150px" label="操作">
          <template #default="scope">
            <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
            <el-button type="primary" link @click="showQrcode(scope.row)">查看测试版二维码</el-button>
            <base-delete-btn v-if="!scope.row.isFixed" @ok="handleDelete(scope.row)"></base-delete-btn>
          </template>
        </el-table-column>
      </base-table-p>
    </base-content>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="50%">
      <el-form v-if="dialogStatus !== 'detail'" ref="dataFormRef" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="商户ID:">
          <el-input v-if="form.id" v-model="form.id" disabled />
          <el-input v-else v-model="form.customId" :disabled="form.id" placeholder="不填则由系统自动生成" />
        </el-form-item>
        <el-form-item label="名称:">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="抖音AppID:">
          <el-input v-model="form.appId" placeholder="从抖音开放平台中获取" />
        </el-form-item>
        <el-form-item label="抖音AppSecret:">
          <el-input v-model="form.appSecret" placeholder="从抖音开放平台中获取" />
        </el-form-item>
        <el-form-item label="小程序首页头部名称:">
          <el-input v-model="form.appIndexTitle" placeholder="不填则默认为: 首页（只在发布小程序后才生效）" />
        </el-form-item>
        <el-form-item label="状态:">
          <base-radio-group v-model="form.status" />
        </el-form-item>
        <el-form-item label="商户类型:">
          <base-select v-model="form.type" :data-list="typeList" style="margin-right: 10px" tag-type="success" clearable :option-props="{ label: 'name', value: 'id' }" />
        </el-form-item>
        <el-form-item label="过期时间:">
          <el-date-picker v-model="form.expireTime" type="datetime" placeholder="请选择" format="YYYY-MM-DD hh:mm:ss" value-format="YYYY-MM-DD hh:mm:ss" />
        </el-form-item>
        <el-form-item v-if="dialogStatus == 'add'" label="账号:">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item v-if="dialogStatus == 'add'" label="密码:">
          <el-input v-model="form.password" />
        </el-form-item>
        <el-form-item label="用户数:">
          <el-input-number v-model="form.userNum" :min="1" controls-position="right" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="发布数:">
          <el-input-number v-model="form.jobNum" :min="1" controls-position="right" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="排序:">
          <el-input-number v-model="form.sort" :min="1" controls-position="right" placeholder="请输入" />
        </el-form-item>
        <el-form-item label="备注:">
          <el-input v-model="form.remark" :rows="2" type="textarea" />
        </el-form-item>
      </el-form>
      <template v-if="dialogStatus !== 'detail'" #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </base-dialog>

    <base-dialog v-model="qrcodeDialogVisible" class="" title="小程序测试版二维码" width="350px">
      <el-image style="width: 300px; height: 300px" :src="qrcodeUrl" />
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
let typeList = $ref([
  { id: 1, name: '共享小程序' },
  { id: 2, name: '独立小程序' },
]);
let qrcodeDialogVisible = $ref(false);
let qrcodeUrl = $ref('');

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleAdd() {
  form = { status: 1, sort: 100 };
  dialogStatus = 'add';
  dialogVisible = true;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  dialogStatus = 'update';
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.sys_merchant.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.sys_merchant[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}

async function showQrcode(row) {
  let res = await proxy.$api.sys_merchant.qrcode({ id: row.id, version: 'latest' });
  qrcodeUrl = res.data;
  qrcodeDialogVisible = true;
}
</script>

<style lang="scss" scoped></style>
