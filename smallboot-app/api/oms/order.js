import request from '@/utils/request'

const BASE_API = '/mini/api/mall/order'

export default {
	page(params) {
		return request({
			url: BASE_API + '/page',
			method: 'get',
			params: params
		})
	},
	detail(orderNo) {
		return request({
			url: BASE_API + '/' + orderNo,
			method: 'get',
		})
	},
	create(data) {
		return request({
			url: BASE_API + '/create',
			method: 'post',
			data: data
		})
	},
	pay(data) {
		return request({
			url: BASE_API + '/pay',
			method: 'post',
			data: data
		})
	},
}