<template>
  <base-wrapper class="flex-center-start">
    <div v-if="mapObj" style="width: 80vh">
      <div class="item">
        <div class="title">{{ mapObj.lbs_qq_key.remark }}：</div>
        <el-input v-model="mapObj.lbs_qq_key.value" />
      </div>

      <div class="item">
        <div class="title">{{ mapObj.mall_index_slide_img_list.remark }}：</div>
        <base-upload-multi v-model="mapObj.mall_index_slide_img_list.value" />
      </div>

      <div class="flex-center-center m-t-20">
        <el-button type="primary" @click="saveBatch">保 存</el-button>
      </div>
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
<style lang="scss" scoped>
.item {
  display: flex;
  margin-top: 10px;
  .title {
    display: flex;
    align-items: center;

    width: 150px;
    font-size: 10px;
    font-weight: bold;
  }
}
</style>
