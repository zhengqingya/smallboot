<template>
  <div style="cursor: pointer; display: inline-block; border-radius: 4px; padding: 0px 10px; opacity: 0.8" @click="handleClick">
    {{ text }}
  </div>
</template>
<script setup>
const { proxy } = getCurrentInstance();
import { Check, Close } from '@element-plus/icons-vue';
const props = defineProps({
  modelValue: { type: null, required: false, default: null },
  // onValue: { type: null, required: false, default: null },
  // 开关样式
  // onStyle: { type: String, required: true, default: '' },
  // offStyle: { type: String, required: true, default: '' },
  // 显示文本
  text: { type: String, required: false, default: '' },
  // 请求
  api: { type: String, required: true, default: '' },
  params: { type: Object, required: true, default: () => {} },
});

async function handleClick() {
  if (props.api) {
    let res = await apiMethod(props.params);
    console.log('111', props.params);
    proxy.$emit('ok');
  }
}
// 接口请求
function apiMethod(params, headers) {
  // eg: proxy.$api.sms_shop.updateStatus(xx);
  return proxy.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(params, headers);
}
</script>
<style lang="scss" scoped></style>
