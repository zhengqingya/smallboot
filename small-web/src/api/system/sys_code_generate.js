import request from '@/utils/request';

const BASE_API = '/web/api/system/codeGenerate';

export default {
  projectPackageTree() {
    return request({
      url: BASE_API + '/projectPackageTree',
      method: 'get',
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
