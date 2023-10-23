<template>
  <!--  超管/系统管理员 才有权限看见 -->
  <div v-if="isLogin && userObj.roleCodeList && (userObj.roleCodeList.includes('super_admin') || userObj.roleCodeList.includes('system_admin'))">
    <base-select v-model="tenantId" tag-type="success" :option-props="{ label: 'name', value: 'id' }" :data-list="tenantList" @change="handleChange">
      <template #prefix>
        <el-icon> <OfficeBuilding /> </el-icon>
      </template>
    </base-select>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';

const { proxy } = getCurrentInstance();
let { isLogin, tenantId, userObj } = toRefs(proxy.$store.user.useUserStore());
let tenantList = $ref([]);
onMounted(async () => {
  let res = await proxy.$api.sys_tenant.list();
  tenantList = res.data;
  tenantList.unshift({ id: -1, name: '全部租户' });
});

function handleChange(tenantIdVal) {
  // userObj.value.tenantName = tenantList.find((item) => item.id === tenantIdVal).name;
  location.reload(); // 强制刷新页面
}
</script>
<style lang="scss" scoped></style>
