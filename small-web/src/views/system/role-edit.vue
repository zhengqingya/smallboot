<template>
  <base-wrapper>
    <base-card title="角色信息">
      <base-cell label-width="100px">
        <base-cell-item label="角色名：">{{ roleForm.name }}</base-cell-item>
        <base-cell-item label="角色编码：">{{ roleForm.code }}</base-cell-item>
      </base-cell>
      <template #append> <el-button type="success" @click="initData">刷新</el-button> </template>
    </base-card>

    <div class="flex m-t-10">
      <base-card title="菜单&按钮 权限" style="width: 40%">
        <menu-perm-tree v-show="menuTree.length > 0" ref="menuTreeRef" v-model="menuTree" :role-id="roleId" />
      </base-card>

      <base-card title="数据权限" class="flex-1">
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

    <div class="flex-center-center m-t-10">
      <router-link to="/system/role">
        <el-button>返回</el-button>
      </router-link>
      <el-button type="primary" class="m-l-20" @click="savePerm">保存</el-button>
    </div>
  </base-wrapper>
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
}
</script>
<style lang="scss" scoped></style>
