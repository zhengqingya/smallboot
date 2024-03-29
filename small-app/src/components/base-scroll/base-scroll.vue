<template>
  <view class="h-full">
    <scroll-view
      class="h-full"
      scroll-y
      enable-back-to-top
      @scrolltolower="onPushRefresh"
      :refresher-enabled="true"
      :refresher-triggered="isReFresh"
      @refresherrefresh="onPullRefresh">
      <view v-if="isPage" class="h-full">
        <view v-if="dataList.length > 0" class="h-full">
          <slot :list="dataList" />
          <u-loadmore
            v-if="dataList.length > loadmoreNum"
            :line="true"
            :status="loadmoreStatus"></u-loadmore>
        </view>
        <view v-else class="flex-center-center h-full">
          <slot name="empty" />
        </view>
      </view>
      <view v-else class="h-full">
        <slot :data="dataList" />
      </view>
    </scroll-view>
  </view>
</template>
<script setup>
const { proxy } = getCurrentInstance();
import { onShow } from '@dcloudio/uni-app';

const props = defineProps({
  params: {},
  api: { type: String, default: '' },
  isPage: { type: Boolean, default: false },
  loadmoreNum: { type: Number, default: 5 },
});

let pageParams = ref({ pageNum: 1, pageSize: 10 });
let current = ref(0);
let total = ref(1);
let isReFresh = ref(true);
let dataList = ref([]);
let loadmoreStatus = ref('loadmore');

// 页面显示就触发
onShow(() => {
  getApiData(true);
});

watch(
  dataList,
  (newValue, oldValue) => {
    // console.log('监听器执行了... ', newValue);
    proxy.$emit('changeData', newValue);
  },
  {
    // immediate: true, // 初始化执行一次
    deep: true, // 深度监听
  }
);

// 暴露方法  使用eg: proxy.$refs.baseScrollRef.refresh();
defineExpose({
  refresh,
});

function refresh() {
  onPullRefresh();
}

// 上拉加载更多 -- 滑到底部触发
function onPushRefresh() {
  loadmoreStatus.value = 'loading';
  pageParams.value.pageNum++;
  setTimeout(async () => {
    await getApiData();
  }, 200);
}

// 下拉刷新
function onPullRefresh() {
  isReFresh.value = true;
  getApiData(true);
  setTimeout(() => {
    isReFresh.value = false;
  }, 100);
}

async function getApiData(isFirst) {
  if (!props.api) {
    return;
  }
  if (!props.isPage) {
    // 直接请求处理数据
    let res = await apiMethod(props.params);
    dataList.value = res.data;
    return;
  }

  if (isFirst) {
    pageParams.value.pageNum = 1;
  }
  if (!isFirst && dataList.value.length >= total.value) {
    loadmoreStatus.value = 'nomore';
    return;
  } else {
    loadmoreStatus.value = 'loadmore';
  }

  // 请求处理数据
  let res = await apiMethod(props.params, pageParams.value);
  let result = res.data;
  total.value = result.total;
  pageParams.value.pageNum = result.current;
  dataList.value = isFirst ? result.records : [...dataList.value, ...result.records];

  // 最终显示状态
  if (dataList.value.length >= total.value) {
    loadmoreStatus.value = 'nomore';
  }
}

function apiMethod(params, headers) {
  // eg: proxy.$api.order.page(xx);
  return props.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(params, headers);
}
</script>
<style lang="scss" scoped></style>
