<script setup>
import Logo from '@/assets/vue.svg'
import { useAppStore } from '@/store'

const appStore = useAppStore()
const title = import.meta.env.VITE_APP_TITLE
</script>

<template>
  <div flex justify-center items-center f-c-c h-56>
    <img :src="Logo" alt="logo" />
    <h2 v-show="!appStore.menuCollapsed" ml-8 max-w-120 flex-shrink-0 text-16 color-primary font-bold>
      {{ title }}
    </h2>
  </div>
</template>
