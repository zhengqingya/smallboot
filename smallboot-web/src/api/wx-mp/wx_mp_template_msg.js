import request from '@/utils/request'
import { localStorage } from '@/utils/storage'

const BASE_API = '/web/api/wx/mp/templateMsg/' + localStorage.get('appId')

export default {
  page(query, headers) {
    return request({
      url: BASE_API + '/page',
      method: 'get',
      params: query,
      headers,
    })
  },
  sync(form) {
    return request({
      url: BASE_API + '/sync',
      method: 'post',
      data: form,
    })
  },
}
