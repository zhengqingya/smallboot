<template>
  <div class="flex-center-center" style="height: 100%; width: 100%; background-color: #00aaff">
    <div class="flex-c-center-center bg-color-white" style="height: 400px; width: 500px; border-radius: 10px">
      <h1 class="font-size-lg">SmallBoot多租户管理系统</h1>

      <div style="margin-top: 30px">
        <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules">
          <el-form-item v-if="!tenantId && tenantList && tenantList.length > 0" prop="tenantId">
            <base-select is-full v-model="loginForm.tenantId" style="width: 260px" :filterable="true" placeholder="请选择租户" :option-props="{ label: 'name', value: 'id' }" :data-list="tenantList">
              <template #prefix>
                <el-icon> <OfficeBuilding /> </el-icon>
              </template>
            </base-select>
            <!-- <el-input v-model="loginForm.tenantName" prefix-icon="House" placeholder="请输入租户名称" maxlength="30" /> -->
          </el-form-item>
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" style="width: 260px" prefix-icon="User" placeholder="请输入账号" maxlength="30" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" style="width: 260px" prefix-icon="Lock" placeholder="请输入密码" show-password maxlength="30" />
          </el-form-item>
        </el-form>
        <div class="tips text-color-grey font-size-sm">
          <span>&nbsp;&nbsp;1. 超管: superadmin/123456</span>
        </div>
        <div class="tips text-color-grey font-size-sm">
          <span>&nbsp;&nbsp;2. 系统管理员: admin/123456</span>
        </div>
        <el-button type="primary" class="w-full" style="margin-top: 10px; width: 260px" @click="handleLogin">登 录</el-button>
      </div>
    </div>
    <div class="copyright">
      <p>IF I WERE YOU</p>
    </div>
  </div>
</template>

<script setup>
import { getCurrentInstance, onMounted } from 'vue';
// 组件实例
const { proxy } = getCurrentInstance();
const { login } = proxy.$store.user.useUserStore();
let { firstRouterPermPath } = toRefs(proxy.$store.user.useUserStore());
const loginForm = $ref({ tenantId: 1, username: 'admin', password: '123456' });
let tenantId = $ref(null);
let tenantList = $ref([]);

const loginRules = {
  tenantId: [{ required: true, trigger: 'change', message: '请选择租户' }],
  tenantName: [{ required: true, trigger: 'change', message: '请输入租户名称' }],
  username: [{ required: true, trigger: 'change', message: '请输入账号' }],
  password: [{ required: true, trigger: 'change', validator: validatePassword }],
};

function validatePassword(rule, value, callback) {
  if (!value || value.length < 6) {
    callback(new Error('密码最少6位'));
  } else {
    callback();
  }
}

onUpdated(async () => {});

onMounted(async () => {
  // 拿到租户数据
  let res = await proxy.$api.sys_tenant.list();
  tenantList = res.data;
});

function handleLogin() {
  proxy.$refs.loginFormRef.validate((valid) => {
    if (valid) {
      login(loginForm).then(() => {
        let fullPath = proxy.$route.fullPath;
        if (fullPath.startsWith('/login?redirect=')) {
          let lastPath = fullPath.replace('/login?redirect=', '');
          // 跳转到上次退出的页面
          proxy.$router.push({ path: lastPath });
        } else {
          // 跳转有权限的第一个页面
          proxy.$router.push({ path: firstRouterPermPath.value });
        }
      });
    }
  });
}
</script>

<style lang="scss" scoped>
// 谷歌浏览器中input输入框默认填充时的背景色设置
::v-deep(.el-input) {
  input:-internal-autofill-selected {
    box-shadow: inset 0 0 0 32px #fff !important;
    -webkit-text-fill-color: black;
    color: black;
  }
}

.copyright {
  width: 100%;
  position: absolute;
  bottom: 0;
  font-size: 12px;
  text-align: center;
  color: #ccc;
}
</style>
