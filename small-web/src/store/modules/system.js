import { defineStore } from 'pinia';
import sys_province_city_area from '@/api/system/sys_province_city_area.js';

export const useSystemStore = defineStore('system', () => {
  let provinceCityAreaList = ref([]); // 省市区数据

  // 初始化省市区数据
  async function initProvinceCityAreaList() {
    let res = await sys_province_city_area.tree();
    provinceCityAreaList.value = res.data;
  }

  return { provinceCityAreaList, initProvinceCityAreaList };
});
