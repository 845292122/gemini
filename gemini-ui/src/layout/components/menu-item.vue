<script setup>
import { Icon } from '@iconify/vue'
const props = defineProps({
  menuList: {
    type: Array,
    require: true
  }
})
</script>

<template>
  <template v-for="item in props.menuList" :key="item.path">
    <!-- 有子菜单 -->
    <el-sub-menu :index="item.path" v-if="item.children && item.children.length > 0">
      <template #title v-if="item.meta.icon">
        <el-icon>
          <Icon :icon="item.meta.icon" />
        </el-icon>
        <span>{{ item.meta.title }}</span>
      </template>
      <template #title v-else>
        <span>{{ item.meta.title }}</span>
      </template>
      <MenuItem :menuList="item.children" />
    </el-sub-menu>

    <!-- 没有子菜单 -->
    <el-menu-item :index="item.path" v-else>
      <el-icon v-if="item.meta.icon">
        <Icon :icon="item.meta.icon" />
      </el-icon>
      <template #title>
        <span>{{ item.meta.title }}</span>
      </template>
    </el-menu-item>
  </template>
</template>
