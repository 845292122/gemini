export const _localStorage = {
  setItem: (key, value, expire) => {
    const data = {
      value: value,
      expire: expire ? Date.now() + expire : null
    }
    localStorage.setItem(key, JSON.stringify(data))
  },
  getItem: key => {
    const data = JSON.parse(localStorage.getItem(key))
    if (!data) return null

    if (data.expire && Date.now() > data.expire) {
      localStorage.removeItem(key)
      return null
    }
    return data.value
  },
  removeItem: key => {
    localStorage.removeItem(key)
  }
}

export const _sessionStorage = {
  setItem: (key, value, expire) => {
    const data = {
      value: value,
      expire: expire ? Date.now() + expire : null
    }
    sessionStorage.setItem(key, JSON.stringify(data))
  },
  getItem: key => {
    const data = JSON.parse(sessionStorage.getItem(key))
    if (!data) return null

    if (data.expire && Date.now() > data.expire) {
      sessionStorage.removeItem(key)
      return null
    }
    return data.value
  },
  removeItem: key => {
    sessionStorage.removeItem(key)
  }
}
