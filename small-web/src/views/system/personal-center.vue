<template>
  <div style="background: #f5f6fa; height: 100%; width: 100%">
    <div class="bg" />
    <div style="position: absolute; top: 118px; width: 100%; padding: 0px 24px">
      <div style="padding: 40px; border-radius: 6px; background: #fff">
        <div class="flex-between-start">
          <div class="flex-start-center">
            <el-avatar :size="78" :src="userObj.avatarUrl" />
            <span style="margin-left: 32px; font-weight: 600; font-size: 22px; color: #333333">Hi，{{ userObj.nickname }}</span>
          </div>

          <div>
            <el-button type="primary" @click="updateUser">修改基本信息</el-button>
            <el-button type="warning" @click="updatePwdDialogVisible = true">修改密码</el-button>
          </div>
        </div>

        <div style="height: 1px; background: #e6e9f4; margin: 40px 0px 24px 0px" />

        <div class="grid-start-center-2" style="grid-gap: 20px; margin-left: 100px; width: 500px">
          <span>账号：{{ userObj.username }}</span>
          <span>性别：{{ $filters.sexName(userObj.sex) }}</span>
          <span>手机：{{ userObj.phone }}</span>
          <span>邮箱：{{ userObj.email }}</span>
          <span>角色：{{ userObj.roleNameList }}</span>
          <span>创建时间：{{ userObj.createTime }}</span>
        </div>
      </div>
    </div>
  </div>

  <update-sys-user-password :userId="userObj.userId" @cancel="updatePwdDialogVisible = false" @ok="updatePwdDialogVisible = false" v-if="updatePwdDialogVisible" />

  <!-- <el-col :span="11">
        <base-card title="第三方帐号绑定">
          <base-table-p ref="baseTableRef" api="sys_oauth.getOauthDataList" :params="tableOauthDataListQuery" :index-code="true" :is-page="false">
            <el-table-column label="绑定帐号信息" align="center">
              <template #default="scope">
                <span>{{ scope.row.oauthTypeName }}</span>
              </template>
            </el-table-column>

            <el-table-column label="是否绑定" align="center">
              <template #default="scope">
                <span v-if="scope.row.ifBind" style="font-weight: bold; background-color: #00ffff">是</span>
                <span v-else>否</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template #default="scope">
                <el-button v-if="scope.row.ifBind" type="danger" @click="handleOauthBind(scope.row)">解绑</el-button>
                <el-button v-else type="primary" @click="handleOauthBind(scope.row)"><a :href="import.meta.env.VITE_APP_SERVICE_API + '/system/web/api/oauth/' + scope.row.oauthTypeBindName"> 绑定 </a></el-button>
              </template>
            </el-table-column>
          </base-table-p>
        </base-card>
      </el-col> -->
  <base-dialog v-model="dialogVisible" title="修改基本信息" width="500px">
    <el-form :model="form" label-width="80px">
      <el-form-item label="账号:" prop="username">
        <el-input v-model="form.username" disabled />
      </el-form-item>
      <!-- <el-form-item label="密码:" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码" />
        </el-form-item> -->
      <el-form-item label="昵称:" prop="nickname">
        <el-input v-model="form.nickname" />
      </el-form-item>
      <el-form-item label="性别:" prop="sex">
        <el-select v-model="form.sex" placeholder="请选择" style="width: 100%">
          <el-option v-for="item in sexList" :key="item.value" :label="item.name" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="手机号码:" prop="phone">
        <el-input v-model="form.phone" />
      </el-form-item>
      <el-form-item label="邮箱:" prop="email">
        <el-input v-model="form.email" />
      </el-form-item>
      <el-form-item label="头像:" prop="avatar">
        <base-upload-single v-model="form.avatarUrl" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">确定</el-button>
    </template>
  </base-dialog>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let useUserStore = proxy.$store.user.useUserStore();
let { userObj } = toRefs(useUserStore);
let { logout } = useUserStore;

let dialogVisible = $ref(false);
let updatePwdDialogVisible = $ref(false);
let form = $ref({});

function updateUser() {
  dialogVisible = true;
  form = JSON.parse(JSON.stringify(userObj.value));
}

async function submitForm() {
  await proxy.$api.sys_user.update(form);
  proxy.submitOk('保存成功');
  dialogVisible = false;
  // proxy.submitConfirm('保存成功，请重新登录！', () => {
  //   logout(); // 退出登录
  // });
}

// async function refreshTableData() {
//   proxy.$refs.baseTableRef.refresh();
// }

// async function handleOauthBind(row) {
//   let ifBind = row.ifBind;
//   if (ifBind === 1) {
//     // 如果为绑定则解除绑定
//     let res = await proxy.$api.sys_oauth.removeBind({
//       userReOauthId: row.userReOauthId,
//     });
//     proxy.submitOk(res.msg);
//     refreshTableData();
//   } else {
//     await proxy.$api.sys_oauth.thirdpartOauth(row.oauthTypeName);
//   }
// }
</script>

<style lang="scss" scoped>
.bg {
  width: 100%;
  height: 355px;
  background-color: #00bfff;
}
</style>
