<template>
  <base-wrapper>
    <base-card title="角色信息">
      <base-cell label-width="100px">
        <base-cell-item label="角色名：">{{ roleForm.name }}</base-cell-item>
        <base-cell-item label="角色编码：">{{ roleForm.code }}</base-cell-item>
      </base-cell>
    </base-card>

    <base-card title="权限信息" class="m-t-10">
      <menu-perm-tree v-show="menuTree.length > 0" v-model="menuTree" :role-id="roleId" />
    </base-card>

    <div class="m-t-10">
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
}

// 保存角色关联的所有权限数据
async function savePerm() {
  let res = await proxy.$api.sys_perm.saveRoleRePerm({
    roleId: roleId,
    menuTree: menuTree,
  });
  proxy.submitOk(res.msg);
  proxy.$router.push('/system/role');
}
</script>
<style lang="scss" scoped></style>
