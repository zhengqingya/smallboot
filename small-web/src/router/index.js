import { createRouter, createWebHashHistory } from 'vue-router';

// 本地静态路由
export const constantRoutes = [
  // {
  //   path: '/',
  //   component: () => import('@/views/dashboard/index.vue'),
  // },
  {
    path: '/login',
    component: () => import('@/views/login/index.vue'),
    meta: {
      isParentView: true,
    },
  },
  // {
  //   path: '/login/:tenantId(\\d+)',
  //   component: () => import('@/views/login/index.vue'),
  //   meta: {
  //     isParentView: true,
  //   },
  // },
  {
    path: '/test',
    component: () => import('@/views/test/index.vue'),
    hidden: true,
  },
  {
    path: '/test-layout',
    component: () => import('@/views/test/index.vue'),
    // component: () => import('@/layout/index.vue'),
    // component: () => import('@/layout/parentView.vue'),
    meta: {
      isParentView: true,
      xxx: true,
    },
    children: [
      {
        path: 'xxx', // 加斜杠 全路径，不加的话会拼接父path eg：/test-layout/xxx
        component: () => import('@/views/test/index.vue'),
      },
    ],
  },
  {
    // path: '/404',
    path: '/:pathMatch(.*)*', // 防止浏览器刷新时路由未找到警告提示: vue-router.mjs:35 [Vue Router warn]: No match found for location with path "/xxx"
    component: () => import('@/views/error-page/404.vue'),
  },
];

// 创建路由
const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes,
  // 滚动行为 eg: 用户希望查看详情页以后,返回列表页刚刚浏览的位置
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    } else {
      return { x: 0, y: 0 };
    }
  },
});

export default router;
