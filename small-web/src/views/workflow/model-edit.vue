<template>
  <base-wrapper>
    <base-card title="基本信息">
      <template #append>
        <el-button type="warning" @click="submitForm">保存</el-button>
      </template>
      <base-input v-model="dataForm.name" label="名称" />
      <base-input v-model="dataForm.key" label="模型Key" />
    </base-card>

    <base-card title="流程内容"> 模型 </base-card>
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let id = $ref();
let dataForm = $ref({});

onMounted(() => {
  id = proxy.$route.query.id;
  dataForm = {};
  if (id) {
    init();
  }
});

async function init() {
  let res = await proxy.$api.wf_model.detail({ id: id });
  dataForm = res.data;
}

async function submitForm() {
  let res = await proxy.$api.wf_model[dataForm.id ? 'update' : 'add'](dataForm);
  proxy.submitOk(res.message);
  // proxy.$router.push('/workflow/model');
}
</script>

<style lang="scss" scoped></style>
