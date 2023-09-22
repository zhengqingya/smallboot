<template>
  <view class="h-full flex-column p-x-20">
    <view class="flex-start-center" style="height: 80rpx">
      <navigator :url="'/subPackages/product/region'" class="flex-start-center">
        <text class="font-bold font-size-base">{{ chooseRegionData.name }}</text>
        <u-icon name="arrow-right" color="#999" size="16"></u-icon>
      </navigator>
    </view>
    <view class="" style="height: 30vh">map</view>
    <view class="h-full">
      <view class="bg-color-red" v-for="item in 10">
        {{ item }}
      </view>
    </view>
  </view>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let { chooseRegionData } = toRefs(proxy.$store.system.useSystemStore());

const props = defineProps({
  modelValue: {
    type: Array,
    required: false,
    default: () => [],
  },
});
let shopProvinceCityAreaList = ref([]);

onMounted(() => {
  init();
});

async function init() {
  // 省市区数据
  let res = await proxy.$api.common.provinceCityAreaTree({
    type: 1,
  });
}
</script>

<style lang="scss" scoped></style>
