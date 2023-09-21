<template>
  <view v-if="list.length === 0" class="h-full flex-center-center">
    <slot name="empty" />
  </view>
  <view v-else class="h-full flex overflow-y-scroll">
    <scroll-view
      :style="{ width: categoryWidth }"
      class="bg-color-lightgrey text-color-grey p-r-10"
      scroll-with-animation
      scroll-y>
      <view
        :class="{ active: item.id === currentCategoryId }"
        v-for="(item, index) in list"
        :key="index"
        @tap="hanleCategoryTap(item.id)">
        <slot name="category" :data="item" :isActive="item.id === currentCategoryId" />
      </view>
    </scroll-view>

    <scroll-view
      class="flex-1 p-20"
      scroll-with-animation
      scroll-y
      :scroll-top="categoryScrollTop"
      @scroll="handleSpuScroll">
      <view id="ads"></view>
      <view>
        <view class="spu-box" :id="`cate-${item.id}`" v-for="(item, index) in list" :key="index">
          <slot name="categoryReList" :data="item" />
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let currentCategoryId = ref(0); // 当前分类id
let cateScrollTopList = ref([]); // 左侧分类关联右边商品滑动的顶部位置
let categoryScrollTop = ref(0); // 竖向滚动条位置
const props = defineProps({
  list: {
    type: Array,
    default: () => [],
  },
  categoryWidth: {
    type: String,
    default: '',
  },
});

// dom加载完后，在组件更新之后调用
onUpdated(() => {
  init();
});

async function init() {
  // 自己定义的内部的id分类关联值数据等...
  for (var i = 0; i < props.list.length; i++) {
    props.list[i].id = i + 1;
  }
  if (props.list.length > 0) {
    currentCategoryId.value = props.list[0].id;
  }

  setTimeout(() => {
    calcSize();
  }, 500);
}

// 点击左侧分类时，动态滑动右侧数据到关联分类位置
function hanleCategoryTap(id) {
  currentCategoryId.value = id;
  categoryScrollTop.value = props.list.find((item) => item.id == id).top;
}
// 右侧商品滚动时触发
function handleSpuScroll({ detail }) {
  const { scrollTop } = detail;

  if (cateScrollTopList.value.includes(categoryScrollTop.value)) {
    // 这里标识是从左侧分类点击触发的滚动
    categoryScrollTop.value = scrollTop;
    return;
  }

  let len = props.list.length;
  let endE = props.list[len - 1];
  let endTop = endE.top;
  for (let i = 0; i < len; i++) {
    let item = props.list[i];
    if (item.top <= scrollTop) {
      currentCategoryId.value = item.id;
    }
  }
}
function calcSize() {
  // 高度
  let h = 0;
  // 获取节点信息 https://uniapp.dcloud.net.cn/api/ui/nodes-info.html#createselectorquery
  uni
    .createSelectorQuery()
    .select('#ads')
    .fields({ size: true }, (data) => {
      h += Math.floor(data.height);
    })
    .exec();
  props.list.forEach((item) => {
    let view = uni.createSelectorQuery().select(`#cate-${item.id}`);
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
.spu-box {
  &:nth-last-child(1) {
    margin-bottom: 100rpx;
  }
}
</style>
