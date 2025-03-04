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
  let isSuperAdmin = ref(false); // 是否为超管
  let isSuperOrSystemAdmin = ref(false); // 是否为系统管理员|超管

  // 登录
  async function login(loginObj) {
    if (isLogin.value) {
      return;
    }
    tenantId.value = loginObj.tenantId;
    loginBeforeUrl.value = route.path;
    let result = await api.sys_user.login({
      username: loginObj.username.trim(),
      password: loginObj.password.trim(),
    });
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
    let result = await api.sys_user.getUserPerm();
    userObj.value = result.data;
    if (userObj.value.roleCodeList && userObj.value.roleCodeList.includes('system_admin')) {
      isSuperAdmin.value = true;
    }
    if (isSuperAdmin.value || userObj.value.roleCodeList.includes('super_admin')) {
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

      // 组件页面显示处理  () => import('@/views/login/index.vue')

      if (permItem.component) {
        let localCp = views[`/src/views/${permItem.component}.vue`];
        if (localCp) {
          // console.log('222', permItem.component, localCp);
          permItem.component = localCp;
        }
      }

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

  return { tenantId, tenantList, isLogin, loginBeforeUrl, login, logout, tokenObj, userObj, getUserInfo, routerList, routerMap, isSuperAdmin, isSuperOrSystemAdmin };
});
