<template>
  <div style="border: 1px solid #ccc; width: 100%">
    <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :default-config="toolbarConfig" :mode="mode" />
    <Editor v-bind="$attrs" style="height: 310px; width: 100%; overflow-y: hidden" :default-config="editorConfig" :mode="mode" @on-created="handleCreated" />
  </div>
</template>

<script setup>
const { proxy } = getCurrentInstance();
import '@wangeditor/editor/dist/css/style.css'; // 引入 css
import { onBeforeUnmount, ref, shallowRef, onMounted } from 'vue';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';

// 编辑器实例，必须用 shallowRef
const editorRef = shallowRef();

let mode = ref('default'); // 'default' 或 'simple'

const toolbarConfig = {};
const editorConfig = { placeholder: '请输入内容...', MENU_CONF: {} };

// 图片上传设置 https://blog.csdn.net/SunFlower914/article/details/130349126
editorConfig.MENU_CONF['uploadImage'] = {
  maxNumberOfFiles: 1,
  allowedFileTypes: ['image/*'],
  async customUpload(file, insertFn) {
    if (!file.type.includes('image')) {
      return proxy.submitFail('请上传图片格式的文件');
    }
    if (file.size / 1024 / 1024 > 10) {
      return proxy.submitFail('请上传10M以内的图片');
    }
    const formData = new FormData();
    formData.append('file', file);

    let res = await proxy.$api.sys_file.upload(formData);
    insertFn(res.data.url);
  },
};

onMounted(() => {});

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  console.log(11, editor);
  if (editor == null) return;
  editor.destroy();
});

// 编辑器回调函数
const handleCreated = (editor) => {
  // let list = editor.children;
  // if (list.length > 0) {
  //   list.forEach((e) => {
  //     // 配置默认字体
  //     e.children[0]['fontFamily'] = '微软雅黑';
  //     e.children[0]['fontSize'] = '18px';
  //     console.log(e);
  //   });
  // }
  // console.log(22, editor.children);
  editorRef.value = editor; // 记录 editor 实例，重要！
};
</script>
<style lang="scss" scoped></style>
