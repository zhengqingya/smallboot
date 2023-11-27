<template>
  <div style="border: 1px solid #ccc; width: 100%">
    <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :default-config="toolbarConfig" :mode="mode" />
    <Editor v-bind="$attrs" style="height: 310px; width: 100%; overflow-y: hidden" :default-config="editorConfig" :mode="mode" @onCreated="handleCreated" />
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

onMounted(() => {});

const toolbarConfig = {};
const editorConfig = { placeholder: '请输入内容...' };

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value;
  console.log(11, editor);
  if (editor == null) return;
  editor.destroy();
});

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
