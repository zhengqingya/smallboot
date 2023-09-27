<template>
  <view class="h-full flex-column">
    <view class="region flex-start-center p-x-20">
      <navigator
        hover-class="none"
        :url="'/subPackages/product/region'"
        class="flex-start-center"
        style="height: 80rpx">
        <text class="font-bold font-size-base">{{ chooseRegionData.name }}</text>
        <u-icon name="arrow-right" color="#999" size="16"></u-icon>
      </navigator>
    </view>

    <map
      v-if="markerList.length > 0"
      style="width: 100%; height: 60vh"
      :latitude="markerList[0].latitude"
      :longitude="markerList[0].longitude"
      scale="15"
      :markers="markerList"></map>

    <base-scroll
      class="h-full overflow-y-scroll bg-color-lightgrey"
      @changeData="changeData"
      :isPage="true"
      api="shop.page"
      :params="{
        areaName: chooseRegionData.name,
        longitude: userGeoObj.longitude,
        latitude: userGeoObj.latitude,
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
let { userGeoObj } = toRefs(proxy.$store.user.useUserStore());
let markerList = ref([]); // 地图标点数据

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

function changeData(list) {
  list.forEach((item) => {
    // console.log('111', item);
    markerList.value.push({
      id: item.shopId,
      latitude: item.latitude,
      longitude: item.longitude,
      iconPath: 'http://127.0.0.1:886/2023-09-11/1701130377848147968-美图35.jpg',
      width: 30,
      height: 30,
      callout: {
        display: 'ALWAYS',
        content: item.shopName,
        borderRadius: 2,
        padding: 5,
      },
    });
  });
}
</script>

<style lang="scss" scoped>
.region {
  box-shadow: 1rpx 0 5rpx rgba(0, 0, 0, 0.2);
}
</style>
