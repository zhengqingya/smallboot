import request from '@/utils/request';

const BASE_API = '/web/api/system/dept';

export default {
  tree(query) {
    return request({
      url: BASE_API + '/tree',
      method: 'get',
      params: query,
    });
  },
  add(data) {
    return request({
      url: BASE_API + '/add',
      method: 'post',
      data: data,
    });
  },
  update(data) {
    return request({
      url: BASE_API + '/update',
      method: 'put',
      data: data,
    });
  },
  delete(params) {
    return request({
      url: BASE_API + '/delete',
      method: 'delete',
      params: params,
    });
  },
};
