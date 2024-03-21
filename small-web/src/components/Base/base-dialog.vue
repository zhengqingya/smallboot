<template>
  <!--  top="0px" -->
  <el-dialog :close-on-click-modal="false" v-bind="$attrs" :show-close="false" align-center>
    <template #header="{ close, titleId, titleClass }">
      <div class="flex-between-center" style="height: 68px; background: #f5f6f8; width: 100%; padding: 0px 24px; border-radius: 6px">
        <span style="font-weight: 600; font-size: 18px">{{ title }}</span>
        <el-icon size="25" color="#272636" style="opacity: 0.5" @click="close"><CircleClose /></el-icon>
      </div>
    </template>

    <el-scrollbar style="width: 100%">
      <div style="padding: 0px 0px">
        <slot />
      </div>
    </el-scrollbar>

    <template #footer>
      <div class="flex-center-center w-full" style="padding: 0px 0px 0px 0px">
        <slot name="footer" />
      </div>
    </template>
  </el-dialog>
</template>
<script setup>
const props = defineProps({
  title: { type: String, default: '' },
});
</script>
<style lang="scss" scoped></style>
