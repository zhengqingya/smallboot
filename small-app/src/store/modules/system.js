import { defineStore } from 'pinia';

export const useSystemStore = defineStore('system', () => {
  let chooseRegionData = ref({});

  return { chooseRegionData };
});
