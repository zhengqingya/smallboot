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
  operationBatch(data) {
    return request({
      url: BASE_API + '/operationBatch',
      method: 'post',
      data: data,
    });
  },
  syncStatus(data) {
    return request({
      url: BASE_API + '/syncStatus',
      method: 'post',
      data: data,
    });
  },
};
