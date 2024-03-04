<template>
  <div class="app">hello</div>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let { isLogin, tenantId, userObj, tokenObj } = toRefs(proxy.$store.user.useUserStore());

onMounted(async () => {
  init();
});

function unmounted() {
  proxy.$wsApi.close();
}

async function init() {
  // ws初始化
  proxy.$wsApi.connect(import.meta.env.VITE_APP_WS_URL, tokenObj.value.tokenValue);
  proxy.$wsApi.onConnect(() => {
    // 加载离线消息
  });
  proxy.$wsApi.onMessage((cmd, msgInfo) => {
    console.log('接收消息:', cmd, msgInfo);
  });
  proxy.$wsApi.onClose((e) => {
    console.log(e);
    // 断线重连
    proxy.submitFail('ws连接断开，正在尝试重新连接...');
    proxy.$wsApi.reconnect(import.meta.env.VITE_APP_WS_URL, tokenObj.value.tokenValue);
  });
}
</script>
<style lang="scss" scoped>
.app {
  position: absolute;
  bottom: 10px;
  right: 10px;
  border-radius: 10px;
  box-shadow: 1px 1px 3px 1px rgba(0, 0, 0, 0.2);
  padding: 10px;
  background-color: #409eff;
  width: 380px;
  height: 600px;
}
</style>
