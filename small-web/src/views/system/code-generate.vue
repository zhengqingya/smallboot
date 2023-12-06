<template>
  <base-wrapper class="flex" style="padding: 10px">
    <base-content>
      <el-tree :data="configData.pgList" :props="{ children: 'childList', label: 'name' }" highlight-current default-expand-all :expand-on-click-node="false" @node-click="handleNodeClick" />
    </base-content>

    <base-content class="flex-1" style="margin-left: 10px">
      <base-header>
        <base-input v-model="configData.parentPackageName" label="父包名" clearable />
        <base-input v-model="configData.moduleName" label="模块名" clearable />
        <base-input v-model="configData.tableName" label="生成表名" clearable />
        <el-button type="primary" @click="generateCode">生成代码</el-button>
        <template #right>
          <el-button type="warning" @click="saveConfig">保存模板数据</el-button>
        </template>
      </base-header>

      <base-content v-if="tplData.tplContent">
        <el-scrollbar style="height: 350px">
          <codemirror v-model="tplData.tplContent" basic :extensions="[java(), oneDark]" />
        </el-scrollbar>
      </base-content>
    </base-content>
  </base-wrapper>
</template>
<script setup>
const { proxy } = getCurrentInstance();
import codemirror from 'vue-codemirror6';
import { java } from '@codemirror/lang-java';
import { oneDark } from '@codemirror/theme-one-dark';

let configData = $ref({});
let tplData = $ref({});
// let tplGenerateQuery = $ref({ parentPackageName: 'com.zhengqing', moduleName: 'system' });

onMounted(() => {
  init();
});

async function init() {
  let res = await proxy.$api.sys_code_generate.getConfig();
  configData = res.data;
}

function handleNodeClick(data) {
  tplData = data;
}

async function saveConfig() {
  let res = await proxy.$api.sys_code_generate.saveConfig(configData);
  proxy.submitOk(res.msg);
}

async function generateCode() {
  let res = await proxy.$api.sys_code_generate.generateTplData(configData);
  proxy.submitOk('成功');
}
</script>
<style lang="scss" scoped></style>
