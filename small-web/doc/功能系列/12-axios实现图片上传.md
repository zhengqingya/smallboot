# axios实现图片上传

```shell
cnpm install axios
```

```
import axios from 'axios';
let { tenantId, tokenObj } = toRefs(proxy.$store.user.useUserStore());


const formData = new FormData();
formData.append('file', file); // 封装要上传的文件数据

axios.post(import.meta.env.VITE_APP_BASE_FILE_API, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          [tokenObj.value.tokenName]: tokenObj.value.tokenValue,
          TENANT_ID: tenantId.value,
        },
      })
      .then((res) => {
        console.log(res);
      })
      .catch((error) => {
        console.log(error);
      });
```