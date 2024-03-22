import request from '@/utils/request';

const BASE_API = '/web/api/mall/category';

export default {
  page(query, headers) {
    return request({
      url: BASE_API + '/page',
      method: 'get',
      params: query,
      headers,
    });
  },
  list(query, headers) {
    return request({
      url: BASE_API + '/list',
      method: 'get',
      params: query,
      headers,
    });
  },
  tree(query) {
    return request({
      url: BASE_API + '/tree',
      method: 'get',
      params: query,
    });
  },
  add(data) {
    return request({
      url: BASE_API,
      method: 'post',
      data: data,
    });
  },
  update(data) {
    return request({
      url: BASE_API,
      method: 'put',
      data: data,
    });
  },
  deleteBatch(params) {
    return request({
      url: BASE_API + '/deleteBatch',
      method: 'delete',
      params: params,
    });
  },
  updateBatchShow(data) {
    return request({
      url: BASE_API + '/updateBatchShow',
      method: 'put',
      data: data,
    });
  },
};
