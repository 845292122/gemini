<script setup>
import { useAppStore } from '@/store'
import Sidebar from './sidebar'
import Header from '../components/header'

const appStore = useAppStore()
</script>

<template>
  <div wh-full flex>
    <div flex-col flex-shrink-0 transition-width-300 :class="appStore.menuCollapsed ? 'w-64' : 'w-220'" class="aside-wrapper">
      <Sidebar />
    </div>
    <div w-0 flex-col flex-1>
      <Header />
      <transition name="fade-slide" mode="out-in" appear>
        <div flex-1 p-12 m-12 h-0>
          <slot />
        </div>
      </transition>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.aside-wrapper {
  box-shadow: 1px 0 5px 1px rgba(0, 0, 0, 0.3);
  background-color: #001628;
  border-right: 1px solid #001628;
}
</style>
