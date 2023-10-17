import request from '@/utils/request';

const BASE_API = '/web/api/system/menu';

export default {
  tree(params) {
    return request({
      url: BASE_API + '/tree',
      method: 'get',
      params: params,
    });
  },
  add(data) {
    return request({
      url: BASE_API,
      method: 'post',
      data,
    });
  },
  update(data) {
    return request({
      url: BASE_API,
      method: 'put',
      data,
    });
  },
  delete(params) {
    return request({
      url: BASE_API,
      method: 'delete',
      params: params,
    });
  },
};
