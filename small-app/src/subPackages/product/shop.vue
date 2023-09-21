<template>
  <view class="h-full overflow-y-scroll">
    <!-- 分类关联商品数据 -->
    <base-linkage
      :list="shopProvinceCityAreaList"
      categoryClass="category-fixed-right"
      category-width="100rpx">
      <template #empty>
        <u-empty mode="data" text="数据为空" />
      </template>
      <template #category="{ data, isActive }">
        <view class="category-item flex-center-center" :class="{ active: isActive }">
          <text class="font-size-sm">{{ data.name }}</text>
        </view>
      </template>
      <template #categoryReList="{ data }">
        <view
          style="height: 60rpx"
          class="flex-start-center font-size-base font-bold bg-color-lightgrey p-l-20">
          {{ data.name }}
        </view>
        <view
          class="flex-center-center p-l-20"
          v-for="(item, index) in data.children"
          :key="index"
          @tap="showSpuDetailModal(data, item)">
          <view class="flex-1 h-full">
            <text class="font-size-base">{{ item.name }}</text>
          </view>
        </view>
      </template>
    </base-linkage>
  </view>
</template>

<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  modelValue: {
    type: Array,
    required: false,
    default: () => [],
  },
});
let shopProvinceCityAreaList = ref([]);

// 暴露方法
defineExpose({
  init,
});

onMounted(() => {
  init();
});

async function init() {
  // 省市区数据
  let res = await proxy.$api.common.provinceCityAreaTree({
    type: 1,
    // isShop: true,
  });
  shopProvinceCityAreaList.value = getGroupArrayObj(res, 'name');
}

// 根据首字母分组
function getGroupArrayObj(list, attr) {
  let resultList = [];
  const map = new Map();
  list.forEach((item, index, arr) => {
    let key = proxy.$filters.getInitialsToUpperCase(item[attr]);
    if (!map.has(key)) {
      let value = arr.filter((a) => proxy.$filters.getInitialsToUpperCase(a[attr]) == key);
      map.set(key, value);
      resultList.push({ name: key, children: value });
    }
  });
  return resultList;
}
</script>

<style lang="scss" scoped>
.category-item {
  &.active {
    color: #000;
    font-weight: bold;
  }
}
</style>
