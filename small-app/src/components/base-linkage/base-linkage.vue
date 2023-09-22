<template>
  <!-- 使用外层class需要加上 "h-full overflow-y-scroll" 微信小程序才能兼容剩余高度 -->
  <view class="h-full">
    <view v-if="dataList.length === 0" class="flex-center-center h-full">
      <slot name="empty" />
    </view>
    <view v-else class="box flex h-full" :class="flexLayout">
      <scroll-view
        :style="{ width: categoryWidth }"
        :class="categoryClass"
        class="category h-full"
        scroll-with-animation
        scroll-y>
        <view
          :class="{ active: item.customCategoryId === currentCategoryId }"
          v-for="(item, index) in dataList"
          :key="index"
          @tap="hanleCategoryTap(item.customCategoryId)">
          <slot
            name="category"
            :data="item"
            :isActive="item.customCategoryId === currentCategoryId" />
        </view>
      </scroll-view>

      <scroll-view
        class="flex-1 h-full"
        :class="categoryReDataClass"
        scroll-with-animation
        scroll-y
        :scroll-top="categoryScrollTop"
        @scroll="handleCategoryReDataScroll">
        <view id="ads"></view>
        <view>
          <view
            class="category-re-data-item-box"
            :id="`cate-${item.customCategoryId}`"
            v-for="(item, index) in dataList"
            :key="index">
            <slot name="categoryReList" :data="item" />
          </view>
        </view>
      </scroll-view>
    </view>
  </view>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let currentCategoryId = ref(0); // 当前分类id
let cateScrollTopList = ref([]); // 左侧分类关联右边商品滑动的顶部位置
let categoryScrollTop = ref(0); // 竖向滚动条位置
const props = defineProps({
  // 布局
  flexLayout: { type: String, default: 'flex' },
  // 分类样式
  categoryClass: { type: String, default: '' },
  // 分类关联数据样式
  categoryReDataClass: { type: String, default: '' },
  // 分类&关联商品数据 eg: [{ name:'分类1', list:[{name:'测试'}] }]
  list: { type: Array, default: () => [] },
  // 分类宽度 eg: 25%
  categoryWidth: { type: String, default: '' },
});
let dataList = ref([]); // 分类&关联商品数据 eg: [{ name:'分类1', list:[{name:'测试'}] }]

// dom加载完后，在组件更新之后调用
onUpdated(() => {
  init();
});

// defineExpose({ init });

async function init() {
  dataList.value = props.list;

  // 自己定义的内部的customCategoryId分类关联值数据等...
  for (var i = 0; i < dataList.value.length; i++) {
    dataList.value[i].customCategoryId = i + 1;
  }
  if (dataList.value.length > 0) {
    currentCategoryId.value = dataList.value[0].customCategoryId;
  }

  // 延时防止dom未加载完
  setTimeout(() => {
    calcSize();
  }, 500);
}

// 点击分类时，动态滑动关联数据到关联分类位置
function hanleCategoryTap(customCategoryId) {
  currentCategoryId.value = customCategoryId;
  categoryScrollTop.value = dataList.value.find(
    (item) => item.customCategoryId == customCategoryId
  ).top;
}
// 分类关联数据滚动时触发
function handleCategoryReDataScroll({ detail }) {
  const { scrollTop } = detail;

  if (cateScrollTopList.value.includes(categoryScrollTop.value)) {
    // 这里标识是从左侧分类点击触发的滚动
    categoryScrollTop.value = scrollTop;
    return;
  }

  let len = dataList.value.length;
  let endE = dataList.value[len - 1];
  let endTop = endE.top;
  for (let i = 0; i < len; i++) {
    let item = dataList.value[i];
    if (item.top <= scrollTop) {
      currentCategoryId.value = item.customCategoryId;
    }
  }
}
function calcSize() {
  // 高度
  let h = 0;
  // 获取节点信息 https://uniapp.dcloud.net.cn/api/ui/nodes-info.html#createselectorquery
  uni
    .createSelectorQuery()
    .in(proxy)
    .select('#ads')
    .fields({ size: true }, (data) => {
      h += Math.floor(data.height);
    })
    .exec();
  dataList.value.forEach((item) => {
    let view = uni.createSelectorQuery().in(proxy).select(`#cate-${item.customCategoryId}`);
    view
      .fields({ size: true }, (data) => {
        item.top = h;
        cateScrollTopList.value.push(h);
        h += data.height;
        item.bottom = h;
      })
      .exec();
  });
}
</script>

<style lang="scss" scoped>
.box {
  position: relative;
  .category-right {
    position: absolute;
    margin-left: 90%;
    // 垂直居中
    margin-top: 50%;
    transform: translateY(-25%);
    z-index: 2;
  }

  .category-re-data-item-box {
    &:nth-last-child(1) {
      // margin-bottom: 100rpx;
      // background-color: red;
    }
  }
}
</style>
