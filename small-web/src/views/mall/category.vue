<template>
  <base-wrapper>
    <div class="flex h-full">
      <div style="width: 680px">
        <base-card title="菜单" style="height: 100%">
          <base-header>
            <base-input v-model="listQuery.name" label="名称" clearable @clear="refreshTableData"></base-input>
            <el-button type="primary" @click="refreshTableData">查询</el-button>
            <template #right>
              <el-button type="primary" @click="handleAdd(0)">添加</el-button>
            </template>
          </base-header>

          <base-content>
            <base-table-p ref="baseTableRef" api="pms_category.list" :params="listQuery" :is-page="false" @cell-click="tableNodeclick">
              <!-- <el-table-column label="ID" prop="id" align="center" width="160px"></el-table-column> -->
              <!-- <el-table-column label="父分类id" prop="parentId" align="center" width="160px"></el-table-column> -->
              <el-table-column label="名称" prop="name" align="center"></el-table-column>
              <el-table-column label="排序" prop="sort" align="center"></el-table-column>
              <el-table-column label="显示" prop="isShow" align="center">
                <template #default="scope">
                  {{ scope.row.isShow ? '是' : '否' }}
                </template>
              </el-table-column>
              <el-table-column align="center" label="操作">
                <template #default="scope">
                  <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
                  <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
                </template>
              </el-table-column>
            </base-table-p>
          </base-content>
        </base-card>
      </div>

      <base-card :title="selectParentName" style="height: 100%; width: 100%; margin-left: 10px" v-if="selectParentId">
        <template #append>
          <el-button type="primary" @click="handleAdd(selectParentId)">添加</el-button>
        </template>

        <base-content>
          <base-table-p :data="childList" :is-page="false">
            <!-- <el-table-column label="ID" prop="id" align="center" width="160px"></el-table-column> -->
            <!-- <el-table-column label="父分类id" prop="parentId" align="center" width="160px"></el-table-column> -->
            <el-table-column label="名称" prop="name" align="center"></el-table-column>
            <el-table-column label="排序" prop="sort" align="center"></el-table-column>
            <el-table-column label="显示" prop="isShow" align="center">
              <template #default="scope">
                {{ scope.row.isShow ? '是' : '否' }}
              </template>
            </el-table-column>
            <el-table-column align="center" label="操作">
              <template #default="scope">
                <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
                <base-delete-btn @ok="handleDelete(scope.row)"></base-delete-btn>
              </template>
            </el-table-column>
          </base-table-p>
        </base-content>
      </base-card>
    </div>

    <base-dialog v-model="dialogVisible" :title="dialogTitleObj[dialogStatus]" width="500px">
      <el-form v-if="dialogStatus !== 'detail'" ref="dataFormRef" :model="form" label-width="100px">
        <el-form-item label="父分类:" v-if="form.parentId != 0">
          <el-input v-model="selectParentName" :disabled="true" />
        </el-form-item>
        <el-form-item label="名称:" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="排序:" prop="sort">
          <el-input-number v-model="form.sort" :min="1" controls-position="right" placeholder="请输入排序" />
        </el-form-item>
        <el-form-item label="显示:" prop="isShow">
          <el-radio-group v-model="form.isShow">
            <el-radio :label="true">是</el-radio>
            <el-radio :label="false">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template v-if="dialogStatus !== 'detail'" #footer>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </template>
    </base-dialog>
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let listQuery = $ref({ parentId: 0 });
let form = $ref({});
let dialogVisible = $ref(false);
let dialogStatus = $ref('');

function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleAdd(parentId) {
  form = Object.assign({}, {});
  form.isShow = true;
  form.sort = 1;
  form.parentId = parentId;
  dialogStatus = 'add';
  dialogVisible = true;
}
function handleUpdate(row) {
  form = Object.assign({}, row);
  dialogStatus = 'update';
  dialogVisible = true;
}
async function handleDelete(row) {
  let res = await proxy.$api.pms_category.deleteBatch({ idList: [row.id].join() });
  if (row.parentId == 0) {
    refreshTableData();
    if (row.id == selectParentId) {
      selectParentId = null;
    }
  } else {
    refreshChildList();
  }
  proxy.submitOk(res.message);
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.pms_category[form.id ? 'update' : 'add'](form);
      proxy.submitOk(res.message);
      dialogVisible = false;
      if (form.parentId == 0) {
        refreshTableData();
      } else {
        refreshChildList();
      }
    }
  });
}

let selectParentId = $ref(null);
let selectParentName = $ref(null);

async function tableNodeclick(row) {
  selectParentId = row.id;
  selectParentName = row.name;
  refreshChildList();
}
let childList = $ref([]);
async function refreshChildList() {
  let res = await proxy.$api.pms_category.list({ parentId: selectParentId });
  childList = res.data;
}
</script>
<style scoped></style>
