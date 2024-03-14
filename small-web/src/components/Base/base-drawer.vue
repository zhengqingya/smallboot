<template>
  <el-drawer :size="width" center :close-on-click-modal="false" direction="rtl" v-bind="$attrs" :with-header="false" style="border-radius: 6px">
    <div class="flex-c-between-start h-full">
      <div class="flex-between-center" style="height: 60px; background: #f5f6f8; width: 100%; padding: 0px 24px">
        <span style="font-weight: 600; font-size: 18px">{{ title }}</span>
        <el-icon size="24" color="#272636" style="opacity: 0.5" @click="handleColse"><CircleClose /></el-icon>
      </div>

      <el-scrollbar style="width: 100%">
        <div style="padding: 16px 24px">
          <slot />
        </div>
      </el-scrollbar>

      <div class="flex-center-center w-full" style="border-top: 1px solid #e6e9f4; padding: 16px 0px">
        <slot name="footer" />
      </div>
    </div>
  </el-drawer>
</template>
<script setup>
const { proxy } = getCurrentInstance();

const props = defineProps({
  title: { type: String, default: '' },
  width: { type: String, default: '' },
});

function handleColse() {
  proxy.$emit('update:modelValue', false);
}
</script>
<style lang="scss" scoped></style>
