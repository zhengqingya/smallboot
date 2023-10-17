<template>
  <base-wrapper class="flex-cc-center-start">
    <div class="flex-end-center">
      <el-button type="primary" @click="init">刷新</el-button>
    </div>
    <div class="flex-c-center-center">
      <base-card title="抖音服务商平台" style="width: 660px">
        <template #append>
          <el-button type="warning" @click="saveBatch">保 存</el-button>
        </template>
        <div class="flex-column">
          <base-cell label-width="200px">
            <base-cell-item label="第三方小程序appid"><base-input v-model="mapObj.douyin_component_appid.value" style="width: 95%" /></base-cell-item>
            <base-cell-item label="第三方小程序appsecret"><base-input v-model="mapObj.douyin_component_appsecret.value" style="width: 95%" /></base-cell-item>
            <base-cell-item label="消息验证TOKEN"><base-input v-model="mapObj.douyin_tp_token.value" style="width: 95%" /></base-cell-item>
            <base-cell-item label="消息加密解密KEY"><base-input v-model="mapObj.douyin_encoding_aes_key.value" style="width: 95%" /></base-cell-item>
          </base-cell>
        </div>
      </base-card>
      <base-card title="抖音服务商-授权链接" style="width: 660px; margin-top: 20px">
        <el-tag>{{ authLink }}</el-tag>
      </base-card>
    </div>
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

  let versionRes = await proxy.$api.sys_version.lately();
  versionObj = versionRes.data;

  if (mapObj.douyin_component_appid.value) {
    let linkRes = await proxy.$api.sys_merchant.genLink();
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
</script>
<style lang="scss" scoped></style>
