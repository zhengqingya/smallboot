import { defineStore } from 'pinia';
import api from '@/api';
import config from '@/config.js';
import store from '@/store';

export const useUserStore = defineStore('user', () => {
  let isLogin = ref(false);
  let userObj = ref({});
  let userGeoObj = ref({}); // 用户授权后存储的地理位置
  let isTest = ref(config.baseUrl.includes('127.0.0.1'));

  // 登录
  async function login() {
    if (isLogin.value) {
      return;
    }
    uni.login({
      provider: 'weixin',
      success: async (res) => {
        // console.log(res);
        // 请求后台获取openid注册登录成功后返回基本用户信息
        let apiRes = await api.user.login({
          code: res.code,
          // iv: data.iv,
          // encryptedData: data.encryptedData,
          // userInfo: {
          //     nickName: data.userInfo.nickName,
          //     avatarUrl: data.userInfo.avatarUrl,
          // },
        });
        isLogin.value = true;
        userObj.value = apiRes.data;
      },
    });
  }

  // watch：监听器
  watch(isLogin, (newValue, oldValue) => {
    if (newValue) {
      if (isTest.value) {
        // #ifdef H5
        userGeoObj.value = { longitude: 104.0752, latitude: 30.55262 }; // 天府三街定位 TODO 本地测试使用
        // #endif
      }
      // 初始化系统设置数据
      store.system.useSystemStore().init();
    }
  });

  // 退出登录
  function logout() {
    isLogin.value = false;
    userObj.value = {};
  }

  // 仅测试使用
  async function localLogin() {
    let res = await api.user.login({
      code: '1',
      iv: '1',
      encryptedData: '1',
      isLocalLogin: true,
    });
    // console.log('用户信息：', res);
    isLogin.value = true;
    userObj.value = res.data;
  }

  return { isLogin, isTest, userObj, userGeoObj, login, logout, localLogin };
});
