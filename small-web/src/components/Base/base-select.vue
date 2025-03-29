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

      <!-- 自定义标签 -->
      <template #label="{ label, value }">
        <slot name="label" :label="label" :value="value">
          <span>{{ label }}</span>
        </slot>
      </template>

      <!-- 选择值 -->
      <!-- :label="item" 这里可以直接传整个值，上面自定义标签的时候可以拿到整个item做自定义数据展示 -->
      <el-option v-for="item in list" :key="item[optionProps.value]" :label="item[optionProps.label]" :value="valueType == 'string' ? String(item[optionProps.value]) : item[optionProps.value]">
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
import { useAttrs } from 'vue';
const attrs = useAttrs(); // 获取所有传递的属性（包括未在 props 中声明的）
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
  valueType: { type: String, default: null }, // 'string' | 'long' 默认根据类型判断回显
  // 是否过滤无效值 -- tips: 假设有2个选择框A、B，B依赖A，这个配置暂无法过滤选项框B的无效值，因为B框的值可能没有刷新就触发过滤，就有可能过滤错误。
  isFilterInvalidValue: { type: Boolean, default: true },
});

let modelValueLocal = $ref([]);
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
  } else if (props.dataList) {
    list = props.dataList;
  }
  filterModelValue();
}

watch(
  () => attrs.modelValue,
  (newValue) => {
    // console.log('监听器执行了... ', newValue);
    if (list.length > 0) {
      // 在数据初始化后执行
      filterModelValue();
    }
  },
  {
    deep: true,
    immediate: true, // 初始化执行一次
  },
);

// 过滤无效数据值 -- 比如下拉框数据已被删除，无法正常回显...
function filterModelValue() {
  if (true) {
    return; // 临时关闭
  }
  if (!list || list.length == 0) {
    return;
  }
  let modelValueLocal = attrs.modelValue;
  if (!props.isFilterInvalidValue || !modelValueLocal) {
    // console.log('无数据...', modelValueLocal);
    return;
  }
  // 判断 multiple 是否存在
  // let hasMultiple = Object.prototype.hasOwnProperty.call(attrs, 'multiple');
  // 处理实际值（兼容字符串和布尔类型）
  let isMultipleEnabled = attrs.multiple !== undefined && attrs.multiple !== false;

  // console.log('是否多选：', isMultipleEnabled, '列表数据：', list, '数据值:', modelValueLocal);
  if (isMultipleEnabled) {
    // 多选
    modelValueLocal = filterIdList(list, isMultipleEnabled ? modelValueLocal : [modelValueLocal]);
  } else {
    // 单选
    modelValueLocal = list.find((item) => item?.[props.optionProps.value] == modelValueLocal)?.[props.optionProps.value];
  }
  proxy.$emit('update:modelValue', modelValueLocal);
  // console.log(isMultipleEnabled, '原数据值：', attrs.modelValue, '过滤无效数据后的值：', modelValueLocal, list);
}

function filterIdList(list, idList) {
  if (!list?.length || !idList?.length) return [];
  // 统一转换为字符串避免类型问题
  // console.log('xxx:', list[0]?.[props.optionProps.value]?.toString());
  const listIdSet = new Set(list.map((item) => item?.[props.optionProps.value]?.toString()));
  return idList.filter((id) => listIdSet.has(id?.toString()));
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
