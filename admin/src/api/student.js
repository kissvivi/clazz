import request from '@/utils/request'

export function list(params) {
  return request({
    url: '/student',
    method: 'get',
    params
  })
}

export function add(studentForm) {
  return request({
    url: '/student',
    method: 'post',
    data: studentForm
  })
}

export function update(studentForm) {
  return request({
    url: '/student',
    method: 'put',
    data: studentForm
  })
}

export function remove(studentId) {
  return request({
    url: '/student/' + studentId,
    method: 'delete'
  })
}



export function resetPassword(studentId) {
  return request({
    url: '/student/resetPassword/' + studentId,
    method: 'put'
  })
}
