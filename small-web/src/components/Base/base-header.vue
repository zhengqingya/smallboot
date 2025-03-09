<template>
  <div class="app">
    <el-form label-position="left" inline>
      <div class="flex-start-end">
        <div class="flex-1">
          <slot />
            <el-form-item label-position="left" inline>
              <slot name="left" />
            </el-form-item>
        </div>
        <div class="base-header-gap" >
          <el-form-item label-position="left" inline>
            <slot name="right" />
          </el-form-item>
        </div>
      </div>
    </el-form> 
  </div>
</template>
<script setup></script>
<style lang="scss" scoped>
.app {
  // padding: 12px 10px;
  // box-shadow: 1px 1px 3px 1px rgba(0, 0, 0, 0.1);
  // border-radius: 5px;
  margin-bottom: 12px;
}

::v-deep(.el-form-item) { 
  margin: 5px 5px;
}

// 头部-间隔
.base-header-gap {
  // margin: 6px 0px;
}
</style>
