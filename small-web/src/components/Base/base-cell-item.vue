<template>
  <div class="app flex">
    <div class="flex-end-center bg-color-lightgrey" style="padding: 10px 0px" :style="{ width: computedLabelWidth }">
      <span style="margin-right: 10px">{{ label }}</span>
    </div>
    <div class="flex-1 flex-start-center" style="overflow: hidden; margin: 0px 10px">
      <slot />
    </div>
  </div>
</template>
<script setup>
const props = defineProps({
  label: {
    type: String,
    default: '',
  },
  labelWidth: {
    type: String,
    default: '',
  },
});

const baseCellLabelWidth = inject('baseCellLabelWidth', '200px');

const computedLabelWidth = computed(() => {
  return props.labelWidth || baseCellLabelWidth;
});
</script>
<style lang="scss" scoped>
.app {
  border: 1px solid rgb(230, 233, 240);
  &:not(:first-child) {
    border-top: none;
  }
}
</style>
