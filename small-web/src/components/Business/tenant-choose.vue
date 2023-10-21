<template>
  <!--  超管/系统管理员 才有权限看见 -->
  <div v-if="isLogin && (userObj.roleCodeList.includes('super_admin') || userObj.roleCodeList.includes('system_admin'))">
    <base-select v-model="tenantId" tag-type="success" :option-props="{ label: 'name', value: 'id' }" api="sys_tenant.list">
      <template #prefix>
        <el-icon> <OfficeBuilding /> </el-icon>
      </template>
    </base-select>
  </div>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let { tenantId, isLogin, userObj } = toRefs(proxy.$store.user.useUserStore());
</script>
<style lang="scss" scoped></style>
