# el-upload达到上传数量后隐藏上传框

效果：
![](./images/10-el-upload达到上传数量后隐藏上传框-1709259707990.png)
变为
![](./images/10-el-upload达到上传数量后隐藏上传框-1709259719182.png)

代码：

```
<el-upload
      ...
      multiple
      :limit="limit"
      :class="{ 'hide-upload': limit && fileList.length >= limit }">
      <el-icon><Plus /></el-icon>
</el-upload>
```

```
<style lang="scss" scoped>
// 隐藏上传按钮
::v-deep.hide-upload {
  .el-upload--picture-card {
    display: none;
  }
}
</style>
```
