<template>
  <base-wrapper>
    <div class="flex h-full w-full">
      <base-card title="菜单" style="width: 300px; max-width: 300px" isCustomLeft>
        <template #left>
          <div class="flex-start-center">
            <div class="font-bold font-size-base" style="width: 40px">菜单</div>
            <el-select v-model="selectCategoryId" placeholder="请选择菜单" style="width: 100%; margin-left: 10px" @change="clickCategory">
              <template #prefix>
                <el-icon> <Menu /> </el-icon>
              </template>
              <el-option v-for="item in categoryList" :key="item.id" :label="item.name" :value="item.id">
                <slot name="option" :data="item" />
              </el-option>
            </el-select>
          </div>
        </template>
        <base-no-data v-if="selectCategoryChildList.length == 0" />
        <div v-else class="flex-c-center-end">
          <div @click="clickChildCategory(item)" v-for="item in selectCategoryChildList" class="flex-end-center" style="height: 50px; width: 100px; border-radius: 5px; background-color: #fafafc">
            <el-tag :type="item.id === selectCategoryChildId ? 'success' : 'info'" style="width: 100%; height: 100%">{{ item.name }}</el-tag>
          </div>
        </div>
      </base-card>
      <base-card title="关联商品" style="margin-left: 10px; width: 100%" v-show="selectCategoryChildList.length > 0">
        <template #append>
          <el-button type="primary" @click="handleAddSpu">添加</el-button>
        </template>

        <base-content>
          <base-table-p ref="baseTableRef" api="pms_category_spu_relation.page" :isInitData="false" :params="listQuerySpu">
            <!-- <el-table-column label="商品ID" prop="spuId" align="center"></el-table-column> -->
            <el-table-column label="名称" prop="spuName" align="center"></el-table-column>
            <el-table-column label="封面图" prop="spuCoverImg" align="center">
              <template #default="scope">
                <el-image :src="scope.row.spuCoverImg" style="width: 50px; height: 50px" />
              </template>
            </el-table-column>
            <el-table-column label="是否上架" prop="isPut" align="center">
              <template #default="scope">
                <base-switch
                  v-model="scope.row.isPut"
                  api="pms_category_spu_relation.updateBatchPut"
                  :params="{
                    idList: [scope.row.id],
                    isPut: !scope.row.isPut,
                  }" />
              </template>
            </el-table-column>
            <el-table-column label="是否显示" prop="isShow" align="center">
              <template #default="scope">
                <base-switch
                  v-model="scope.row.isShow"
                  api="pms_category_spu_relation.updateBatchShow"
                  :params="{
                    idList: [scope.row.id],
                    isShow: !scope.row.isShow,
                  }" />
              </template>
            </el-table-column>
            <el-table-column label="排序" prop="sort" align="center"></el-table-column>
            <el-table-column align="center" label="操作">
              <template #default="scope">
                <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
                <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
              </template>
            </el-table-column>
          </base-table-p>
        </base-content>

        <base-dialog v-model="dialogVisibleSpu" :title="dialogTitleObj[dialogStatusSpu]" width="400px">
          <el-form ref="dataFormRef" :model="formSpu" label-width="100px">
            <el-form-item v-if="dialogStatusSpu === 'add'" label="商品:">
              <el-select v-model="formSpu.spuId" filterable placeholder="选择商品">
                <el-option v-for="item in spuList" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
            <el-form-item v-if="dialogStatusSpu === 'update'" label="商品名称:">
              <el-input v-model="formSpu.spuName" disabled></el-input>
            </el-form-item>
            <el-form-item label="是否上架:" prop="isPut">
              <el-radio-group v-model="formSpu.isPut">
                <el-radio :value="true">是</el-radio>
                <el-radio :value="false">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="是否显示:" prop="isShow">
              <el-radio-group v-model="formSpu.isShow">
                <el-radio :value="true">是</el-radio>
                <el-radio :value="false">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="排序:" prop="sort">
              <el-input-number v-model="formSpu.sort" :min="1" controls-position="right" placeholder="请输入排序" />
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="dialogVisibleSpu = false">取 消</el-button>
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </template>
        </base-dialog>
      </base-card>
    </div>
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let categoryListQuery = $ref({});
let categoryList = $ref([]);
let categoryMap = $ref(new Map());
let selectCategoryId = $ref(null);

onMounted(() => {
  refreshTableDataForCategory();
});

async function refreshTableDataForCategory() {
  let res = await proxy.$api.pms_category.tree(categoryListQuery);
  categoryList = res.data;
  categoryList.forEach((item, index) => {
    if (index == 0) {
      selectCategoryId = item.id;
    }
    categoryMap.set(item.id, item.children);
  });
  if (selectCategoryId) {
    clickCategory(selectCategoryId);
  }
}

let selectCategoryChildList = $ref([]);
function clickCategory(id) {
  selectCategoryChildList = categoryMap.get(id);
  if (selectCategoryChildList.length > 0) {
    clickChildCategory(selectCategoryChildList[0]);
  }
}
let selectCategoryChildId = $ref(null);
function clickChildCategory(item) {
  selectCategoryChildId = item.id;
  listQuerySpu.categoryId = selectCategoryChildId;
  refreshTableDataSpu();
}

// ----------------------- 商品 ----------------------
let listQuerySpu = $ref({});
let formSpu = $ref({});
let dialogVisibleSpu = $ref(false);
let dialogStatusSpu = $ref('');
let spuList = $ref([]);

function refreshTableDataSpu() {
  proxy.$refs.baseTableRef.refresh();
}
function handleAddSpu() {
  getSpuList();
  formSpu = Object.assign({}, {});
  formSpu.isPut = true;
  formSpu.isShow = true;
  formSpu.sort = 1;
  dialogStatusSpu = 'add';
  dialogVisibleSpu = true;
}
function handleUpdate(row) {
  formSpu = Object.assign({}, row);
  dialogStatusSpu = 'update';
  dialogVisibleSpu = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.pms_category_spu_relation.deleteBatch({ idList: [row.id].join() });
  refreshTableDataSpu();
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      formSpu.categoryId = listQuerySpu.categoryId;
      let res = await proxy.$api.pms_category_spu_relation[formSpu.id ? 'update' : 'add'](formSpu);
      proxy.submitOk(res.message);
      refreshTableDataSpu();
      dialogVisibleSpu = false;
    }
  });
}
async function getSpuList() {
  let res = await proxy.$api.pms_spu.list({ notEqCategoryId: listQuerySpu.categoryId });
  spuList = res.data;
}
</script>
<style scoped></style>
