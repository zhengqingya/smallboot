<template>
  <span v-if="label" class="label">{{ label }} &nbsp;</span>
  <el-select filterable v-bind="$attrs" :placeholder="label ? `请选择${label}` : '请选择'">
    <template #prefix> <slot name="prefix" /></template>
    <el-option v-for="item in list" :key="item[optionProps.value]" :label="item[optionProps.label]" :value="item[optionProps.value]" />
  </el-select>
</template>

<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  api: { type: String, required: false, default: '' },
  params: { type: Object, required: false, default: () => {} },
  // :option-props="{ label: 'name', value: 'id' }"
  optionProps: { type: Object, default: () => {} },
  dataList: { type: Array, default: () => [] },
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
    return;
  }
  if (props.dataList) {
    list = props.dataList;
  }
}

// 接口请求
function apiMethod(params, headers) {
  // eg: proxy.$api.sms_shop.updateStatus(xx);
  return proxy.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(params, headers);
}
</script>

<style lang="scss" scoped></style>
