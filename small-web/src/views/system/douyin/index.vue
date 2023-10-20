<template>
  <base-wrapper class="flex">
    <div class="flex-column">
      <base-card title="抖音服务商平台" style="width: 588px">
        <template #append>
          <el-button type="warning" @click="saveBatch">保 存</el-button>
        </template>
        <div class="flex-column">
          <base-cell label-width="150px">
            <base-cell-item label="第三方小程序appid"><base-input v-model="mapObj.douyin_component_appid.value" style="width: 95%" /></base-cell-item>
            <base-cell-item label="第三方小程序appsecret"><base-input v-model="mapObj.douyin_component_appsecret.value" style="width: 95%" /></base-cell-item>
            <base-cell-item label="消息验证TOKEN"><base-input v-model="mapObj.douyin_tp_token.value" style="width: 95%" /></base-cell-item>
            <base-cell-item label="消息加密解密KEY"><base-input v-model="mapObj.douyin_encoding_aes_key.value" style="width: 95%" /></base-cell-item>
          </base-cell>
        </div>
      </base-card>
      <base-card title="抖音服务商-授权链接" style="width: 588px; margin-top: 20px">
        <el-tag>{{ authLink }}</el-tag>
      </base-card>
    </div>

    <div class="flex-1">
      <base-header>
        <base-input v-model="listQuery.name" label="名称" @clear="refreshTableData" />
        <el-button type="primary" @click="refreshTableData">查询</el-button>
        <template #right>
          <el-button type="success" @click="syncAppStatus()">一键同步小程序最新状态</el-button>
          <el-button type="warning" @click="showApp()">小程序批量操作</el-button>
        </template>
      </base-header>

      <base-content>
        <base-table-p ref="baseTableRef" api="sys_app_config.page" :params="listQuery">
          <el-table-column label="名称" prop="name" align="center" />
          <el-table-column label="AppID" prop="appId" align="center" />
          <!-- <el-table-column label="小程序类型" prop="appType" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.appType == 1" type="success"> {{ appTypeList.find((e) => e.id == scope.row.appType).name }}</el-tag>
            <el-tag v-if="scope.row.appType === 2"> {{ appTypeList.find((e) => e.id == scope.row.appType).name }}</el-tag>
          </template>
        </el-table-column> -->
          <el-table-column label="小程序状态" align="center" width="230px">
            <template #default="scope">
              <!-- <el-tag v-if="scope.row.appStatus == 1">未发版</el-tag>
          <div v-else>
            <el-tag>版本：{{ scope.row.appVersion }}</el-tag>
            <el-tag v-if="scope.row.appStatus == 10">已提交代码</el-tag>
            <el-tag v-else-if="scope.row.appStatus == 21">提审中</el-tag>
            <el-tag v-else-if="scope.row.appStatus == 31" type="warning">审核通过</el-tag>
            <el-tag v-else-if="scope.row.appStatus == 32" type="danger">审核不通过</el-tag>
            <el-tag v-else-if="scope.row.appStatus == 51" type="success">已发布</el-tag>
          </div> -->
              <div v-if="scope.row.appConfigObj && scope.row.appVersionObj">
                <el-tag type="success"
                  >线上版本：{{ scope.row.appVersionObj.current.version }}（{{
                    scope.row.appVersionObj.current.version == scope.row.appVersionObj.audit.version ? '已发布最新版' : '未发布最新版'
                  }}）</el-tag
                >
                <el-tag>审核版本：{{ scope.row.appVersionObj.audit.version }}（{{ getAppAuditStatusDesc(scope.row.appVersionObj.audit.status) }}）</el-tag>
                <el-tag type="info">测试版本号：{{ scope.row.appVersionObj.latest.version }}（{{ scope.row.appVersionObj.latest.has_audit ? '已提审' : '未提审' }}）</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column align="center" width="150px" label="操作">
            <template #default="scope">
              <div v-if="scope.row.id !== 1">
                <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
                <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
                <el-button type="warning" link @click="showApp(scope.row)">小程序操作</el-button>
                <el-button type="primary" link @click="showQrcode(scope.row, 'latest')">查看测试版二维码</el-button>
                <el-button type="primary" link @click="showQrcode(scope.row, 'current')">查看线上版二维码</el-button>
              </div>
            </template>
          </el-table-column>
        </base-table-p>
      </base-content>
    </div>
    <base-dialog v-model="qrcodeDialogVisible" :title="qrcodeDialogTitle" width="350px">
      <el-image style="width: 300px; height: 300px" :src="qrcodeUrl" />
    </base-dialog>
    <base-dialog v-model="appDialogVisible" title="抖音小程序一键操作（☆谨慎操作☆）" width="60%">
      <span v-if="appDataForm.name" style="color: red">操作企业： {{ appDataForm.name }}</span>
      <span v-else style="color: red">操作企业： 所有企业（☆☆☆）</span>
      <div class="flex-column">
        <div class="flex m-t-10">
          <div class="flex-column">
            <base-input v-model="appDataForm.templateId" style="width: 100%; margin-top: 10px" label="小程序模板ID：" />
            <base-input v-model="appDataForm.version" style="width: 100%; margin-top: 10px" label="提交代码版本：" />
            <base-input v-model="appDataForm.uploadCodeDesc" style="width: 100%; margin-top: 10px" label="提交代码描述：" />
            <el-button type="primary" style="margin-top: 10px" @click="appOperationBatch(10)">① 一键提交代码</el-button>
          </div>
          <div class="flex-center-end m-l-20">
            <el-button type="success" @click="appOperationBatch(20)">② 一键提审代码</el-button>
            <el-button type="danger" @click="appOperationBatch(50)">③ 一键发布代码</el-button>
          </div>
        </div>
      </div>
    </base-dialog>
  </base-wrapper>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let mapObj = $ref({
  douyin_component_appid: { value: null, remark: '抖音服务商平台-第三方小程序应用appid' },
  douyin_component_appsecret: { value: null, remark: '抖音服务商平台-第三方小程序应用appsecret' },
  douyin_tp_token: { value: null, remark: '抖音服务商平台-消息验证TOKEN' },
  douyin_encoding_aes_key: { value: null, remark: '抖音服务商平台-消息加密解密KEY' },
  douyin_app_template_id: { value: null, remark: '抖音服务商平台-小程序模板ID' },
});
let authLink = $ref('');

