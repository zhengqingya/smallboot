<template>
  <base-dialog v-bind="$attrs" v-model="dialogVisible" title="更新密码" width="300px" @close="doCancel">
    <el-form ref="dataFormRef" :model="form" :rules="rules">
      <el-form-item label="密码:" prop="newPassword">
        <el-input v-model="form.newPassword" placeholder="请输入密码" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="doCancel">取消</el-button>
      <el-button type="primary" @click="doSubmit()">确定</el-button>
    </template>
  </base-dialog>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let useUserStore = proxy.$store.user.useUserStore();
let { logout } = useUserStore;
let { userObj } = toRefs(useUserStore);

const props = defineProps({
  userId: { type: String, default: null },
  isShow: { type: Boolean, default: false },
});

let rules = ref({
  newPassword: [{ required: true, pattern: /^(\w){6,16}$/, message: '请设置6-16位字母或数字', trigger: 'blur' }],
});

let dialogVisible = ref(true);
let form = ref({ newPassword: '123456' });

function doCancel() {
  proxy.$emit('cancel');
}

async function doSubmit() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      form.value.userId = props.userId;
      let res = await proxy.$api.sys_user.updatePassword(form.value);
      proxy.submitOk(res.msg);
      handelCurrentLoginUser();
      proxy.$emit('ok');
    }
  });
}

function handelCurrentLoginUser() {
  if (form.value.userId === userObj.value.userId) {
    proxy.submitConfirm('密码修改成功，请重新登录！', () => {
      logout();
    });
  }
}
</script>
<style lang="scss" scoped></style>
