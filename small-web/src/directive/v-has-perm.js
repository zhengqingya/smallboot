import store from '@/store';

/**
 * 自定义按钮权限指令 `v-has-perm`
 * single: v-has-perm="'sys:user:add'"
 * array : v-has-perm="['sys:user:add','sys:user:edit']"
 */
export const hasPerm = {
  mounted(el, binding) {
    // 拿到DOM绑定需要的按钮权限
    const { value } = binding;
    if (!value) {
      throw new Error("need perms! Like v-has-perm=\"['sys:user:add','sys:user:edit']\"");
    }
    const requiredPermList = value instanceof Array ? value : [value];
    let { isLogin, userObj } = toRefs(store.user.useUserStore());
    let hasPerm = false;
    if (isLogin.value) {
      const currentRouteUrl = window.location.hash.replace('#', ''); // eg: /system/user
      const btnPermList = getBtnPermList(currentRouteUrl, userObj.value.permissionTreeList, []);
      // console.log('按钮权限判断-当前路由：', currentRouteUrl, '拥有的按钮权限：', btnPermList);
      hasPerm = btnPermList.some((btnPerm) => {
        return requiredPermList.includes(btnPerm);
      });
    }
    if (!hasPerm) {
      el.parentNode && el.parentNode.removeChild(el);
    }
  },
};

/**
 * 获取当前路由下的按钮权限
 * @param currentRouteUrl 当前路由url eg: system/user/index
 * @param permissionTreeList 权限菜单树
 * @param btnPermList 按钮权限
 * @returns 按钮权限
 */
function getBtnPermList(currentRouteUrl, permissionTreeList, btnPermList) {
  if (permissionTreeList.length === 0) {
    return btnPermList;
  }
  permissionTreeList.forEach((e) => {
    // 只要有权限就拿到，暂时不判断当前路由下是否具有按钮权限  (e.meta.fullPath === currentRouteUrl || e.meta.fullPath === currentRouteUrl + '/index')
    if (e.type == 2) {
      btnPermList.push(e.btnPerm);
    }
    const childList = e.children;
    if (childList) {
      getBtnPermList(currentRouteUrl, childList, btnPermList);
    } else {
      return btnPermList;
    }
  });
  return btnPermList;
}
