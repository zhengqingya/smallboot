import request from '@/utils/request';

const BASE_API = '/web/api/system/file';

export default {
  page(query, headers) {
    return request({
      url: BASE_API + '/page',
      method: 'get',
      params: query,
      headers,
    });
  },
  delete(params) {
    return request({
      url: BASE_API + '/delete',
      method: 'delete',
      params: params,
    });
  },
  upload(data) {
    return request({
      url: BASE_API + '/upload',
      method: 'post',
      data: data,
      isFile: true,
    });
  },
};
