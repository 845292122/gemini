export const RouteUtil = {
  filterAuthRoutes: (routes, isAdmin) => {
    let adminBool = false
    if (isAdmin === 1) {
      adminBool = true
    }
    return routes.filter(route => {
      if (Array.isArray(route.children)) {
        route.children = RouteUtil.filterAuthRoutes(route.children, isAdmin)
      }
      if (!route.requireAdmin) return true
      return adminBool === route.requireAdmin
    })
  },
  filterHiddenRoutes: routes => {
    return routes.filter(route => {
      if (route.hidden && route.hidden === true) {
        return false
      }
      if (route.children) {
        route.children = RouteUtil.filterHiddenRoutes(route.children)
      }
      return true
    })
  }
}
