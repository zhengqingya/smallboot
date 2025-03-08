# vue3 + vue-clipboard3 实现复制文本到剪切板

```shell
cnpm install vue-clipboard3
```

```
<div @click="copyMiniUrl(scope.row)">复制链接</div>
```

```
//一键复制
import useClipboard from 'vue-clipboard3';
const { toClipboard } = useClipboard();
async function copyMiniUrl(row) {
  try {
    let url = `pages/index/index?userId=${row.userId}`;
    await toClipboard(url);
    proxy.submitOk('复制成功！');
  } catch (e) {
    console.error(e);
    proxy.submitOk('复制失败！您的浏览器不支持复制功能！');
  }
}
```

---

### 封装到 mixin.js

页面使用eg：`<el-button @click="doCopyUrl(`pages/index/job?id=${scope.row.id}`)">复制链接</el-button>`

`mixin.js`

```js
import { ElMessage, ElMessageBox } from 'element-plus';

//一键复制
import useClipboard from 'vue-clipboard3';
const { toClipboard } = useClipboard();

// 抽取公用的实例 - 操作成功与失败消息提醒内容等
export default {
  data() {
    return { };
  },
  methods: {
    // 操作成功消息提醒内容
    submitOk(msg, cb) {
      this.$notify({
        title: '成功',
        message: msg || '操作成功！',
        type: 'success',
        duration: 2000,
        onClose: function () {
          cb && cb();
        },
      });
    },
    // 操作失败消息提醒内容
    submitFail(msg, cb) {
      this.$message({
        message: msg || '网络异常，请稍后重试！',
        type: 'error',
        onClose: function () {
          cb && cb();
        },
      });
    },
    // 封装复制方法
    async doCopyUrl(url) {
      try {
        console.log('111:', url);
        await toClipboard(url);
        this.submitOk('复制成功！');
      } catch (e) {
        console.error(e);
        this.submitFail('复制失败！您的浏览器不支持复制功能！');
      }
    },
  },
};
```