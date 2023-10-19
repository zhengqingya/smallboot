import request from '@/utils/request';

const BASE_API = '/web/api/system/log';

export default {
  page(query, headers) {
    return request({
      url: BASE_API + '/page',
      method: 'get',
      params: query,
      headers,
    });
  },
};
