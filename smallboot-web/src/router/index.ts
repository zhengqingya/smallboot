import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import useStore from '@/store'

// 静态路由
export const constantRoutes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    component: () => import('@/views/login/index.vue'),
  },
  {
    path: '/test',
    component: () => import('@/views/test/index.vue'),
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404.vue'),
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401.vue'),
  },
]

// 创建路由
const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes as RouteRecordRaw[],
})

// 重置路由
export function resetRouter() {
  const { permission } = useStore()
  permission.routes.forEach((route) => {
    const name = route.name
    if (name && router.hasRoute(name)) {
      router.removeRoute(name)
    }
  })
}

export default router
