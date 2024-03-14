<template>
  <el-button v-bind="$attrs" link style="color: #1e5eff" @click="dialogVisible = true">
    <slot />
  </el-button>

  <base-dialog v-model="dialogVisible" append-to-body title="系统提示" width="360px">
    <div class="flex-center-center">
      <span style="font-size: 16px">{{ tips }}</span>
    </div>
    <template #footer>
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="handleOk">确 定</el-button>
    </template>
  </base-dialog>
</template>
<script setup>
const { proxy } = getCurrentInstance();
const props = defineProps({
  label: { type: String, default: '' },
  tips: { type: String, default: '您确定此操作吗？' },
});
let dialogVisible = $ref(false);

const emits = defineEmits(['ok']);
function handleOk() {
  dialogVisible = false;
  emits('ok');
}
</script>
<style lang="scss" scoped></style>
