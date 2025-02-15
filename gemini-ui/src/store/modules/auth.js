import { $AuthApi } from '@/api'
import { dynamicRoutes } from '@/router/routes'
import { RouteUtil, TokenUtil } from '@/utils'
import { cloneDeep } from 'lodash'
import { defineStore } from 'pinia'

export const useAuthStore = defineStore('authStore', {
  state: () => ({
    token: TokenUtil.getToken(),
    permRoutes: [],
    info: undefined
  }),
  actions: {
    login(loginInfo) {
      const { phone, password } = loginInfo
      return new Promise((resolve, reject) => {
        $AuthApi
          .login({ phone, password })
          .then(res => {
            TokenUtil.setToken(res)
            this.token = res
            resolve()
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    getInfo() {
      return new Promise((resolve, reject) => {
        $AuthApi
          .getInfo()
          .then(res => {
            this.info = res
            const { isAdmin } = res
            const cloneRoutes = cloneDeep(dynamicRoutes)
            const permRoutes = RouteUtil.filterAuthRoutes(cloneRoutes, isAdmin)
            this.permRoutes = permRoutes
            resolve(permRoutes)
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    logout() {
      this.token = ''
      this.permRoutes = []
      this.info = undefined
      TokenUtil.removeToken()
    }
  }
})