onMounted(async () => {
  await init();
});

async function init() {
  let res = await proxy.$api.sys_config.listByKey({ keyList: Object.keys(mapObj).join(',') });
  let data = res.data;
  for (var key in data) {
    mapObj[key].value = data[key].value;
  }

  if (mapObj.douyin_component_appid.value) {
    let linkRes = await proxy.$api.sys_app_config.genLink();
    authLink = linkRes.data;
  }
}

async function saveBatch() {
  let list = [];
  for (var key in mapObj) {
    mapObj[key].key = key;
    mapObj[key].type = 1; // 1:配置 2:属性
    list.push(mapObj[key]);
  }
  let res = await proxy.$api.sys_config.saveBatch(list);
  proxy.submitOk(res.msg);
  init();
}

// --------------------------------- 小程序 ---------------------------------
let listQuery = $ref({ isUsable: true });
function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
let qrcodeDialogVisible = $ref(false);
let qrcodeDialogTitle = $ref('');
let qrcodeUrl = $ref('');
async function showQrcode(row, version) {
  let res = await proxy.$api.sys_app_config.qrcode({ appId: row.appId, version: version });
  qrcodeUrl = res.data;
  qrcodeDialogVisible = true;

  if (version == 'latest') {
    qrcodeDialogTitle = '小程序测试版二维码';
  } else if (version == 'current') {
    qrcodeDialogTitle = '小程序线上版二维码';
  }
}
let appDialogVisible = $ref(false);
let appDataForm = $ref({});
async function showApp(row) {
  appDataForm = {};
  if (row) {
    appDataForm = row;
    appDataForm.appIdList = [row.appId];
    appDataForm.version = row.appVersion;
  }
  appDialogVisible = true;
}
async function appOperationBatch(appStatus) {
  appDataForm.appStatus = appStatus;
  let res = await proxy.$api.sys_app_config.operationBatch(appDataForm);
  proxy.submitOk(res.message);
}
async function syncAppStatus() {
  let res = await proxy.$api.sys_app_config.syncStatus();
  proxy.submitOk(res.message);
  refreshTableData();
}

function getAppAuditStatusDesc(status) {
  switch (status) {
    case 0:
      return '审核中';
    case 1:
      return '通过';
    case 2:
      return '不通过';
    case 3:
      return '撤回审核';
    default:
      return '未知';
  }
}
</script>
<style lang="scss" scoped></style>
