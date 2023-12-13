<template>
  <base-wrapper>
    <base-card title="基本信息">
      <template #append>
        <el-button type="warning" @click="submitForm">保存</el-button>
      </template>
      <base-input v-model="dataForm.name" label="名称" />
      <base-input v-model="dataForm.key" label="模型Key" />
    </base-card>

    <base-card title="流程内容">
      <div class="flex">
        <!-- 流程设计器，负责绘制流程等 -->
        <div>
          <MyProcessDesigner
            key="designer"
            v-model="xmlString"
            :value="xmlString"
            v-bind="controlForm"
            keyboard
            ref="processDesigner"
            @init-finished="initModeler"
            :additionalModel="controlForm.additionalModel"
            @save="save" />
        </div>
        <!-- 流程属性器，负责编辑每个流程节点的属性 -->
        <div><MyProcessPenal key="penal" :bpmnModeler="modeler as any" :prefix="controlForm.prefix" class="process-panel" :model="model" /></div>
      </div>
    </base-card>
  </base-wrapper>
</template>

<script setup lang="ts">
import { onMounted, getCurrentInstance, ref } from 'vue';
const { proxy } = getCurrentInstance();
let id = $ref();
let dataForm = $ref({});
let designerData = $ref({});
let designerOpen = $ref(false);

onMounted(() => {
  id = proxy.$route.query.id;
  dataForm = {};
  if (id) {
    init();
  }
});

import { MyProcessDesigner, MyProcessPenal } from './component/bpmnProcessDesigner/package';
// 自定义元素选中时的弹出菜单（修改 默认任务 为 用户任务）
import CustomContentPadProvider from './component/bpmnProcessDesigner/package/designer/plugins/content-pad';
// 自定义左侧菜单（修改 默认任务 为 用户任务）
import CustomPaletteProvider from './component/bpmnProcessDesigner/package/designer/plugins/palette';
const router = useRouter(); // 路由
const { query } = useRoute(); // 路由的查询
// const message = useMessage(); // 国际化

const xmlString = ref(undefined); // BPMN XML
const modeler = ref(null); // BPMN Modeler
const controlForm = ref({
  simulation: true,
  labelEditing: false,
  labelVisible: false,
  prefix: 'flowable',
  headerButtonSize: 'mini',
  additionalModel: [CustomContentPadProvider, CustomPaletteProvider],
});
const model = ref({}); // 流程模型的信息
/** 初始化 modeler */
const initModeler = (item) => {
  setTimeout(() => {
    modeler.value = item;
  }, 10);
};
/** 添加/修改模型 */
const save = async (bpmnXml) => {
  const data = {
    ...model.value,
    bpmnXml: bpmnXml, // bpmnXml 只是初始化流程图，后续修改无法通过它获得
  };
  // 提交
  if (data.id) {
    // await ModelApi.updateModel(data);
    // message.success('修改成功');
  } else {
    // await ModelApi.createModel(data);
    // message.success('新增成功');
  }
  // 跳转回去
  // close();
};

async function init() {
  let res = await proxy.$api.wf_model.detail({ id: id });
  dataForm = res.data;
}

async function submitForm() {
  let res = await proxy.$api.wf_model[dataForm.id ? 'update' : 'add'](dataForm);
  proxy.submitOk(res.message);
  // proxy.$router.push('/workflow/model');
}
</script>

<style lang="scss" scoped></style>
