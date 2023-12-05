<template>
  <div class="app">
    <div style="padding: 10px">
      <slot />
    </div>
  </div>
</template>
<script setup></script>
<style lang="scss" scoped>
.app {
  margin: 15px 10px;
  box-shadow: 1px 1px 3px 1px rgba(0, 0, 0, 0.2);
  border-radius: 5px;
  background-color: white;
  overflow: hidden;
}
</style>
