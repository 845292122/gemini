import { service } from '@/utils'

const baseURL = '/system/tenant'

export const TenantApi = {
  getTenantList: params => service.get(`${baseURL}/list`, { params }),
  getTennatInfo: id => service.get(`${baseURL}/${id}`),
  createTenant: data => service.post(`${baseURL}`, data),
  modifyTenant: data => service.put(`${baseURL}`, data),
  removeTenant: id => service.delete(`${baseURL}/${id}`)
}
