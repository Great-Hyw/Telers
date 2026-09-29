import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
 plugins: [vue()],
  server: {
    port: 5173, // 你的前端端口
    proxy: {
      // 当请求以 /api 开头时，转发到后端
      '/api': {
        target: 'http://localhost:8080', // 后端地址
        changeOrigin: true, // 必须开启，用于欺骗后端
        rewrite: (path) => path.replace(/^\/api/, '') // 可选：如果后端不需要 /api 前缀，则去掉它
      }
    }
  }
}
)
