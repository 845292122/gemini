<script setup>
import MenuCollapsed from './menu-collapse.vue'
import { useRoute } from 'vue-router'
import Profile from './profile.vue'

const route = useRoute()
</script>

<template>
  <div h-56 pl-12 pr-24 justify-between items-center sticky top-0 z-1 flex border-b="1px solid #e8e8e8">
    <div flex items-center text-12 w-full>
      <MenuCollapsed class="mr-8" />
      <div class="mr-8 h-20 w-4 rounded bg-primary" />
      <h2>{{ route?.meta.title }}</h2>
      <div ml-auto>
        <Profile />
      </div>
    </div>
  </div>
</template>
