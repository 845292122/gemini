module.exports = {
    env: {
      browser: true,
      es2021: true,
    },
    extends: [
      'eslint:recommended',
      'plugin:vue/vue3-essential',
      'plugin:prettier/recommended',
    ],
    overrides: [
      {
        env: {
          node: true,
        },
        files: ['.eslintrc.{js,cjs}'],
        parserOptions: {
          sourceType: 'script',
        },
      },
    ],
    parserOptions: {
      ecmaVersion: 'latest',
      sourceType: 'module',
    },
    plugins: ['vue'],
    rules: {
      quotes: ['error', 'single'],
      semi: [2, 'never'],
      'vue/multi-word-component-names': 'off',
      'no-undef': 'off',
      'vue/no-mutating-props': 'off',
      indent: ['error', 2, { SwitchCase: 1 }],
    },
  }
  