<template>
  <base-wrapper class="flex" style="padding: 10px">
    <base-content>
      <el-tree :data="deptTreeData" :props="{ children: 'childList', label: 'name' }" highlight-current default-expand-all :expand-on-click-node="false" @node-click="handleNodeClick" />
    </base-content>

    <base-content class="flex-1" style="margin-left: 10px">
      <div style="height: 100px">hello</div>

      <div style="height: 680px">
        <el-scrollbar>
          <codemirror v-model="tplData.tplContent" basic :extensions="[java(), oneDark]" />
        </el-scrollbar>
      </div>
    </base-content>
  </base-wrapper>
</template>
<script setup>
const { proxy } = getCurrentInstance();
import codemirror from 'vue-codemirror6';
import { java } from '@codemirror/lang-java';
import { oneDark } from '@codemirror/theme-one-dark';

let deptTreeData = $ref([]);
let tplData = $ref({});

onMounted(() => {
  init();
});

async function init() {
  let res = await proxy.$api.sys_code_generate.projectPackageTree();
  deptTreeData = res.data;
}

function handleNodeClick(data) {
  tplData = data;
}
</script>
<style lang="scss" scoped></style>
