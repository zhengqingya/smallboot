import request from '@/utils/request';

const BASE_API = '/web/api/system/app/service/config';

export default {
  detail(query) {
    return request({
      url: BASE_API + '/detail',
      method: 'get',
      params: query,
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
