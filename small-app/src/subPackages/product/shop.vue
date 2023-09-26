<template>
  <view class="h-full flex-column">
    <view class="region flex-start-center p-x-20" style="height: 100rpx">
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
        longitude: userGeo.longitude,
        latitude: userGeo.latitude,
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
            <view class="flex-center-center">
              <view class="font-size-sm">距离{{ $filters.calDistance(item.distance) }}</view>
              <u-icon name="arrow-right" color="#999" size="20" />
            </view>
          </view>
        </view>
      </template>
    </base-scroll>
  </view>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let { chooseRegionData, chooseShop } = toRefs(proxy.$store.system.useSystemStore());
let { userGeo } = toRefs(proxy.$store.user.useUserStore());

onMounted(() => {
  init();
});

async function init() {
  if (!chooseRegionData.value.name) {
    if (chooseShop.value.areaName) {
      chooseRegionData.value.name = chooseShop.value.areaName;
    } else {
      uni.redirectTo({ url: '/subPackages/product/region' });
    }
  }
}

function goProductPage(data) {
  chooseShop.value = data;
  uni.switchTab({ url: '/pages/product/index' });
}
</script>

<style lang="scss" scoped>
.region {
  box-shadow: 1rpx 0 5rpx rgba(0, 0, 0, 0.2);
}
</style>
