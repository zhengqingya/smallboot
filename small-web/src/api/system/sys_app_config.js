import request from '@/utils/request';

const BASE_API = '/web/api/system/app';

export default {
  genLink() {
    return request({
      url: BASE_API + '/genLink',
      method: 'get',
    });
  },
  qrcode(params) {
    return request({
      url: BASE_API + '/qrcode',
      method: 'get',
      params: params,
      responseType: 'blob',
    });
  },
  appOperationBatch(data) {
    return request({
      url: BASE_API + '/appOperationBatch',
      method: 'post',
      data: data,
    });
  },
};
