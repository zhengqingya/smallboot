<template>
  <base-wrapper>
    <base-header>
      <base-input v-model="listQuery.name" label="名称" @clear="refreshTableData" />
      <el-button type="primary" @click="refreshTableData">查询</el-button>
      <template #right>
        <el-button type="success" @click="syncAppStatus()">一键同步小程序最新状态</el-button>
        <el-button type="warning" @click="showApp()">小程序批量操作</el-button>
        <el-button type="primary" @click="handleAdd">添加</el-button>
      </template>
    </base-header>

    <el-table row-key="id" border :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" :data="dataList" default-expand-all>
      <el-table-column label="ID" prop="id" align="center" />
      <el-table-column label="名称" prop="name" align="center" />
      <el-table-column label="状态" align="center">
        <template #default="scope">
          <base-tag v-model="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="过期时间" prop="expireTime" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.expireTime" type="warning"> {{ scope.row.expireTime }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="最大用户数" prop="userNum" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.userNum" type="success"> {{ scope.row.userNum }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="排序" prop="sort" align="center" />
      <el-table-column label="小程序类型" prop="appType" align="center">
        <template #default="scope">
          <el-tag v-if="scope.row.appConfigObj.appType == 1" type="success"> {{ appTypeList.find((e) => e.id == scope.row.appConfigObj.appType).name }}</el-tag>
          <el-tag v-if="scope.row.appConfigObj.appType === 2"> {{ appTypeList.find((e) => e.id == scope.row.appConfigObj.appType).name }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="小程序状态" align="center" width="230px">
        <template #default="scope">
          <!-- <el-tag v-if="scope.row.appConfigObj.appStatus == 1">未发版</el-tag>
          <div v-else>
            <el-tag>版本：{{ scope.row.appConfigObj.appVersion }}</el-tag>
            <el-tag v-if="scope.row.appConfigObj.appStatus == 10">已提交代码</el-tag>
            <el-tag v-else-if="scope.row.appConfigObj.appStatus == 21">提审中</el-tag>
            <el-tag v-else-if="scope.row.appConfigObj.appStatus == 31" type="warning">审核通过</el-tag>
            <el-tag v-else-if="scope.row.appConfigObj.appStatus == 32" type="danger">审核不通过</el-tag>
            <el-tag v-else-if="scope.row.appConfigObj.appStatus == 51" type="success">已发布</el-tag>
          </div> -->
          <div v-if="scope.row.appConfigObj.appVersionObj">
            <el-tag type="success"
              >线上版本：{{ scope.row.appConfigObj.appVersionObj.current.version }}（{{
                scope.row.appConfigObj.appVersionObj.current.version == scope.row.appConfigObj.appVersionObj.audit.version ? '已发布最新版' : '未发布最新版'
              }}）</el-tag
            >
            <el-tag>审核版本：{{ scope.row.appConfigObj.appVersionObj.audit.version }}（{{ getAppAuditStatusDesc(scope.row.appConfigObj.appVersionObj.audit.status) }}）</el-tag>
            <el-tag type="info">测试版本号：{{ scope.row.appConfigObj.appVersionObj.latest.version }}（{{ scope.row.appConfigObj.appVersionObj.latest.has_audit ? '已提审' : '未提审' }}）</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" align="center" />
      <el-table-column align="center" width="150px" label="操作">
        <template #default="scope">
          <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button link type="primary" @click="showDetail(scope.row)">详情</el-button>
          <el-button type="warning" link @click="showApp(scope.row)">小程序操作</el-button>
          <el-button type="primary" link @click="showQrcode(scope.row, 'latest')">查看测试版二维码</el-button>
          <el-button type="primary" link @click="showQrcode(scope.row, 'current')">查看线上版二维码</el-button>
          <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
        </template>
      </el-table-column>
    </el-table>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="60%" top="0px">
      <el-form ref="appDataFormRef" :inline="!true" :model="form" :rules="rules" label-width="150px">
        <base-card title="企业信息">
          <!-- <el-form-item label="父部门:">
          <base-cascader
            v-if="dialogVisible"
            v-model="form.parentId"
            clearable
            :params="{ excludeDeptId: form.id }"
            placeholder="请选择(为空时标识顶级)"
            :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
            api="sys_dept.tree" />
        </el-form-item> -->
          <el-form-item label="名称:">
            <el-input v-model="form.name" :disabled="isDetail" />
          </el-form-item>
          <!-- <el-form-item label="负责人:">
            <base-select v-model="form.leaderUserId" :disabled="isDetail" clearable :option-props="{ label: 'nickname', value: 'userId' }" api="sys_user.list" />
          </el-form-item> -->
          <el-form-item label="状态:">
            <base-radio-group v-model="form.status" :disabled="isDetail" />
          </el-form-item>

          <div v-if="form.parentId == 0">
            <el-form-item label="所属地区:">
              <province-city-area v-model="form.provinceCityAreaList" :disabled="isDetail" />
            </el-form-item>
            <el-form-item label="详细地址:">
              <el-input v-model="form.address" :disabled="isDetail" />
            </el-form-item>

            <el-form-item label="过期时间:">
              <el-date-picker v-model="form.expireTime" :disabled="isDetail" type="datetime" placeholder="请选择" format="YYYY-MM-DD hh:mm:ss" value-format="YYYY-MM-DD hh:mm:ss" />
            </el-form-item>
            <el-form-item label="最大员工数:">
              <el-input-number v-model="form.userNum" :disabled="isDetail" :min="1" controls-position="right" placeholder="请输入" />
            </el-form-item>
            <el-form-item label="最大发布数:">
              <el-input-number v-model="form.jobNum" :disabled="isDetail" :min="1" controls-position="right" placeholder="请输入" />
            </el-form-item>
          </div>
          <el-form-item label="排序:">
            <el-input-number v-model="form.sort" :min="1" :disabled="isDetail" controls-position="right" placeholder="请输入排序" />
          </el-form-item>

          <el-form-item label="备注:">
            <el-input v-model="form.remark" :rows="2" :disabled="isDetail" type="textarea" />
          </el-form-item>
        </base-card>
        <base-card v-if="form.parentId == 0" title="小程序配置">
          <el-form-item label="小程序类型:">
            <base-select
              v-model="form.appConfigObj.appType"
              :disabled="isDetail"
              :data-list="appTypeList"
              style="margin-right: 10px"
              tag-type="success"
              clearable
              :option-props="{ label: 'name', value: 'id' }" />
          </el-form-item>
          <el-form-item label="抖音AppID:">
            <el-input v-model="form.appConfigObj.appId" :disabled="isDetail" placeholder="从抖音开放平台中获取" />
          </el-form-item>
          <el-form-item label="抖音AppSecret:">
            <el-input v-model="form.appConfigObj.appSecret" :disabled="isDetail" placeholder="从抖音开放平台中获取" />
          </el-form-item>
          <el-form-item label="小程序首页头部名称:">
            <el-input v-model="form.appConfigObj.appIndexTitle" :disabled="isDetail" placeholder="不填则默认为: 首页（只在发布小程序后才生效）" />
          </el-form-item>
        </base-card>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </base-dialog>

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
let listQuery = $ref({});
let form = $ref({});
let dialogVisible = $ref(false);
let dialogStatus = $ref('');
let isDetail = $ref(false);
let rules = $ref({});
let dataList = $ref([]);
let appTypeList = $ref([
  { id: 1, name: '共享版' },
  { id: 2, name: '独立版' },
]);

onMounted(() => {
  refreshTableData();
});

async function refreshTableData() {
  let res = await proxy.$api.sys_dept.tree(listQuery);
  dataList = res.data;
}
function handleAdd() {
  form = { status: 1, sort: 100, parentId: 0, appType: 2, appConfigObj: {} };
  dialogStatus = 'add';
  dialogVisible = true;
  isDetail = false;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  form.provinceCityAreaList = [form.provinceName, form.cityName, form.areaName];
  dialogStatus = 'update';
  dialogVisible = true;
  isDetail = false;
}
function showDetail(row) {
  form = Object.assign({}, row);
  form.provinceCityAreaList = [form.provinceName, form.cityName, form.areaName];
  dialogStatus = 'detail';
  dialogVisible = true;
  isDetail = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.sys_dept.delete({ id: row.id });
  refreshTableData();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.appDataFormRef.validate(async (valid) => {
    if (valid) {
      // 省市区
      if (form.provinceCityAreaList) {
        form.provinceName = form.provinceCityAreaList[0];
        if (form.provinceCityAreaList.length > 1) {
          form.cityName = form.provinceCityAreaList[1];
          form.areaName = form.provinceCityAreaList[2];
        }
      }
      if (!form.parentId || !isFinite(form.parentId)) {
        form.parentId = 0;
      }
      let res = await proxy.$api.sys_dept[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      refreshTableData();
      dialogVisible = false;
    }
  });
}

// 小程序 ---------------------------------
let qrcodeDialogVisible = $ref(false);
let qrcodeDialogTitle = $ref('');
let qrcodeUrl = $ref('');
async function showQrcode(row, version) {
  let res = await proxy.$api.sys_app_config.qrcode({ appId: row.appConfigObj.appId, version: version });
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
    appDataForm = row.appConfigObj;
    appDataForm.appIdList = [row.appConfigObj.appId];
    appDataForm.version = row.appConfigObj.appVersion;
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
