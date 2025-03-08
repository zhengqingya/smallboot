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