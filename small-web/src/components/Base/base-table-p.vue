<template>
  <div style="height: 100%; width: 100%" class="flex-column">
    <slot name="custom-header" />

    <el-table
      ref="baseTableRef"
      v-loading="isLoading"
      class="flex-1"
      :border="false"
      stripe
      element-loading-text="加载中..."
      v-bind="$attrs"
      :data="isPage ? pageRes.records : tableDataList"
      size="default"
      :header-cell-style="{ background: '#F8F9FA', color: '#333333' }"
      highlight-current-row
      :row-key="rowKey"
      :selection="true"
      :reserve-selection="true">
      <el-table-column v-if="selection" type="selection" :width="55"></el-table-column>
      <template v-if="indexCode">
        <el-table-column type="index" :index="(index) => index + 1 + (isPage ? (pageRes.current - 1) * pageRes.size : 0)" label="序号" align="center" width="80px" />
      </template>
      <slot />

      <template v-for="item in columns" :key="item">
        <el-table-column v-if="item.slotName" v-bind="item" :width="item.width">
          <template #scope>
            <slot :name="item.slotName" v-bind="scope"></slot>
          </template>
        </el-table-column>
        <el-table-column v-else v-bind="item"></el-table-column>
      </template>
    </el-table>

    <div v-if="isPage" class="flex-end-center" style="margin-top: 10px">
      <el-config-provider :locale="zhCn">
        <el-pagination
          v-if="pageRes && pageRes.total > 0"
          :current-page="pageParams.pageNum"
          :page-size="pageParams.pageSize"
          :total="pageRes.total == null ? 0 : pageRes.total"
          size="default"
          :page-sizes="[10, 20, 30, 40, 50]"
          :background="true"
          layout="total, sizes, prev, pager, next, jumper, slot"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange" />
      </el-config-provider>
    </div>
  </div>
</template>
<script setup>
import zhCn from 'element-plus/dist/locale/zh-cn.mjs';
zhCn.el.pagination = { goto: '前往', pageClassifier: '页', pagesize: '条/页', total: '共{total}条数据' };
const { proxy } = getCurrentInstance();
const props = defineProps({
  indexCode: { type: Boolean, default: false },
  selection: { type: Boolean, default: false },
  // 动态渲染的表格字段 :columns="[{ prop: 'name', label: '名称', width: '100' }]" =>  <el-table-column prop="name" label="名称" width="100" />
  columns: { type: Array, default: () => [] },
  params: { type: Object, default: () => {} },
  api: { type: String, default: '' },
  data: { type: Array, default: () => [] },
  //是否分页
  isPage: { type: Boolean, default: () => true },
  // row-key
  rowKey: { type: String, default: 'id' },
  // 是否初始化数据
  isInitData: { type: Boolean, default: true },
});

let isLoading = $ref(true);
let pageParams = $ref({ pageNum: 1, pageSize: 10 });
// 分页响应数据
let pageRes = $ref({ current: 1, pages: 1, size: 10, total: 0, records: [] });
let tableDataList = $ref([]); // 列表响应数据

// 暴露方法
defineExpose({
  refresh,
  refreshCurrentPage,
  getSelectionRows,
  getSelectionRowIdList,
});

watch(
  () => props.data,
  (newValue, oldValue) => {
    // console.log('监听器执行了... ', newValue, oldValue);
    pageRes = [];
    if (!props.isPage || (newValue && newValue.length > 0)) {
      tableDataList = newValue;
    }
  },
  {
    immediate: true, // 初始化执行一次
    deep: true, // 深度监听
  },
);

onMounted(() => {
  if (props.isInitData) {
    refresh();
  } else {
    isLoading = false;
  }
});

// 刷新
async function refresh() {
  isLoading = true;
  if (props.data && props.data.length > 0) {
    // 情况1：走父组件传值过来
    tableDataList = props.data;
  } else {
    // 情况2：走api接口数据
    if (props.isPage) {
      pageRes = {
        current: 1,
        pages: 1,
        size: 10,
        total: 0,
        records: [],
      };
      pageParams.pageNum = 1;
    }
    await getApiData();
  }
  isLoading = false;
}

// 刷新当前page
async function refreshCurrentPage() {
  console.log('刷新当前page:', pageParams.pageNum);
  isLoading = true;
  // pageParams.pageNum = 1;
  await getApiData();
  isLoading = false;
}

// 获取接口数据
async function getApiData(pageObj) {
  if (!props.api) {
    return;
  }

  isLoading = true;

  if (props.isPage) {
    // 处理分页参数
    if (pageObj) {
      // 从分页组件中拿到数据
      pageParams.pageNum = pageObj.page;
      pageParams.pageSize = pageObj.limit;
    }
    let response = await apiMethod(props.params, pageParams);
    pageRes = response.data;
  } else {
    let response = await apiMethod(props.params);
    tableDataList = response.data;
  }
  isLoading = false;
}
function apiMethod(params, headers) {
  // eg: proxy.$api.sys_user.save(xx);
  return props.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(params, headers);
}

// 分页组件参数变更时触发
function handleCurrentChange(val) {
  getApiData({ page: val, limit: pageParams.pageSize });
}
function handleSizeChange(val) {
  getApiData({ page: pageParams.pageNum, limit: val });
}

// 拿到选中行数据
function getSelectionRows() {
  return proxy.$refs.baseTableRef.getSelectionRows();
}

// 拿到选中行数据中的属性值
function getSelectionRowIdList() {
  let idList = getSelectionRows();
  return idList.map((item) => item[props.rowKey]);
}
</script>
<style lang="scss" scoped>
::v-deep(.el-pagination) {
  .el-pager li {
    background-color: #fff;
    border: 1px solid #d9d9d9;
  }
  .btn-prev,
  .btn-next {
    background-color: #fff;
    border: 1px solid #d9d9d9;
  }
}
</style>
