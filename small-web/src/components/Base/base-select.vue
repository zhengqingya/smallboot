<template>
  <!-- {{ list[0] }} -->
  <el-form-item :label="label">
    <el-select
      filterable
      v-bind="$attrs"
      style="min-width: 200px"
      :placeholder="placeholder ? placeholder : label ? `请选择${label}` : list.length == 0 && isCustomNull ? isCustomNullTips : '请选择'"
      @change="handleChange">
      <template #prefix> <slot name="prefix" /></template>

      <el-option v-for="item in list" :key="item[optionProps.value]" :label="item[optionProps.label]" :value="item[optionProps.value]">
        <slot name="option" :data="item" />
      </el-option>
      <div v-if="list.length == 0 && isCustomNull">
        <el-option> <slot name="custom-null" /> </el-option>
      </div>
    </el-select>
  </el-form-item>
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
  placeholder: { type: String, default: null },
  isDefaultValue: { type: Boolean, default: false }, // true:默认选中第一个值
  isCustomNull: { type: Boolean, default: false },
  isCustomNullTips: { type: String, default: '暂无数据' },
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

<style lang="scss" scoped>
.label {
  font-size: var(--el-form-label-font-size);
  color: var(--el-text-color-regular);
}
</style>
