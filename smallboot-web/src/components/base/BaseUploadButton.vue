<template>
  <el-upload
    v-bind="$attrs"
    class="upload-demo"
    action=""
    :http-request="handleUploadFile"
    :before-upload="beforeUpload"
    :on-progress="uploadProgress"
    :on-success="uploadSuccess"
    :on-error="uploadError"
    :headers="dataToken"
  >
    <slot></slot>
  </el-upload>
</template>
<script>
import useStore from '@/store'
// import { Loading } from 'element-ui';
export default {
  props: {
    api: {
      type: String,
      default: 'uploadUrlApi',
    },
    params: {
      type: Object,
      default() {
        return {}
      },
    },
  },
  data() {
    return {
      // dataToken: { [useStore().user.tokenName]: useStore().user.tokenValue },
      // uploadUrl: process.env.VUE_APP_BASE_API + this.uploadUrlApi,
      // uploadUrl: process.env.VUE_APP_BASE_API + "/api/smallTools/crawler/articleInfo/importData",
      loadingInstance: '',
      responseData: undefined,
    }
  },
  computed: {
    apiMethod() {
      return this.api.split('.').reduce((acc, item) => {
        return acc[item]
      }, this.$api)
    },
  },
  methods: {
    // 上传文件
    async handleUploadFile(param) {
      let form = new FormData()
      form.append('file', param.file)
      for (var key in this.params) {
        form.append(key, this.params[key])
      }
      let res = await this.apiMethod(form)
      this.responseData = res
    },
    uploadSuccess(response, fileInfo, fileInfoList) {
      this.loadingInstance.close()
      this.$emit('success', this.responseData, fileInfo, fileInfoList)
    },
    uploadError() {
      this.loadingInstance.close()
    },
    uploadRemove(file, fileList) {
      this.$emit('change', fileList)
    },
    beforeUpload() {
      // let loadingInstance = Loading.service({
      //   fullscreen: true,
      //   background: 'transparent',
      //   text: '上传中',
      // })
      // this.loadingInstance = loadingInstance
    },
    uploadProgress(event) {},
  },
}
</script>
