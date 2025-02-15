import { createRouter, createWebHistory } from 'vue-router'
import { setupRouterGuard } from './guards'
import { dynamicRoutes, staticRoutes } from './routes'

export const router = createRouter({
  history: createWebHistory(),
  routes: staticRoutes.concat(dynamicRoutes),
  scrollBehavior: () => ({ left: 0, top: 0 })
})

export async function setupRouter(app) {
  app.use(router)
  setupRouterGuard(router)
}
