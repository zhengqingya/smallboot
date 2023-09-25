import QQMapWX from '@/utils/qqmap-wx-jssdk.min.js';
import store from '@/store';

export default {
  init() {
    let { lbs_qq_key } = store.system.useSystemStore();
    // 实例化API核心类
    let qqmapsdk = new QQMapWX({
      key: lbs_qq_key,
    });
    return qqmapsdk;
  },
  // 地点搜索 https://lbs.qq.com/miniProgram/jsSdk/jsSdkGuide/methodSearch
  qqmapsdkSearch(keyword) {
    let qqmapsdk = this.init();
    // 调用接口
    qqmapsdk.search({
      keyword: keyword,
      success: function (res) {
        console.log(res);
      },
      fail: function (res) {
        console.log(res);
      },
      complete: function (res) {
        console.log(res);
      },
    });
  },
  // 逆地址解析(坐标位置描述) https://lbs.qq.com/miniProgram/jsSdk/jsSdkGuide/methodReverseGeocoder
  reverseGeocoder(res) {
    let qqmapsdk = this.init();
    //逆地址解析  坐标转地址信息
    qqmapsdk.reverseGeocoder({
      //Object格式
      location: {
        latitude: res.latitude,
        longitude: res.longitude,
      },
      success: function (res) {
        const mapdata = res.result.ad_info;
        console.log('ok:', mapdata);
      },
      fail: function (error) {
        console.error(error);
      },
      complete: function (res) {
        // console.log(res);
      },
    });
  },
};
