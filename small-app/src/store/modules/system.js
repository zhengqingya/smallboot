import { defineStore } from 'pinia';

export const useSystemStore = defineStore('system', () => {
  let chooseRegionData = ref({}); // 选择的区域数据
  let chooseShop = ref({}); // 选择的店铺信息

  return { chooseRegionData, chooseShop };
});
