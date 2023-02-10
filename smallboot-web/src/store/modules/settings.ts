import { defineStore } from 'pinia'
import { SettingState } from '@/types/store/setting'
import { localStorage } from '@/utils/storage'

export const useSettingStore = defineStore({
  id: 'setting',
  state: (): SettingState => ({
    // tagsView: localStorage.get('tagsView') != null ? localStorage.get('tagsView') : true,
    tagsView: false,
  }),
})

export default useSettingStore
