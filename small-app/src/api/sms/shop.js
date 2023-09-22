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
};
