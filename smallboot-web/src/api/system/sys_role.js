import request from '@/utils/request'

const BASE_API = '/web/api/system/role'

export default {
  listPage(query, headers) {
    return request({
      url: BASE_API + '/listPage',
      method: 'get',
      params: query,
      headers,
    })
  },
  list(query) {
    return request({
      url: BASE_API + '/list',
      method: 'get',
      params: query,
    })
  },
  detail(id) {
    return request({
      url: BASE_API + '/detail',
      method: 'get',
      params: { roleId: id },
    })
  },
  add(data) {
    return request({
      url: BASE_API,
      method: 'post',
      data,
    })
  },
  update(data) {
    return request({
      url: BASE_API,
      method: 'put',
      data,
    })
  },
  delete(id) {
    return request({
      url: BASE_API,
      method: 'delete',
      params: { roleId: id },
    })
  },
  updateStatus(id, status) {
    return request({
      url: BASE_API + '/updateStatus',
      method: 'post',
      data: { roleId: id, status: status },
    })
  },

  /**
   * 获取角色管理 页面中的权限按钮被选中的按钮
   */
  getPermissionBtnsByRoleIdAndMenuId(data) {
    return request({
      url: BASE_API + '/getPermissionBtnsByRoleIdAndMenuId',
      method: 'get',
      params: data,
    })
  },
  /**
   * 保存角色管理页面中的权限按钮被选中的按钮
   */
  savePermissionBtnIds(data) {
    return request({
      url: BASE_API + '/saveRoleMenuBtnIds',
      method: 'post',
      data,
    })
  },
  savePermissionMenuIds(data) {
    return request({
      url: BASE_API + '/saveRoleMenuIds',
      method: 'post',
      data,
    })
  },
  saveRolePermission(data) {
    return request({
      url: BASE_API + '/saveRolePermission',
      method: 'post',
      data,
    })
  },
}
