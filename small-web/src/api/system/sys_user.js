import request from '@/utils/request';

const BASE_API = '/web/api/system/user';

export default {
  // 获取验证码
  getCaptcha() {
    return request({
      url: '/captcha?t=' + new Date().getTime().toString(),
      method: 'get',
    });
  },
  // 登录
  login(data) {
    return request({
      url: '/web/api/auth/login',
      method: 'post',
      data,
      // headers: {
      //   // 客户端信息Base64明文：web:123456
      //   Authorization: 'Basic d2ViOjEyMzQ1Ng==',
      // },
    });
  },
  // 注销
  logout() {
    return request({
      url: '/web/api/auth/logout',
      method: 'delete',
    });
  },
  // 获取用户权限
  getUserPerm() {
    return request({
      url: '/web/api/system/perm/getUserPerm',
      method: 'get',
      // params: { systemSource: 0 }
    });
  },
  listPage(query, headers) {
    return request({
      url: BASE_API + '/listPage',
      method: 'get',
      params: query,
      headers,
    });
  },
  list(query) {
    return request({
      url: BASE_API + '/list',
      method: 'get',
      params: query,
    });
  },
  add(data) {
    return request({
      url: BASE_API,
      method: 'post',
      data,
    });
  },
  update(data) {
    return request({
      url: BASE_API,
      method: 'put',
      data,
    });
  },
  delete(id) {
    return request({
      url: BASE_API,
      method: 'delete',
      params: { userId: id },
    });
  },
  updateStatus(id, status) {
    return request({
      url: BASE_API + '/updateStatus',
      method: 'post',
      data: { userId: id, status: status },
    });
  },
  getUserInfoById(userId) {
    return request({
      url: BASE_API + '',
      method: 'get',
      params: {
        userId: userId,
      },
    });
  },
  // 修改密码
  updatePassword(data) {
    return request({
      url: BASE_API + '/updatePassword',
      method: 'put',
      data: data,
    });
  },
};
