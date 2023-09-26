<template>
  <div>
    <el-switch
      :style="{
        '--el-switch-on-color': onColor,
        '--el-switch-off-color': offColor,
      }"
      v-bind="$attrs"
      inline-prompt
      :active-icon="Check"
      :inactive-icon="Close"
      @change="handleChange" />
  </div>
</template>
<script setup>
const { proxy } = getCurrentInstance();
import { Check, Close } from '@element-plus/icons-vue';
const props = defineProps({
  // 开关颜色 style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
  onColor: { type: String, required: false, default: '' },
  offColor: { type: String, required: false, default: '' },
  api: { type: String, required: false, default: '' },
  params: { type: Object, required: false, default: () => {} },
});

async function handleChange() {
  if (props.api) {
    let res = await apiMethod(props.params);
    console.log('111', res);
  }
}
// 接口请求
function apiMethod(params, headers) {
  // eg: proxy.$api.sms_shop.updateStatus(xx);
  return proxy.api.split('.').reduce((acc, item) => acc[item], proxy.$api)(params, headers);
}
</script>
<style lang="scss" scoped></style>
