<template>
  <base-wrapper>
    <base-card title="基本信息">
      <template #append>
        <el-button type="warning" @click="submitForm">保存</el-button>
      </template>
      <base-input v-model="dataForm.name" label="名称" />
      <base-input v-model="dataForm.remark" label="备注" />
    </base-card>

    <base-card title="表单内容">
      <fc-designer ref="designerRef" height="600px" />
    </base-card>
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let id = $ref();
let dataForm = $ref({});

onMounted(() => {
  id = proxy.$route.query.id;
  init();
});

async function init() {
  let res = await proxy.$api.wf_form.detail({ id: id });
  dataForm = res.data;
  let config = dataForm.config;
  // 回显表单
  proxy.$refs.designerRef.setRule(config.rule);
  proxy.$refs.designerRef.setOption(config.option);
}

async function submitForm() {
  // 获取生成规则
  const FcDesignerRule = proxy.$refs.designerRef.getRule(); // FcDesigner 生成的`JSON`
  const FcDesignerOptions = proxy.$refs.designerRef.getOption(); // FcDesigner 生成的`options`

  dataForm.config = {
    rule: FcDesignerRule,
    option: FcDesignerOptions,
  };

  let res = await proxy.$api.wf_form[dataForm.id ? 'update' : 'add'](dataForm);
  proxy.submitOk(res.message);
}
</script>

<style lang="scss" scoped></style>
