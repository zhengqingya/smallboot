<template>
  <base-wrapper class="flex-cc-center-start">
    <div class="flex-end-center">
      <el-button type="warning" @click="saveBatch">保 存</el-button>
    </div>

    <div v-if="mapObj" class="flex-center-start">
      <base-card :title="mapObj.lbs_qq_key.remark" style="width: 400px">
        <el-input v-model="mapObj.lbs_qq_key.value" />
      </base-card>

      <base-card :title="mapObj.mall_index_slide_img_list.remark" style="width: 400px">
        <base-upload-multi v-model="mapObj.mall_index_slide_img_list.value" />
      </base-card>
    </div>
  </base-wrapper>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let mapObj = $ref();
onMounted(async () => {
  await init();
});

async function init() {
  let res = await proxy.$api.sys_config.listByKey({ keyList: ['lbs_qq_key', 'mall_index_slide_img_list'].join(',') });
  mapObj = res.data;
}

async function saveBatch() {
  let list = [];
  for (var index in mapObj) {
    list.push(mapObj[index]);
  }
  let res = await proxy.$api.sys_config.saveBatch(list);
  proxy.submitOk(res.msg);
  init();
}
</script>
<style lang="scss" scoped></style>
