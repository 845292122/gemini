import { service } from '@/utils'

const baseURL = '/auth'

export const $AuthApi = {
  login: data => service.post(`${baseURL}/login`, data),
  getInfo: () => service.get(`${baseURL}/info`)
}
