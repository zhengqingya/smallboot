<template>
  <div class="flex-start-center" style="line-height: 25px">
    <div class="flex-end-center" :style="{ width: computedLabelWidth }" style="padding: 0px 2px">
      <span style="display: inline-block; white-space: nowrap">{{ label }}：</span>
    </div>

    <div class="flex-1" style="overflow: hidden">
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
