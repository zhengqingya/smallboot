<template>
  <div class="scroll-pagination-container" v-loading="isLoading">
    <!-- 滚动内容区域 -->
    <div ref="scrollContainer" class="scroll-container" @scroll="handleScroll">
      <!-- 内容列表 -->
      <slot :data="dataList"></slot>

      <!-- 无更多数据提示 -->
      <div v-if="!hasMore && pageParams.pageNum > 1" class="no-more-data">没有更多数据了~</div>
    </div>
  </div>
</template>
<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  params: { type: Object, default: () => {} },
  api: { type: String, default: '' },
});

let isLoading = $ref(true);
let pageParams = $ref({ pageNum: 1, pageSize: 10 });
let dataList = $ref([]); // 列表数据

// 暴露方法
defineExpose({
  refresh,
});

// 生命周期
onMounted(() => {
  refresh();
  window.addEventListener('resize', () => {
    handleScroll();
  });
});

onBeforeUnmount(() => {
  window.removeEventListener('resize', () => {
    handleScroll();
  });
});

// 刷新
async function refresh() {
  isLoading = true;
  dataList = [];
  pageParams.pageNum = 1;
  await getApiData();
  isLoading = false;
}

// -----------------------------------

let scrollContainer = ref(null);
let hasMore = $ref(true); // 是否还有更多数据

// 滚动事件处理
async function handleScroll() {
  if (!hasMore || isLoading) {
    return;
  }
  let container = scrollContainer.value;
  let scrollBottom = container.scrollTop + container.clientHeight;
  let triggerPosition = container.scrollHeight - 10; // 提前10px触发加载

  // console.log('111', scrollBottom, triggerPosition);
  if (scrollBottom >= triggerPosition) {
    // 加载下一页数据：触发滚动到底部时触发
    await getApiData({ page: ++pageParams.pageNum, limit: pageParams.pageSize });
    // console.log('加载下一页数据：' + pageParams.pageNum);
  }
}

// -----------------------------------

// 获取接口数据
async function getApiData(pageObj) {
  if (!props.api) {
    return;
  }
  isLoading = true;
  // 处理分页参数
  if (pageObj) {
    // 从分页组件中拿到数据
    pageParams.pageNum = pageObj.page;
    pageParams.pageSize = pageObj.limit;
  }
  let response = await apiMethod(props.params, pageParams);
  let resList = response.data.records;
  if (resList && resList.length > 0) {
    dataList.push(...resList);
  }
  hasMore = pageParams.pageNum < response.data.pages;
  // console.log('计算是否还有更多数据：', hasMore);
  // setTimeout(() => {isLoading = false;}, 1000);
  isLoading = false;
}

function apiMethod(params, headers) {
  // eg: proxy.$api.sys_user.page(xx);
  return props.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(params, headers);
}
</script>
<style lang="scss" scoped>
.scroll-pagination-container {
  height: 100%;

  .scroll-container {
    height: 100%;
    overflow-y: auto;
    position: relative;
  }

  .no-more-data {
    padding: 10px;
    text-align: center;
    color: #909399;
    font-size: 0.9em;
  }
}

@keyframes rotating {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
