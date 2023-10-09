<template>
  <!-- :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true }" -->
  <el-cascader v-bind="$attrs" :options="list" placeholder="请选择" @change="handleChange" />
</template>

<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  api: { type: String, required: false, default: '' },
  params: { type: Object, required: false, default: () => {} },
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
  proxy.$emit('update:modelValue', value[value.length - 1]);
}

// 接口请求
function apiMethod(params, headers) {
  // eg: proxy.$api.sms_shop.updateStatus(xx);
  return proxy.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(params, headers);
}
</script>

<style lang="scss" scoped></style>
