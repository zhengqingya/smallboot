import request from '@/utils/request'

const BASE_API = '/web/api/mall/attr/key'

export default {
  list(query, headers) {
    return request({
      url: BASE_API + '/list',
      method: 'get',
      params: query,
      headers,
    })
  },
  add(data) {
    return request({
      url: BASE_API,
      method: 'post',
      data: data,
    })
  },
  update(data) {
    return request({
      url: BASE_API,
      method: 'put',
      data: data,
    })
  },
  delete(params) {
    return request({
      url: BASE_API,
      method: 'delete',
      params: params,
    })
  },
}
