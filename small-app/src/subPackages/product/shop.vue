<template>
  <view class="h-full flex-column">
    <view class="flex-start-center p-x-20" style="height: 80rpx">
      <navigator hover-class="none" :url="'/subPackages/product/region'" class="flex-start-center">
        <text class="font-bold font-size-base">{{ chooseRegionData.name }}</text>
        <u-icon name="arrow-right" color="#999" size="16"></u-icon>
      </navigator>
    </view>
    <view class="flex-center-center" style="height: 30vh">map地图数据待开发</view>
    <base-scroll
      class="h-full overflow-y-scroll bg-color-lightgrey"
      :isPage="true"
      api="shop.page"
      :params="{
        areaName: chooseRegionData.name,
      }"
      :loadmoreNum="10">
      <template #empty>
        <u-empty mode="data" text="暂无数据" />
      </template>
      <template #default="{ list }">
        <view
          class="bg-color-white m-20 p-20 font-size-base"
          style="border-radius: 10rpx; border-radius: 10rpx"
          v-for="(item, index) in list"
          :key="index"
          @click="goProductPage(item)">
          <view class="flex-between-center">
            <view>
              <view class="font-bold">{{ item.shopName }}</view>
              <view class="m-t-10 font-size-sm text-color-grey">{{ item.address }}</view>
              <view class="m-t-10 font-size-sm text-color-warning">
                营业时间： {{ item.openTimeList[0].startTime }}- {{ item.openTimeList[0].endTime }}
              </view>
            </view>
            <u-icon name="arrow-right" color="#999" size="20"></u-icon>
          </view>
        </view>
      </template>
    </base-scroll>
  </view>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let { chooseRegionData, chooseShop } = toRefs(proxy.$store.system.useSystemStore());

onMounted(() => {
  init();
});

async function init() {}

function goProductPage(data) {
  chooseShop.value = data;
  uni.switchTab({ url: '/pages/product/index' });
}
</script>

<style lang="scss" scoped></style>
