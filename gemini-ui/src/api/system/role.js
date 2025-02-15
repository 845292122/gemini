import { service } from '@/utils'

const baseURL = '/system/role'

export const RoleApi = {
  getRoleList: params => service.get(`${baseURL}/list`, { params }),
  getRoleInfo: id => service.get(`${baseURL}/${id}`),
  createRole: data => service.post(`${baseURL}`, data),
  modifyRole: data => service.put(`${baseURL}`, data),
  removeRole: id => service.delete(`${baseURL}/${id}`)
}
