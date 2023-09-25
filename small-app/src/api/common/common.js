import request from '@/utils/request';

const BASE_API = '/mini/api';

export default {
  upload(tempFilePath) {
    return request({
      url: BASE_API + '/system/file/upload',
      method: 'post',
      isUploadFile: true,
      filePath: tempFilePath,
    });
  },
  provinceCityAreaTree(query) {
    return request({
      url: BASE_API + '/system/pca/tree',
      method: 'get',
      params: query,
    });
  },
  listSystemConfigByKey(query) {
    return request({
      url: BASE_API + '/system/config/listByKey',
      method: 'get',
      params: query,
    });
  },
};
