<template>
  <div style="margin: 15px 0px">
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
  </div>
</template>
<script setup></script>
<style lang="scss" scoped></style>
