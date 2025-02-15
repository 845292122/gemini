<script setup>
import { useRoute } from 'vue-router'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import plusZhCn from 'plus-pro-components/es/locale/lang/zh-cn'

const zhCnLocales = {
  ...zhCn,
  ...plusZhCn
}

const btnConfig = reactive({
  autoInsertSpace: true
})
const route = useRoute()
const layouts = new Map()

function getLayout(name) {
  // 利用map将加载过的layout缓存起来，防止重新加载layout导致页面闪烁
  if (layouts.get(name)) return layouts.get(name)
  const layout = markRaw(defineAsyncComponent(() => import(`@/layout/${name}/index.vue`)))
  layouts.set(name, layout)
  return layout
}

const defaultLayout = 'simple'
const Layout = computed(() => {
  if (!route.matched?.length) return null
  return getLayout(route.meta?.layout || defaultLayout)
})
</script>

<template>
  <el-config-provider :button="btnConfig" :locale="zhCnLocales">
    <router-view v-slot="{ Component, route: curRoute }">
      <component :is="Layout">
        <component :is="Component" :key="curRoute.fullPath" />
      </component>
    </router-view>
  </el-config-provider>
</template>
