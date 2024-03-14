<template>
  <el-tabs v-bind="$attrs" @tab-click="handleClick" @tab-change="handleChange">
    <el-tab-pane v-for="item in list" :key="item" :label="item.label" :name="item.value">
      <slot />
    </el-tab-pane>
  </el-tabs>
</template>
<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  list: { type: Array, default: () => [] },
});

// let activeValue = $ref(null);
// onMounted(async () => {
//   if (props.list.length > 0) {
//     activeValue = props.list[0].value;
//   }
// });

function handleClick(tab, event) {
  // console.log(111, tab.props.label);
}
function handleChange(tabValue) {
  // console.log(111, tabValue);
  proxy.$emit('change', tabValue);
}
</script>
<style lang="scss" scoped>
::v-deep(.el-tabs__item) {
  color: #333333;
  font-size: 16px;
  line-height: 22px;
  font-weight: 400;

  &.is-active {
    color: #333333;
    font-weight: 600;
  }
}
</style>
