<template>
  <base-content>
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
  </base-content>
</template>
<script setup></script>
<style lang="scss" scoped>
.app {
  // margin: 10px 0px;
  margin-bottom: 24px;
  // border-radius: 5px;
  // padding: 10px 5px;
}

::v-deep(.el-form-item) {
  margin-bottom: 0px;
}
</style>
