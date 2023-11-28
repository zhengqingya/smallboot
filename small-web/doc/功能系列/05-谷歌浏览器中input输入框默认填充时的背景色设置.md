# 谷歌浏览器中input输入框默认填充时的背景色设置

```
<el-form ref="loginFormRef" :model="loginForm" :rules="loginRules">
  <el-form-item prop="username">
    <el-input v-model="loginForm.username" prefix-icon="User" placeholder="请输入账号" maxlength="30" />
  </el-form-item>
  <el-form-item prop="password">
    <el-input v-model="loginForm.password" prefix-icon="Lock" placeholder="请输入密码" show-password maxlength="30" />
  </el-form-item>
</el-form>
```

```
// 谷歌浏览器中input输入框默认填充时的背景色设置
::v-deep(.el-input) {
  input:-internal-autofill-selected {
    box-shadow: inset 0 0 0 32px #fff !important;
    -webkit-text-fill-color: black;
    color: black;
  }
}
```