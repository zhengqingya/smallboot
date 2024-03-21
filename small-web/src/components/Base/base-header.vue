<template>
  <div class="app">
    <div class="flex-start-center">
      <div class="flex-1">
        <el-form label-position="left" inline>
          <slot />
        </el-form>
      </div>
      <div class="gap">
        <slot name="right" />
      </div>
    </div>
  </div>
</template>
<script setup></script>
<style lang="scss" scoped>
.app {
  padding: 12px 10px;
  box-shadow: 1px 1px 3px 1px rgba(0, 0, 0, 0.1);
  border-radius: 5px;
  margin-bottom: 12px;
}

::v-deep(.el-form-item) {
  margin-bottom: 0px;
}
</style>
