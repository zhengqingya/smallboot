<template>
  <span ref="boxRef">
    <el-popover v-bind="$attrs" popper-style="padding:0;display: inline-block;" :visible="visible" placement="bottom" :width="200" trigger="click" @show="show" @hide="hide">
      <template #reference>
        <div @click="visible = true"><slot /></div>
      </template>
      <slot name="content" />
    </el-popover>
  </span>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let visible = $ref(false);

const emits = defineEmits(['ok']);
function handleOk() {
  emits('ok');
  visible = false;
}

function show() {
  document.addEventListener('click', handleClick, false);
}

function hide() {
  document.removeEventListener('click', handleClick, false);
}

function handleClick(e) {
  if (!proxy.$refs.boxRef.contains(e.target)) {
    visible = false;
    hide();
  }
}
</script>
<style lang="scss" scoped></style>
