import request from '@/utils/request';

const BASE_API = '/mini/api/mall/sms/shop';

export default {
  page(params) {
    return request({
      url: BASE_API + '/page',
      method: 'get',
      params: params,
    });
  },
  // 最近门店
  lately(params) {
    return request({
      url: BASE_API + '/lately',
      method: 'get',
      params: params,
    });
  },
};
