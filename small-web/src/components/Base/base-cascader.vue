<template>
  <!-- <div style="display: inline-block; white-space: nowrap; margin-right: 10px" :class="{ gap: label }">
    <span v-if="label" class="label font-bold">{{ label }} &nbsp;</span>
    <el-cascader ref="cascaderRef" filterable v-bind="$attrs" :options="list" :placeholder="label ? `请选择${label}` : '请选择'" @change="handleChange" />
  </div> -->

  <!-- :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }" -->
  <el-form-item :style="{ width: isFull ? '100%' : '' }">
    <el-cascader
      show-all-levels
      ref="cascaderRef"
      filterable
      v-bind="$attrs"
      :options="list"
      :placeholder="placeholder ? placeholder : label ? `请选择${label}` : '请选择'"
      :style="{ width: isFull ? '100vh' : '200px', 'margin-left': '0px' }"
      @change="handleChange"
      @clear="handleClear" />
  </el-form-item>
</template>

<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  api: { type: String, required: false, default: '' },
  params: { type: Object, required: false, default: () => {} },
  placeholder: { type: String, default: '' },
  label: { type: String, default: '' },
  isShowLabel: { type: Boolean, default: true },
  dataList: { type: Array, default: () => [] },
  isFull: { type: Boolean, default: false },
});

let list = $ref([]);

watch(
  () => props.dataList,
  (newValue) => {
    // console.log('监听器执行了... ', newValue);
    list = newValue;
  },
  { deep: true },
);

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

async function handleChange(value) {
  // proxy.$emit('update:modelValue', value[value.length - 1]);

  proxy.$refs.cascaderRef.togglePopperVisible(); // 关闭弹窗
  proxy.$emit('update');
}

async function handleClear() {
  proxy.$emit('update:modelValue', null);
  proxy.$refs.cascaderRef.togglePopperVisible(); // 关闭弹窗
  proxy.$emit('update');
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
