<template>
  <base-wrapper
    class="flex-center-center"
    :style="{
      'background-color': isAdmin ? '#304156' : '#00aaff',
    }">
    <div class="flex-c-center-center bg-color-white" style="height: 400px; width: 500px; border-radius: 10px">
      <!-- <h1 v-if="isAdmin" class="font-size-lg">后台管理系统</h1>
      <div v-else>
        <h1 v-if="tenantId && tenantList && tenantList.length > 0" class="font-size-lg">{{ getTenantName() }}</h1>
        <h1 v-else class="font-size-lg">SmallBoot多租户管理系统</h1>
      </div> -->

      <h1 class="font-size-lg">SmallBoot多租户管理系统</h1>

      <div class="m-t-20">
        <el-form ref="loginFormRef" :model="loginForm" :rules="loginRules">
          <el-form-item v-if="!tenantId && tenantList && tenantList.length > 0">
            <base-select v-model="loginForm.tenantId" :filterable="false" placeholder="请选择租户" :option-props="{ label: 'name', value: 'id' }" :data-list="tenantList">
              <template #prefix>
                <el-icon> <OfficeBuilding /> </el-icon>
              </template>
            </base-select>
            <!-- <el-input v-model="loginForm.tenantName" prefix-icon="House" placeholder="请输入租户名称" maxlength="30" /> -->
          </el-form-item>
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" prefix-icon="User" placeholder="请输入账号" maxlength="30" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" prefix-icon="Lock" placeholder="请输入密码" show-password maxlength="30" />
          </el-form-item>
        </el-form>
        <!-- <div class="tips">
          <span>用户名: admin</span>
          <span class="m-l-20"> 密码: 123456</span>
        </div> -->
        <el-button type="primary" class="m-t-10 w-full" @click="handleLogin">登 录</el-button>
      </div>
    </div>
    <div class="copyright">
      <p>IF I WERE YOU</p>
    </div>
  </base-wrapper>
</template>

<script setup>
import { getCurrentInstance, onMounted } from 'vue';
// 组件实例
const { proxy } = getCurrentInstance();
const { login } = proxy.$store.user.useUserStore();
const loginForm = $ref({});
let tenantId = $ref(null);
let tenantList = $ref([]);
let isAdmin = $ref(false);

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

watch(
  () => proxy.$router.currentRoute.value,
  (newValue) => {
    // console.log('路由变化：', newValue.path);
    // 初始化变量
    tenantId = 1;
    loginForm.tenantId = tenantId;
    // 赋值
    var path = newValue.path;
    isAdmin = path.includes('admin');
    let lastPath = path.substring(path.lastIndexOf('/') + 1);
    if (!lastPath || lastPath == 'login') {
      return;
    }
    tenantId = lastPath;
    loginForm.tenantId = parseInt(tenantId);
  },
  {
    immediate: true, // 初始化执行一次
  },
);

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
          // 跳转到首页
          proxy.$router.push({ path: '/' });
        }
      });
    }
  });
}

function getTenantName() {
  let obj = tenantList.find((e) => e.id == tenantId);
  if (!obj) {
    return '';
  }
  return obj.name;
}
</script>

<style lang="scss" scoped>
.copyright {
  width: 100%;
  position: absolute;
  bottom: 0;
  font-size: 12px;
  text-align: center;
  color: #ccc;
}
</style>
