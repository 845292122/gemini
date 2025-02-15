import { defineStore } from 'pinia'

export const useAppStore = defineStore('appStore', {
  state: () => ({
    menuCollapsed: false
  }),
  actions: {
    toggleMenuCollapsed() {
      this.menuCollapsed = !this.menuCollapsed
    }
  }
})
