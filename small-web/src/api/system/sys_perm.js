import request from '@/utils/request';

const BASE_API = '/web/api/system/perm';

export default {
  // 根据角色id拿到关联的数据权限
  getScopeIdListByRoleId(params) {
    return request({
      url: BASE_API + '/getScopeIdListByRoleId',
      method: 'get',
      params,
    });
  },
  // 保存角色权限（菜单权限+按钮权限+数据权限）
  saveRoleRePerm(data) {
    return request({
      url: BASE_API + '/saveRoleRePerm',
      method: 'post',
      data,
    });
  },
};
