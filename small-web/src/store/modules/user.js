import { defineStore } from 'pinia';
import api from '@/api';
// 动态导入拿到所有页面 eg: {/src/views/test/index.vue: () => import("/src/views/test/index.vue")}
const views = import.meta.glob('@/views/**/**.vue');
import { useRoute, useRouter } from 'vue-router';
import store from '@/store';

export const useUserStore = defineStore('user', () => {
  const route = useRoute();
  const router = useRouter();
  let isLogin = ref(false);
  let tenantId = ref({});
  let tokenObj = ref({});
  let userObj = ref({});
  let routerMap = ref({}); // 全路径'/system/user' -> 路由信息
  let loginBeforeUrl = ref(''); // 登录前的路径
  let tenantList = ref([]); // 租户列表
  let isSuperOrSystemAdmin = ref(false); // 是否为系统管理员|超管

  // 登录
  async function login(loginObj) {
    if (isLogin.value) {
      return;
    }
    tenantId.value = loginObj.tenantId;
    loginBeforeUrl.value = route.path;
    let result = {
      code: 200,
      msg: 'OK',
      data: {
        tokenName: 'Authorization',
        tokenValue: 'Bearer B:2:1783462849612666666',
      },
    };
    isLogin.value = true;
    tokenObj.value = result.data;
    // getUserInfo();
  }

  // 退出登录
  function logout() {
    let localLoginUrl = loginBeforeUrl.value; // 先存储下，防止下面清空掉

    isLogin.value = false;

    // 清空pinia存储的数据
    this.$reset();

    store.settings.useSettingsStore().$reset();

    // window.localStorage.setItem('user2', 'hello');
    // window.localStorage.removeItem('user2');
    // tips: pinia持久化的无法通过这种方式清空数据，只能删除同样方式存储的值 eg: window.localStorage.setItem('user2', 'hello');
    window.localStorage.clear();
    window.sessionStorage.clear();

    isLogin.value = false; // 放在这里，防止pinia数据未清空，路由跳转过去时还是登录状态，然后就会出现死循环问题

    // 跳转登录页
    router.push(`${localLoginUrl}?redirect=${route.fullPath}`);
    location.reload(); // 强制刷新页面
  }

  // 获取用户 & 权限数据
  async function getUserInfo() {
    let result = {
      code: 200,
      msg: 'OK',
      data: {
        userId: 2,
        username: 'admin',
        nickname: '系统管理员',
        sex: 0,
        sexName: '未知',
        phone: '',
        email: '',
        avatarUrl: 'http://127.0.0.1:886/2023-10-08/1710975930417258496-小知识.jpg',
        createTime: '2020-08-22 15:01:51',
        deptId: null,
        postIdList: [1],
        roleIdList: [2],
        roleCodeList: ['system_admin'],
        roleNameList: ['系统管理员'],
        permissionTreeList: [
          {
            id: 1,
            parentId: 0,
            name: '首页',
            fullName: '/首页',
            icon: 'Odometer',
            path: '/',
            btnPerm: '',
            sort: 1,
            component: 'dashboard',
            redirect: '',
            isShow: true,
            isShowBreadcrumb: true,
            children: [],
            meta: {
              isShow: true,
              sort: 1,
              title: '首页',
              icon: 'Odometer',
              isShowBreadcrumb: true,
            },
            type: 1,
            isHasPerm: true,
          },
          {
            id: 2,
            parentId: 0,
            name: '租户管理',
            fullName: '/租户管理',
            icon: 'Star',
            path: '/tenant',
            btnPerm: '',
            sort: 2,
            component: '',
            redirect: '',
            isShow: true,
            isShowBreadcrumb: true,
            children: [
              {
                id: 32,
                parentId: 2,
                name: '租户列表',
                fullName: '/租户管理/租户列表',
                icon: 'List',
                path: 'tenant',
                btnPerm: '',
                sort: 1,
                component: 'system/tenant',
                redirect: '',
                isShow: true,
                isShowBreadcrumb: true,
                children: [],
                meta: {
                  isShow: true,
                  sort: 1,
                  title: '租户列表',
                  icon: 'List',
                  isShowBreadcrumb: true,
                },
                type: 1,
                isHasPerm: true,
              },
              {
                id: 31,
                parentId: 2,
                name: '租户套餐',
                fullName: '/租户管理/租户套餐',
                icon: 'Management',
                path: 'tenant-package',
                btnPerm: '',
                sort: 2,
                component: 'system/tenant-package',
                redirect: '',
                isShow: true,
                isShowBreadcrumb: true,
                children: [],
                meta: {
                  isShow: true,
                  sort: 2,
                  title: '租户套餐',
                  icon: 'Management',
                  isShowBreadcrumb: true,
                },
                type: 1,
                isHasPerm: true,
              },
            ],
            meta: {
              isShow: true,
              sort: 2,
              title: '租户管理',
              icon: 'Star',
              isShowBreadcrumb: true,
            },
            type: 1,
            isHasPerm: true,
          },
          {
            id: 3,
            parentId: 0,
            name: '系统管理',
            fullName: '/系统管理',
            icon: 'Setting',
            path: '/system',
            btnPerm: '',
            sort: 4,
            component: '',
            redirect: '',
            isShow: true,
            isShowBreadcrumb: true,
            children: [
              {
                id: 4,
                parentId: 3,
                name: '个人中心',
                fullName: '/系统管理/个人中心',
                icon: 'Avatar',
                path: 'personal-center',
                btnPerm: '',
                sort: 2,
                component: 'system/personal-center',
                redirect: '',
                isShow: false,
                isShowBreadcrumb: true,
                children: [],
                meta: {
                  isShow: false,
                  sort: 2,
                  title: '个人中心',
                  icon: 'Avatar',
                  isShowBreadcrumb: true,
                },
                type: 1,
                isHasPerm: true,
              },
              {
                id: 5,
                parentId: 3,
                name: '用户管理',
                fullName: '/系统管理/用户管理',
                icon: 'User',
                path: 'user-sys',
                btnPerm: '',
                sort: 11,
                component: 'system/user-sys',
                redirect: '',
                isShow: true,
                isShowBreadcrumb: true,
                children: [
                  {
                    id: 44,
                    parentId: 5,
                    name: '查看用户分页列表',
                    fullName: '/系统管理/用户管理/查看用户分页列表',
                    icon: '',
                    path: 'GET:/web/api/system/user/page',
                    btnPerm: 'sys:user:page',
                    sort: 1,
                    component: '',
                    redirect: '',
                    isShow: false,
                    isShowBreadcrumb: false,
                    children: [],
                    meta: {
                      isShow: false,
                      sort: 1,
                      title: '查看用户分页列表',
                      icon: '',
                      isShowBreadcrumb: false,
                    },
                    type: 2,
                    isHasPerm: true,
                  },
                  {
                    id: 45,
                    parentId: 5,
                    name: '编辑用户',
                    fullName: '/系统管理/用户管理/编辑用户',
                    icon: '',
                    path: 'PUT:/web/api/system/user',
                    btnPerm: 'sys:user:edit',
                    sort: 2,
                    component: '',
                    redirect: '',
                    isShow: false,
                    isShowBreadcrumb: false,
                    children: [],
                    meta: {
                      isShow: false,
                      sort: 2,
                      title: '编辑用户',
                      icon: '',
                      isShowBreadcrumb: false,
                    },
                    type: 2,
                    isHasPerm: true,
                  },
                  {
                    id: 46,
                    parentId: 5,
                    name: '新增用户',
                    fullName: '/系统管理/用户管理/新增用户',
                    icon: '',
                    path: 'POST:/web/api/system/user',
                    btnPerm: 'sys:user:add',
                    sort: 3,
                    component: '',
                    redirect: '',
                    isShow: false,
                    isShowBreadcrumb: false,
                    children: [],
                    meta: {
                      isShow: false,
                      sort: 3,
                      title: '新增用户',
                      icon: '',
                      isShowBreadcrumb: false,
                    },
                    type: 2,
                    isHasPerm: true,
                  },
                  {
                    id: 47,
                    parentId: 5,
                    name: '删除用户',
                    fullName: '/系统管理/用户管理/删除用户',
                    icon: '',
                    path: 'DELETE:/web/api/system/user',
                    btnPerm: 'sys:user:delete',
                    sort: 4,
                    component: '',
                    redirect: '',
                    isShow: false,
                    isShowBreadcrumb: false,
                    children: [],
                    meta: {
                      isShow: false,
                      sort: 4,
                      title: '删除用户',
                      icon: '',
                      isShowBreadcrumb: false,
                    },
                    type: 2,
                    isHasPerm: true,
                  },
                ],
                meta: {
                  isShow: true,
                  sort: 11,
                  title: '用户管理',
                  icon: 'User',
                  isShowBreadcrumb: true,
                },
                type: 1,
                isHasPerm: true,
              },
              {
                id: 6,
                parentId: 3,
                name: '角色管理',
                fullName: '/系统管理/角色管理',
                icon: 'StarFilled',
                path: 'role',
                btnPerm: '',
                sort: 12,
                component: 'system/role',
                redirect: '',
                isShow: true,
                isShowBreadcrumb: true,
                children: [],
                meta: {
                  isShow: true,
                  sort: 12,
                  title: '角色管理',
                  icon: 'StarFilled',
                  isShowBreadcrumb: true,
                },
                type: 1,
                isHasPerm: true,
              },
              {
                id: 7,
                parentId: 3,
                name: '角色权限',
                fullName: '/系统管理/角色权限',
                icon: '',
                path: 'role-edit',
                btnPerm: '',
                sort: 13,
                component: 'system/role-edit',
                redirect: '',
                isShow: false,
                isShowBreadcrumb: false,
                children: [],
                meta: {
                  isShow: false,
                  sort: 13,
                  title: '角色权限',
                  icon: '',
                  isShowBreadcrumb: false,
                },
                type: 1,
                isHasPerm: true,
              },
              {
                id: 33,
                parentId: 3,
                name: '企业管理',
                fullName: '/系统管理/企业管理',
                icon: 'List',
                path: 'dept',
                btnPerm: '',
                sort: 16,
                component: 'system/dept',
                redirect: '',
                isShow: true,
                isShowBreadcrumb: true,
                children: [],
                meta: {
                  isShow: true,
                  sort: 16,
                  title: '企业管理',
                  icon: 'List',
                  isShowBreadcrumb: true,
                },
                type: 1,
                isHasPerm: true,
              },
              {
                id: 30,
                parentId: 3,
                name: '文件上传记录',
                fullName: '/系统管理/文件上传记录',
                icon: 'Files',
                path: 'file',
                btnPerm: '',
                sort: 22,
                component: 'system/file',
                redirect: '',
                isShow: true,
                isShowBreadcrumb: true,
                children: [],
                meta: {
                  isShow: true,
                  sort: 22,
                  title: '文件上传记录',
                  icon: 'Files',
                  isShowBreadcrumb: true,
                },
                type: 1,
                isHasPerm: true,
              },
              {
                id: 50,
                parentId: 3,
                name: '操作日志',
                fullName: '/系统管理/操作日志',
                icon: 'ChatLineSquare',
                path: 'log',
                btnPerm: '',
                sort: 100,
                component: 'system/log',
                redirect: '',
                isShow: true,
                isShowBreadcrumb: true,
                children: [],
                meta: {
                  isShow: true,
                  sort: 100,
                  title: '操作日志',
                  icon: 'ChatLineSquare',
                  isShowBreadcrumb: true,
                },
                type: 1,
                isHasPerm: true,
              },
            ],
            meta: {
              isShow: true,
              sort: 4,
              title: '系统管理',
              icon: 'Setting',
              isShowBreadcrumb: true,
            },
            type: 1,
            isHasPerm: true,
          },
          {
            id: 24,
            parentId: 0,
            name: '小程序用户',
            fullName: '/小程序用户',
            icon: 'User',
            path: '/mini-user',
            btnPerm: '',
            sort: 6,
            component: 'system/user-mini',
            redirect: '',
            isShow: true,
            isShowBreadcrumb: true,
            children: [],
            meta: {
              isShow: true,
              sort: 6,
              title: '小程序用户',
              icon: 'User',
              isShowBreadcrumb: true,
            },
            type: 1,
            isHasPerm: true,
          },
        ],
        tenantId: 1,
        tenantName: 'SmallBoot',
      },
    };
    userObj.value = result.data;
    if ((userObj.value.roleCodeList && userObj.value.roleCodeList.includes('system_admin')) || userObj.value.roleCodeList.includes('super_admin')) {
      isSuperOrSystemAdmin.value = true;
    }

    // 初始化系统设置数据
    store.system.useSystemStore().init();

    // 初始化租户数据
    initTenant();
  }

  async function initTenant() {
    if (isSuperOrSystemAdmin.value) {
      let res = await api.sys_tenant.list();
      tenantList.value = res.data;
      tenantList.value.unshift({ id: -1, name: '全部租户' });
    }
  }

  const routerList = computed(() => {
    // 拿到后台的权限数据
    return generateRouterList({}, userObj.value.permissionTreeList);
  });

  // 生成侧边栏菜单 & 权限路由数据
  function generateRouterList(parentObj, permList) {
    let result = [];
    if (!permList || permList.length === 0) {
      return result;
    }

    for (let index = 0; index < permList.length; index++) {
      let permItem = permList[index];

      // 填充字段数据
      if (permItem.parentId == 0 && !permItem.path.startsWith('/')) {
        permItem.path = `/${permItem.path}`;
      }
      if (!permItem.meta) {
        permItem.meta = {};
      }
      if (!permItem.meta.isParentView) {
        permItem.meta.isParentView = false;
      }
      if (!permItem.meta.sort) {
        permItem.meta.sort = 10000;
      }

      let title = permItem.meta.title;
      if (title) {
        if (parentObj.meta) {
          // [子级]
          // 面包屑数据
          permItem.meta.breadcrumbItemList = parentObj.meta.breadcrumbItemList.concat([title]);
          // 全路径
          permItem.meta.fullPath = parentObj.meta.fullPath + '/' + permItem.path;
        } else {
          // [顶级]
          permItem.meta.breadcrumbItemList = [title];
          permItem.meta.fullPath = permItem.path;
        }
      }

      // 组件页面显示处理
      permItem.component = views[`/src/views/${permItem.component}.vue`];

      routerMap.value[permItem.meta.fullPath] = permItem;

      // 递归处理
      if (permItem.children.length > 0) {
        permItem.children = generateRouterList(permItem, permItem.children);
      }

      result.push(permItem);
    }

    // 从小到大 升序
    result = result.sort((a, b) => {
      return a.meta.sort - b.meta.sort;
    });
    return result;
  }

  return { tenantId, tenantList, isLogin, loginBeforeUrl, login, logout, tokenObj, userObj, getUserInfo, routerList, routerMap, isSuperOrSystemAdmin };
});
