import { defineConfig } from 'vite';
import uni from '@dcloudio/vite-plugin-uni';
import AutoImport from 'unplugin-auto-import/vite';
const path = require('path');
// 引入UnoCSS
import UnoCSS from 'unocss/vite';
import { presetUno, presetAttributify, presetIcons } from 'unocss';
import presetRemToPx from '@unocss/preset-rem-to-px';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    uni(),
    AutoImport({
      imports: ['vue', 'vue-router'],
    }),
    UnoCSS({
      presets: [
        presetUno(),
        presetAttributify(),
        presetIcons(),
        presetRemToPx({
          // iPhone6 1rpx = 0.5px  =>  1px = 2rpx  =>  1rem = 2px = 4rpx
          baseFontSize: 2,
        }),
      ],
      // 自定义规则 https://unocss.dev/config/rules
      // rules: [['m-1', { margin: '1px' }]],
    }),
  ],
  resolve: {
    // 配置路径别名
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
});
