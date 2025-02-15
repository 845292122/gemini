<script setup>
import { useAppStore } from '@/store'
import Logo from '../components/logo'
import Menu from '../components/menu'

const appStore = useAppStore()
</script>

<template>
  <Logo border-b="1px solid #e8e8e8" />
  <Menu h-0 flex-1 mt-4 />
</template>
