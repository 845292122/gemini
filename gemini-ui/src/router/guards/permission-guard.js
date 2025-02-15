import { useAuthStore } from '@/store'

const WHITE_LIST = ['/login', '/404']
export function createPermissionGuard(router) {
  router.beforeEach(async to => {
    const authStore = useAuthStore()
    const token = authStore.token

    /** 没有token */
    if (!token) {
      if (WHITE_LIST.includes(to.path)) return true
      return { path: '/login' }
    }

    /** 有token */
    if (to.path === '/login') return { path: '/' }
    if (WHITE_LIST.includes(to.path)) return true

    if (!authStore.info) {
      const permRoutes = await authStore.getInfo()
      permRoutes.forEach(route => {
        router.addRoute(route)
      })
      return { ...to, replace: true }
    }

    return true
  })
}
