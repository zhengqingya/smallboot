<template>
  <base-wrapper>
    <div class="flex h-full">
      <base-card title="组织架构" style="width: 200px">
        <el-tree
          :data="deptTreeData"
          :props="{
            children: 'children',
            label: 'name',
          }"
          highlight-current
          default-expand-all
          :expand-on-click-node="false"
          @node-click="handleNodeClick" />
      </base-card>

      <base-card title="用户管理" class="flex-1" style="margin-left: 10px">
        <base-header>
          <base-cascader
            v-model="listQuery.deptId"
            style="margin-right: 10px"
            clearable
            label="企业"
            :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
            api="sys_dept.tree" />
          <!-- <base-select v-model="listQuery.merchantId" label="商户" tag-type="success" style="margin-right: 10px" clearable :option-props="{ label: 'name', value: 'id' }" api="sys_merchant.list" /> -->
          <!-- <base-select v-model="listQuery.roleIdList" label="角色" tag-type="warning" multiple :option-props="{ label: 'name', value: 'roleId' }" api="sys_role.list" /> -->
          <base-input v-model="listQuery.username" label="账号" @clear="refreshTableData" />
          <base-input v-model="listQuery.nickname" label="昵称" @clear="refreshTableData" />
          <base-input v-model="listQuery.phone" label="手机号" @clear="refreshTableData" />
          <el-button v-has-perm="'sys:user:page'" type="primary" @click="refreshTableData">查询</el-button>
          <template #right>
            <el-button v-has-perm="'sys:user:add'" type="primary" @click="handleCreate">添加</el-button>
          </template>
        </base-header>

        <base-content>
          <base-table-p ref="baseTableRef" api="sys_user.listPage" :params="listQuery">
            <el-table-column :show-overflow-tooltip="true" prop="username" align="center" label="用户账号" />
            <el-table-column prop="nickname" label="用户名称" align="center" />
            <el-table-column prop="sexName" label="性别" align="center" />
            <el-table-column prop="phone" label="手机号码" align="center" />
            <el-table-column prop="email" label="邮箱" align="center" />
            <el-table-column label="角色" :show-overflow-tooltip="true" align="center">
              <template #default="scope">
                <el-tag v-if="scope.row.roleNames"> {{ scope.row.roleNames }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="头像" prop="avatarUrl" align="center">
              <template #default="scope">
                <span>
                  <img :src="scope.row.avatarUrl" alt="" class="img-sm" />
                </span>
              </template>
            </el-table-column>
            <el-table-column :show-overflow-tooltip="true" prop="deptName" align="center" label="归属企业" />
            <el-table-column label="操作" align="center" width="230">
              <template #default="scope">
                <el-button link @click="handleUpdate(scope.row)">编辑</el-button>
                <base-delete-btn v-if="!scope.row.isFixed" @ok="deleteData(scope.row.userId)" />
                <el-button link @click="updatePwd(scope.row)">更新密码</el-button>
              </template>
            </el-table-column>
          </base-table-p>
        </base-content>
      </base-card>
      <base-dialog v-if="dialogStatus === 'updatePwd'" v-model="dialogVisible" :title="titleMap[dialogStatus]" width="30%">
        <el-input v-model="newPassword" placeholder="请输入密码" />
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitPwd()">确定</el-button>
        </template>
      </base-dialog>
      <base-dialog v-else v-model="dialogVisible" :title="titleMap[dialogStatus]" width="50%">
        <el-form ref="dataFormRef" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="账号:" prop="username">
            <el-input v-model="form.username" :disabled="dialogStatus != 'add'" />
          </el-form-item>
          <el-form-item label="密码:" prop="password">
            <el-input v-model="form.password" placeholder="请输入密码" />
          </el-form-item>
          <el-form-item label="昵称:" prop="nickname">
            <el-input v-model="form.nickname" />
          </el-form-item>
          <el-form-item label="性别:" prop="sex">
            <el-select v-model="form.sex" placeholder="请选择">
              <el-option v-for="item in sexList" :key="item.value" :label="item.name" :value="item.value" />
            </el-select>
          </el-form-item>
          <el-form-item label="手机号码:" prop="phone">
            <el-input v-model="form.phone" />
          </el-form-item>
          <el-form-item label="邮箱:" prop="email">
            <el-input v-model="form.email" />
          </el-form-item>
          <el-form-item label="头像:" prop="avatarUrl">
            <base-upload-single v-model="form.avatarUrl" />
          </el-form-item>
          <el-form-item label="归属企业:">
            <base-cascader
              v-if="dialogVisible"
              v-model="form.deptId"
              clearable
              style="width: 100%"
              placeholder="请选择"
              :props="{ value: 'id', label: 'name', children: 'children', checkStrictly: true, emitPath: false }"
              api="sys_dept.tree" />
          </el-form-item>
          <el-form-item label="岗位:" prop="postIdList">
            <base-select v-if="dialogVisible" v-model="form.postIdList" tag-type="success" style="width: 100%" multiple clearable :option-props="{ label: 'name', value: 'id' }" api="sys_post.list" />
          </el-form-item>
          <!-- <el-form-item v-if="dialogStatus == 'add'" label="归属商户:" style="width: 100%">
          <base-select v-if="dialogVisible" v-model="form.merchantId" clearable style="width: 100%" :option-props="{ label: 'name', value: 'id' }" api="sys_merchant.list" />
        </el-form-item> -->
          <el-form-item label="角色:">
            <base-select v-if="dialogVisible" v-model="form.roleIdList" tag-type="warning" multiple style="width: 100%" :option-props="{ label: 'name', value: 'roleId' }" api="sys_role.list" />
          </el-form-item>
        </el-form>
        <template #footer>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </template>
      </base-dialog>
    </div>
  </base-wrapper>
</template>
<script setup>
const { proxy } = getCurrentInstance();
let useUserStore = proxy.$store.user.useUserStore();
let { logout } = useUserStore;
let { userObj } = toRefs(useUserStore);

let dialogVisible = ref(false);
let listQuery = ref({});
let form = ref({});
let dialogStatus = ref('');
let titleMap = ref({
  update: '编辑',
  add: '创建',
  updatePwd: '更新密码',
});
let newPassword = ref('123456');
let rules = ref({
  username: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  password: [{ pattern: /^(\w){6,16}$/, message: '请设置6-16位字母、数字组合' }],
  nickname: [{ required: true, message: '请输入你昵称', trigger: 'blur' }],
});
let deptTreeData = ref([]);

onMounted(() => {
  init();
});

async function init() {
  let res = await proxy.$api.sys_dept.tree();
  deptTreeData.value = res.data;
}
function handleNodeClick(data) {
  listQuery.value.deptId = data.id;
  refreshTableData();
}

async function refreshTableData() {
  proxy.$refs.baseTableRef.refresh();
}
function handleCreate() {
  form.value = { sex: 0, deptId: listQuery.value.deptId };
  dialogStatus.value = 'add';
  dialogVisible.value = true;
}
function handleUpdate(row) {
  form.value = Object.assign({}, row);
  dialogStatus.value = 'update';
  dialogVisible.value = true;
}
async function deleteData(id) {
  let res = await proxy.$api.sys_user.delete(id);
  proxy.submitOk(res.messge);
  refreshTableData();
}
async function updatePwd(row) {
  form.value = Object.assign({}, row);
  dialogStatus.value = 'updatePwd';
  newPassword.value = '123456';
  dialogVisible.value = true;
}
async function submitPwd() {
  form.value.password = newPassword.value;
  let res = await proxy.$api.sys_user.update(form.value);
  proxy.submitOk(res.msg, () => {
    handelCurrentLoginUser();
  });
  dialogVisible.value = false;
}
function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.sys_user[form.value.userId ? 'update' : 'add'](form.value);
      proxy.submitOk(res.msg);
      handelCurrentLoginUser();
      refreshTableData();
      dialogVisible.value = false;
    }
  });
}
function handelCurrentLoginUser() {
  if (form.value.userId === userObj.value.userId) {
    // logout();
  }
}
</script>
<style lang="scss" scoped></style>
