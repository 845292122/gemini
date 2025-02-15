import IconsResolver from 'unplugin-icons/resolver'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'
import Components from 'unplugin-vue-components/vite'
import { PlusProComponentsResolver } from '@plus-pro-components/resolver'

export default () => {
  return Components({
    resolvers: [
      ElementPlusResolver({ importStyle: 'sass' }),
      IconsResolver({
        enabledCollections: ['bi']
      }),
      PlusProComponentsResolver()
    ]
  })
}
