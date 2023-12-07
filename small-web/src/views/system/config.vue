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
  </base-wrapper>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let mapObj = $ref({
  lbs_qq_key: { value: null, remark: '腾讯地图key' },
  mall_index_slide_img_list: { value: [], remark: '商城首页轮播图' },
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
