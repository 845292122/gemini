import { service } from '@/utils'

const baseURL = '/account'

export const AccountApi = {
  page: params => service.get(`${baseURL}/page`, { params })
}
