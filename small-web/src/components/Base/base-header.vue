<template>
  <base-content>
    <div class="flex-between-end">
      <div class="flex-1">
        <slot />
      </div>
      <div class="base-header-gap">
        <slot name="right" />
      </div>
    </div>
  </base-content>
</template>
<script setup></script>
<style lang="scss" scoped></style>
