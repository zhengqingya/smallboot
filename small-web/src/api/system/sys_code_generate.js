import request from '@/utils/request';

const BASE_API = '/web/api/system/codeGenerate';

export default {
  getConfig() {
    return request({
      url: BASE_API + '/getConfig',
      method: 'get',
    });
  },
  saveConfig(data) {
    return request({
      url: BASE_API + '/saveConfig',
      method: 'post',
      data: data,
    });
  },
  generateTplData(data) {
    return request({
      url: BASE_API + '/generateTplData',
      method: 'post',
      data: data,
    });
  },
};
