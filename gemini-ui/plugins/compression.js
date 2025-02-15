import compression from 'vite-plugin-compression'

/**
 * 打包压缩插件
 */
export default (isBuild) => {
  const plugin = []
  if (isBuild) {
    plugin.push(
      compression({
        ext: '.gz',
        deleteOriginFile: false,
      })
    )
  }
  return plugin
}
