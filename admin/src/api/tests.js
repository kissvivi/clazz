import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/tests',
    method: 'get',
    params
  })
}

export function add(testsForm) {
  return request({
    url: '/tests',
    method: 'post',
    data: testsForm
  })
}

export function update(testsForm) {
  return request({
    url: '/tests',
    method: 'put',
    data: testsForm
  })
}

export function remove(testsId) {
  return request({
    url: '/tests/' + testsId,
    method: 'delete'
  })
}
