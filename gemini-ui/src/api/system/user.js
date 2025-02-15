import { service } from '@/utils'

const baseURL = '/system/user'

export const UserApi = {
  getUserList: params => service.get(`${baseURL}/list`, { params }),
  getUserInfo: id => service.get(`${baseURL}/${id}`),
  createUser: data => service.post(`${baseURL}`, data),
  modifyUser: data => service.put(`${baseURL}`, data),
  removeUser: id => service.delete(`${baseURL}/${id}`)
}
