import { service } from '@/utils'

const baseURL = '/system/permission'

export const PermissionApi = {
  getPermissionList: params => service.get(`${baseURL}/list`, { params }),
  getPermissionInfo: id => service.get(`${baseURL}/${id}`),
  createPermission: data => service.post(`${baseURL}`, data),
  modifyPermission: data => service.put(`${baseURL}`, data),
  removePermission: id => service.delete(`${baseURL}/${id}`)
}
