import request from '@/utils/request';

const BASE_API = '/web/api/system/perm';

export default {
  // 保存角色关联的菜单&按钮权限数据
  saveRoleReMenu(data) {
    return request({
      url: BASE_API + '/saveRoleReMenu',
      method: 'post',
      data,
    });
  },
  // 保存角色关联的数据权限数据
  saveRoleReScope(data) {
    return request({
      url: BASE_API + '/saveRoleReScope',
      method: 'post',
      data,
    });
  },
};
