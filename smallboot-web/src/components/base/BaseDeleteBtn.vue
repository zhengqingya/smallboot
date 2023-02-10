<template>
  <el-popover v-model:visible="visible" placement="bottom" :width="200">
    <p>确定删除此条数据吗？</p>
    <div style="text-align: right; margin: 0">
      <el-button link @click="visible = false">取消</el-button>
      <el-button link @click="handleClick">确定</el-button>
    </div>
    <template #reference>
      <el-button link style="margin-left: 5px; color: #f56c6c" @click="visible = true"> 删除 </el-button>
    </template>
  </el-popover>
</template>
<script>
export default {
  data() {
    return {
      visible: false,
    }
  },
  methods: {
    handleClick() {
      this.$emit('ok')
      this.visible = false
    },
  },
}
</script>
<style lang="scss" scoped></style>
