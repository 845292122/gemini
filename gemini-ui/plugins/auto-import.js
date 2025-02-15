import AutoImport from 'unplugin-auto-import/vite'
import IconsResolver from 'unplugin-icons/resolver'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

export default () => {
  return AutoImport({
    imports: ['vue'],
    resolvers: [
      ElementPlusResolver({ importStyle: 'sass' }),
      IconsResolver({}),
    ],
  })
}
