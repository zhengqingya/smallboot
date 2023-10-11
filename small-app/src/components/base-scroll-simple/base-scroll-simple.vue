<template>
  <view class="h-full">
    <scroll-view
      class="h-full"
      scroll-y
      enable-back-to-top
      :refresher-enabled="true"
      :refresher-triggered="isReFresh"
      @refresherrefresh="refresh">
      <!-- 使用时别名 <template #default="{ dataObj: jobObj }"> -->
      <slot :dataObj="dataObj" />
    </scroll-view>
  </view>
</template>
<script setup>
const { proxy } = getCurrentInstance();
import { onShow } from '@dcloudio/uni-app';

const props = defineProps({
  params: {},
  api: { type: String, default: '' },
});

let isReFresh = ref(true);
let dataObj = ref({});

// 页面显示就触发
onShow(() => {
  getApiData();
});

// 暴露方法  使用eg: proxy.$refs.baseScrollRef.refresh();
defineExpose({
  refresh,
});

// 下拉刷新
function refresh() {
  isReFresh.value = true;
  getApiData();
  setTimeout(() => {
    isReFresh.value = false;
  }, 100);
}

async function getApiData() {
  // 请求处理数据
  let res = await apiMethod(props.params);
  dataObj.value = res.data;
}

function apiMethod(params, headers) {
  // eg: proxy.$api.order.page(xx);
  return props.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(params, headers);
}
</script>
<style lang="scss" scoped></style>
