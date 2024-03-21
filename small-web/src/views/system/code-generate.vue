<template>
  <base-wrapper>
    <div class="flex h-full">
      <base-content>
        <el-tree :data="configData.pgList" :props="{ children: 'childList', label: 'name' }" highlight-current default-expand-all :expand-on-click-node="false" @node-click="handleNodeClick" />
      </base-content>

      <base-content class="flex-1" style="margin-left: 10px">
        <base-header>
          <base-input v-model="configData.parentPackageName" label="父包名" clearable />
          <base-input v-model="configData.moduleName" label="模块名" clearable />
          <base-input v-model="configData.tableName" @input="configData.queryColumnList = []" label="生成表名" clearable />

          <base-header-form label="查询字段" style="white-space: normal">
            <el-checkbox-group v-model="configData.queryColumnList">
              <el-checkbox :label="item" v-for="item in configData.dbColumnList" :key="item" />
            </el-checkbox-group>
          </base-header-form>
          <br />
          <template #right>
            <el-button type="warning" @click="saveConfig">保存模板数据</el-button>
            <el-button type="warning" @click="generateCode">生成代码</el-button>
          </template>
        </base-header>

        <base-content v-if="tplData.tplContent">
          <el-scrollbar style="height: 500px">
            <codemirror v-model="tplData.tplContent" basic :extensions="[java(), oneDark]" />
          </el-scrollbar>
        </base-content>
      </base-content>
    </div>
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
  init();
}

async function generateCode() {
  let res = await proxy.$api.sys_code_generate.generateTplData(configData);
  proxy.submitOk('成功');
}
</script>
<style lang="scss" scoped></style>
