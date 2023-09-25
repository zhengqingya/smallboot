import { defineStore } from 'pinia';
import commonApi from '@/api/common/common.js';

export const useSystemStore = defineStore('system', () => {
  let chooseRegionData = ref({}); // 选择的区域数据
  let chooseShop = ref({}); // 选择的店铺信息
  let lbs_qq_key = ref(''); // 腾讯地图key

  // 初始化系统配置
  async function init() {
    initLbs();
  }

  async function initLbs() {
    let res = await commonApi.listSystemConfigByKey({ keyList: 'lbs_qq_key' });
    lbs_qq_key.value = res.data.lbs_qq_key.value;
  }

  return { chooseRegionData, chooseShop, lbs_qq_key, init };
});
