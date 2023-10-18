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

      <base-card title="数据权限">
        <el-table ref="baseScopeTableRef" row-key="id" border :selection="true" :reserve-selection="true" :data="scopeList">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column label="菜单" align="center" width="300px">
            <template #default="scope">
              <base-cascader v-model="scope.row.menuId" disabled :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }" :data-list="menuTree" />
            </template>
          </el-table-column>
          <el-table-column label="权限名称" width="300px" prop="scopeName" align="center" />
        </el-table>
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
let scopeList = $ref([]); // 数据权限

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
  let selectScopeIdList = scopeRes.data;

  let res = await proxy.$api.sys_scope_data.list();
  scopeList = res.data;
  // 回显选中行
  nextTick(() => {
    scopeList.forEach((item) => {
      if (selectScopeIdList.includes(item.id)) {
        proxy.$refs.baseScopeTableRef.toggleRowSelection(item, true);
      }
    });
  });
}

// 保存角色关联的菜单权限数据
async function savePerm() {
  // 拿到选中的权限id
  let selectScopeIdList = proxy.$refs.baseScopeTableRef.getSelectionRows().map((e) => e.id);
  let selectMenuIdList = proxy.$refs.menuTreeRef.getSelectKeyList();
  let res = await proxy.$api.sys_perm.saveRoleRePerm({ roleId: roleId, menuIdList: selectMenuIdList, scopeIdList: selectScopeIdList });
  proxy.submitOk(res.msg);
  proxy.$router.push('/system/role');
}
</script>
<style lang="scss" scoped></style>
