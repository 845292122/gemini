const TOKEN_KEY = 'auth-token'

/**
 * TOKEN工具类
 */
export const TokenUtil = {
  getToken: () => localStorage.getItem(TOKEN_KEY),
  setToken: val => localStorage.setItem(TOKEN_KEY, val),
  removeToken: () => localStorage.removeItem(TOKEN_KEY)
}
