<template>
  <base-wrapper class="flex-cc-center-start">
    <div class="flex-end-center">
      <el-button type="warning" @click="saveBatch">保 存</el-button>
    </div>

    <div v-if="mapObj" class="flex-center-start">
      <base-card :title="mapObj.lbs_qq_key.remark" style="width: 400px">
        <el-input v-model="mapObj.lbs_qq_key.value" />
      </base-card>

      <base-card :title="mapObj.mall_index_slide_img_list.remark" style="width: 800px">
        <base-upload-multi v-model="mapObj.mall_index_slide_img_list.value" />
      </base-card>
    </div>
    <!-- <div class="flex-center-start">
      <base-card title="抖音服务商平台" style="width: 660px">
        <div class="flex-column">
          <base-cell label-width="180px">
            <base-cell-item label="第三方小程序应用appid"><base-input v-model="mapObj.douyin_component_appid.value" style="width: 95%" /></base-cell-item>
            <base-cell-item label="第三方小程序应用appsecret"><base-input v-model="mapObj.douyin_component_appsecret.value" style="width: 95%" /></base-cell-item>
            <base-cell-item label="消息验证TOKEN"><base-input v-model="mapObj.douyin_tp_token.value" style="width: 95%" /></base-cell-item>
            <base-cell-item label="消息加密解密KEY"><base-input v-model="mapObj.douyin_encoding_aes_key.value" style="width: 95%" /></base-cell-item>
          </base-cell>
        </div>
      </base-card>
    </div> -->
  </base-wrapper>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let mapObj = $ref({
  lbs_qq_key: { value: null, remark: '腾讯地图key' },
  mall_index_slide_img_list: { value: [], remark: '商城首页轮播图' },
  douyin_component_appid: { value: null, remark: '抖音服务商平台-第三方小程序应用appid' },
  douyin_component_appsecret: { value: null, remark: '抖音服务商平台-第三方小程序应用appsecret' },
  douyin_tp_token: { value: null, remark: '抖音服务商平台-消息验证TOKEN' },
  douyin_encoding_aes_key: { value: null, remark: '抖音服务商平台-消息加密解密KEY' },
});
onMounted(async () => {
  await init();
});

async function init() {
  let res = await proxy.$api.sys_config.listByKey({ keyList: Object.keys(mapObj).join(',') });
  let data = res.data;
  for (var key in data) {
    mapObj[key].value = data[key].value;
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
