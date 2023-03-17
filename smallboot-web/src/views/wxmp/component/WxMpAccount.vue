<template>
  <div class="wx-mp-account">
    <div class="box">
      <p>请选择公众号：</p>
      <el-select v-model="appId" placeholder="请选择账号" @change="changeData">
        <el-option v-for="item in list" :key="item.appId" :label="item.name" :value="item.appId" />
      </el-select>
    </div>
  </div>
</template>

<script>
import { localStorage } from '@/utils/storage'
export default {
  name: 'WxMpAccount',
  data() {
    return {
      appId: localStorage.get('appId'),
      list: [],
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    async init() {
      let res = await this.$api.wx_mp_account.list()
      this.list = res.data
      if (this.list.length == 0) {
        alert('请先去添加账号！')
        return
      }
      if (this.appId == null) {
        this.appId = this.list[0].appId
      }
    },
    changeData() {
      localStorage.set('appId', this.appId)
    },
  },
}
</script>
<style lang="scss" scoped>
.wx-mp-account {
  position: relative;

  .box {
    position: absolute;
    right: 10px;
    top: 10px;
    background: #08c0ee8c;
    border-radius: 10%;

    ::v-deep .el-input__inner {
      width: 150px;
      color: red;
      font-size: 20px;
    }
    ::v-deep .el-input__wrapper {
      background-color: #08c0ee8c;
    }
  }
}
</style>
