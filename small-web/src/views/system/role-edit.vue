<template>
  <base-wrapper>
    <base-card title="角色信息">
      <base-cell label-width="120px">
        <base-cell-item label="角色名">{{ roleForm.name }}</base-cell-item>
        <base-cell-item label="角色编码">{{ roleForm.code }}</base-cell-item>
      </base-cell>
      <template #append> <el-button type="success" @click="initData">刷新</el-button> </template>
    </base-card>

    <div class="flex" style="margin-top: 10px">
      <base-card title="菜单&按钮 权限">
        <menu-perm-tree v-show="menuTree.length > 0" ref="menuTreeRef" v-model="menuTree" :role-id="roleId" />
      </base-card>

      <base-card title="数据权限" class="flex-1" style="margin-left: 10px">
        <el-tree
          ref="scopeTreeRef"
          :data="scopeTree"
          :props="{
            children: 'children',
            label: 'customName',
          }"
          show-checkbox
          default-expand-all
          :default-checked-keys="defaultSelectScopeIdList"
          node-key="customId"
          highlight-current />
      </base-card>
    </div>
  </base-wrapper>

  <div class="opt-bottom-container">
    <div class="opt-floating-content">
      <div class="flex-center-center">
        <router-link to="/system/role">
          <el-button>返回</el-button>
        </router-link>
        <el-button type="primary" style="margin-left: 20px" @click="savePerm">保存</el-button>
      </div>
    </div>
  </div>
</template>
<script setup>
import { onMounted } from 'vue';

const { proxy } = getCurrentInstance();
let roleId = $ref(null);
let roleForm = $ref({}); // 角色基本信息
let menuTree = $ref([]); // 菜单树
let scopeTree = $ref([]); // 数据权限
let defaultSelectScopeIdList = $ref([]); //  数据权限树默认选中的数据

onMounted(() => {
  roleId = proxy.$route.query.id;
  if (!roleId) {
    proxy.$router.push('/system/role');
    return;
  }
  initData();
});

async function initData() {
  let res = await proxy.$api.sys_role.detail(roleId);
  roleForm = res.data;
  menuTree = res.data.menuTree;

  initScope();
}

async function initScope() {
  let scopeRes = await proxy.$api.sys_perm.getScopeIdListByRoleId({ roleId: roleId });
  defaultSelectScopeIdList = scopeRes.data;

  let res = await proxy.$api.sys_scope_data.tree();
  scopeTree = res.data;
}

// 保存角色关联的菜单权限数据
async function savePerm() {
  // 拿到选中的权限id
  let selectScopeIdList = proxy.$refs.scopeTreeRef.getCheckedKeys().filter((e) => isFinite(e));
  let selectMenuIdList = proxy.$refs.menuTreeRef.getCheckedKeys();
  let res = await proxy.$api.sys_perm.saveRoleRePerm({ roleId: roleId, menuIdList: selectMenuIdList, scopeIdList: selectScopeIdList });
  proxy.submitOk(res.msg);

  proxy.$router.push('/system/role');

  // 本地方便测试...，直接刷新数据
  // await initData();
  // proxy.$refs.menuTreeRef.update();
}
</script>
<style lang="scss" scoped>
.opt-bottom-container {
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: flex-end; /* 垂直底部对齐 */
  .opt-floating-content {
    position: fixed; /* 固定在视口中 */
    bottom: 20px; /* 距离底部的距离 */
    left: 50%; /* 水平居中 */
    transform: translateX(-50%); /* 偏移自身宽度的一半 */
    background-color: #fff;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    z-index: 1000; /* 确保内容在其他元素之上 */
  }
}
</style>
