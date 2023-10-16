<template>
  <!-- {{ list[0] }} -->
  <span v-if="label" class="label">{{ label }} &nbsp;</span>
  <el-select filterable v-bind="$attrs" :placeholder="label ? `请选择${label}` : '请选择'" @change="handleChange">
    <template #prefix> <slot name="prefix" /></template>
    <el-option v-for="item in list" :key="item[optionProps.value]" :label="item[optionProps.label]" :value="item[optionProps.value]" />
  </el-select>
</template>

<script setup>
import { onUpdated } from 'vue';

const { proxy } = getCurrentInstance();
const props = defineProps({
  api: { type: String, required: false, default: '' },
  params: { type: Object, required: false, default: () => {} },
  // :option-props="{ label: 'name', value: 'id' }"
  optionProps: { type: Object, default: () => {} },
  dataList: { type: Array, default: () => [] },
  label: { type: String, default: '' },
  isDefaultValue: { type: Boolean, default: false }, // true:默认选中第一个值
});

let list = $ref([]);

onMounted(() => {
  init();
});

// onUpdated(() => {
//   init();
// });

async function init() {
  console.log('111', props.api);
  if (props.api) {
    let res = await apiMethod(props.params);
    list = res.data;
    if (list.length > 0 && props.isDefaultValue) {
      proxy.$emit('update:modelValue', list[0][props.optionProps.value]);
    }
    return;
  }
  if (props.dataList) {
    list = props.dataList;
  }
}

function handleChange(val) {
  // console.log('111', val);
}

// 接口请求
function apiMethod(params, headers) {
  // eg: proxy.$api.sms_shop.updateStatus(xx);
  return proxy.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(params, headers);
}
</script>

<style lang="scss" scoped></style>
