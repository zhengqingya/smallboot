<template>
  <!-- {{ list[0] }} -->
  <el-form-item :style="{ width: isFull ? '100%' : '', 'margin-right': '0px' }">
    <el-select
      filterable
      v-bind="$attrs"
      :style="{ width: isFull ? '100%' : '200px', 'margin-left': '0px' }"
      :placeholder="placeholder ? placeholder : label ? `请选择${label}` : list.length == 0 && isCustomNull ? isCustomNullTips : '请选择'"
      @change="handleChange">
      <template #prefix>
        <slot name="prefix">
          <!-- 默认图标 -->
          <!-- <el-icon> <Check /> </el-icon> -->
        </slot>
      </template>

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
  isFull: { type: Boolean, default: false },
  isPage: { type: Boolean, default: false },
});

let list = $ref([]);

// 暴露方法
defineExpose({ refresh });

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

async function refresh() {
  await init();
}

let pageParams = { pageNum: 1, pageSize: 1000 };
async function init() {
  if (props.api) {
    let res;
    if (props.isPage) {
      res = await apiMethod(props.params, pageParams);
      list = res.data.records;
    } else {
      res = await apiMethod(props.params);
      list = res.data;
    }
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
