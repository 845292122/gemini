import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

/**
 * 路由加载动画
 */
export function createLoadingGuard(router) {
  router.beforeEach(() => {
    NProgress.start()
  })
  router.afterEach(() => {
    setTimeout(() => {
      NProgress.done()
    }, 100)
  })
  router.onError(() => {
    NProgress.done()
  })
}
