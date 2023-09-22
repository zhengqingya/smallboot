<template>
  <view class="h-full flex-column">
    <view class="tab flex-around-center" style="height: 80rpx">
      <text class="font-bold font-size-base">城市</text>
      <!-- <text class=" ">地区</text> -->
    </view>
    <base-linkage
      class="h-full overflow-y-scroll m-t-10"
      ref="linkageRef"
      categoryClass="category-right text-color-primary"
      category-width="30rpx">
      <template #empty>
        <u-empty mode="data" text="数据为空" />
      </template>
      <template #category="{ data, isActive }">
        <view class="category-item flex-center-center m-t-6" :class="{ active: isActive }">
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
          style="height: 60rpx"
          v-for="(item, index) in data.children"
          :key="index"
          @tap="chooseCity(item)">
          <view class="flex-start-center h-full p-l-20">
            <text class="font-size-base">{{ item.name }}</text>
          </view>
        </view>
      </template>
    </base-linkage>
  </view>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let { chooseRegionData } = toRefs(proxy.$store.system.useSystemStore());
const props = defineProps({
  modelValue: { type: Array, required: false, default: () => [] },
});
let shopProvinceCityAreaList = ref([]);

// 暴露方法
defineExpose({ init });

onMounted(() => {
  init();
});

async function init() {
  // 区数据
  let res = await proxy.$api.common.provinceCityAreaTree({
    type: 3,
    isShop: true,
  });
  shopProvinceCityAreaList.value = getGroupArrayObj(res.data, 'name');
  proxy.$refs.linkageRef.init(shopProvinceCityAreaList.value);
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

function chooseCity(data) {
  chooseRegionData.value = data;
  // 暂时去掉地区数据
  chooseRegionData.value.children = [];

  uni.redirectTo({ url: '/subPackages/product/shop' });
}
</script>

<style lang="scss" scoped>
.tab {
  box-shadow: 1px 0 5px rgba(0, 0, 0, 0.2);
}
.category-item {
  &.active {
    // color: #000;
    font-weight: bold;
    background-color: #d9d9d9;
    border-radius: 6rpx;
  }
}
</style>
