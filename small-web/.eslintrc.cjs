module.exports = {
  root: true,
  env: {
    browser: true,
    es2021: true,
    node: true,
  },
  extends: ['eslint:recommended', 'plugin:vue/vue3-recommended', 'prettier', 'prettier/@typescript-eslint'],
  overrides: [],
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
    ecmaFeatures: {
      jsx: true,
    },
  },
  plugins: ['vue', 'prettier'],
  parser: '@typescript-eslint/parser', //定义ESLint的解析器
  rules: {
    'vue/multi-word-component-names': 'off',
    'vue/no-v-model-argument': 'off',
    'no-undef': 'off',
    'prettier/prettier': 'error',
    'no-console': process.env.NODE_ENV === 'prod' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'prod' ? 'error' : 'off',
    'no-unused-vars': 'off',
  },
  globals: { $ref: 'readonly', $computed: 'readonly', $shallowRef: 'readonly', $customRef: 'readonly', $toRef: 'readonly' },
};
