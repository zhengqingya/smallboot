<template>
  <span v-if="label" class="label">{{ label }} &nbsp;</span>
  <!-- :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }" -->
  <el-cascader ref="cascaderRef" filterable v-bind="$attrs" :options="list" :placeholder="label ? `请选择${label}` : '请选择'" @change="handleChange" />
</template>

<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  api: { type: String, required: false, default: '' },
  params: { type: Object, required: false, default: () => {} },
  label: { type: String, default: '' },
});

let list = $ref([]);

onMounted(() => {
  init();
});

async function init() {
  if (props.api) {
    let res = await apiMethod(props.params);
    list = res.data;
  }
}

async function handleChange(value) {
  // proxy.$emit('update:modelValue', value[value.length - 1]);
  // 关闭弹窗
  proxy.$refs.cascaderRef.togglePopperVisible();
}

// 接口请求
function apiMethod(params, headers) {
  // eg: proxy.$api.sms_shop.updateStatus(xx);
  return proxy.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(params, headers);
}
</script>

<style lang="scss" scoped>
.label {
  font-size: var(--el-form-label-font-size);
  color: var(--el-text-color-regular);
}
</style>
