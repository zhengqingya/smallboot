import request from '@/utils/request';

const BASE_API = '/web/api/cms/job/apply';

export default {
  page(query, headers) {
    return request({
      url: BASE_API + '/page',
      method: 'get',
      params: query,
      headers,
    });
  },
  update(data) {
    return request({
      url: BASE_API + '/update',
      method: 'put',
      data: data,
    });
  },
};
