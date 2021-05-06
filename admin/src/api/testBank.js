import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/testBank',
    method: 'get',
    params
  })
}

export function add(testBankForm) {
  return request({
    url: '/testBank',
    method: 'post',
    data: testBankForm
  })
}

export function update(testBankForm) {
  return request({
    url: '/testBank',
    method: 'put',
    data: testBankForm
  })
}

export function remove(testBankId) {
  return request({
    url: '/testBank/' + testBankId,
    method: 'delete'
  })
}
