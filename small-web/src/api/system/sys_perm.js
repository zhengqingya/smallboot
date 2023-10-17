import request from '@/utils/request';

const BASE_API = '/web/api/system/perm';

export default {
  // 保存角色关联的所有权限数据
  saveRoleRePerm(data) {
    return request({
      url: BASE_API + '/saveRoleRePerm',
      method: 'post',
      data,
    });
  },
  // saveRolePermission(data) {
  //   return request({
  //     url: BASE_API + '/saveRolePermission',
  //     method: 'post',
  //     data,
  //   })
  // },
};
