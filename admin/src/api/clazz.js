import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/clazz',
    method: 'get',
    params
  })
}

export function getQRCode(params) {
  return request({
    url: '/clazz/getQRCode',
    method: 'get',
    params
  })
}

export function add(clazzForm) {
  return request({
    url: '/clazz',
    method: 'post',
    data: clazzForm
  })
}

export function update(clazzForm) {
  return request({
    url: '/clazz',
    method: 'put',
    data: clazzForm
  })
}

export function remove(clazzId) {
  return request({
    url: '/clazz/' + clazzId,
    method: 'delete'
  })
}
