<template>
  <base-wraper>
    <el-form ref="roleForm" :model="roleForm" :rules="rules" label-width="100px">
      <base-title-card title="角色信息">
        <base-table-cell>
          <el-row :gutter="10">
            <el-col :span="12">
              <base-cell-item label="角色名：">{{ roleForm.name }}</base-cell-item>
            </el-col>
            <el-col :span="12">
              <base-cell-item label="角色编码：">{{ roleForm.code }}</base-cell-item>
            </el-col>
          </el-row>
        </base-table-cell>
      </base-title-card>
      <base-title-card title="权限信息">
        <el-row :gutter="20">
          <el-col :span="14">
            <el-tree ref="menuTree" :data="allMenus" :props="defaultProps" show-checkbox node-key="menuId"
              @node-click="handleNodeClick" />
          </el-col>
          <el-col :span="10">
            <el-card v-if="permissionBtns.length > 0 && showConfigContainer" class="box-card">
              <template #header>
                <div class="card-header">
                  <span>编辑页面按钮权限</span>
                  <el-button style="float: right; padding: 3px 0" link @click="handleSavePermissionBtns">保存</el-button>
                </div>
              </template>
              <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" style="margin-bottom: 10px"
                @change="handleCheckAllBtnsChange">全选</el-checkbox>
              <el-checkbox-group v-model="currentSelectedBtns" @change="handleCheckedBtnsChange">
                <el-checkbox v-for="(item, index) in permissionBtns" :key="index" :label="item.id">{{ item.name }}
                </el-checkbox>
              </el-checkbox-group>
            </el-card>
            <base-no-data v-else>该菜单暂未分配按钮权限</base-no-data>
          </el-col>
        </el-row>
      </base-title-card>
    </el-form>
    <div>
      <el-button type="primary" @click="saveData">保存</el-button>
      <router-link to="/system/role">
        <el-button type="warning">返回</el-button>
      </router-link>
    </div>
  </base-wraper>
</template>
<script>
export default {
  data() {
    return {
      roleForm: {
        // 表单数据
        roleId: undefined, // 角色id
        name: '', // 角色名称
        code: '', // 角色编号
        status: '', // 状态
        menuIdList: '', // 角色菜单权限
        menuList: undefined,
      },
      allMenus: [],
      defaultProps: {
        children: 'children',
        label: 'title',
      },
      rules: {
        // 表单验证
        code: [{ required: true, message: '请输入用户ID', trigger: 'blur' }],
        name: [{ required: true, message: '请输入用户名称', trigger: 'blur' }],
        accountNum: [{ required: true, message: '请输入用户账号', trigger: 'blur' }],
        contact: [{ required: true, message: '请输入联系人', trigger: 'blur' }],
        contactPhone: [{ required: true, message: '请输入联系手机', trigger: 'blur' }],
        roleId: [{ required: true, message: '请选择角色' }],
      },
      checkAll: false,
      isIndeterminate: true,
      currentClickMenu: [],
      permissionBtns: [],
      currentSelectedBtns: [],
      showConfigContainer: false,
    }
  },
  computed: {
    isEdit() {
      return !(this.$route.fullPath.indexOf('id') === -1) // 根据路由判断
    },
    hasEditBtnPermission() {
      // 是否有编辑按钮的权限
      const hasChildren = this.currentClickMenu && this.currentClickMenu.children.length === 0
      return hasChildren
    },
  },
  mounted() {
    this.getMenuTree()
  },
  methods: {
    async getMenuTree() {
      let res = await this.$api.sys_menu.menuTree()
      this.allMenus = res.data
      if (this.isEdit) {
        let res = await this.$api.sys_role.detail(this.$route.query.id)
        this.roleForm = res.data
        if (this.roleForm.menuIdList) {
          const roleMenus = this.filterLeafNode(this.allMenus, this.roleForm.menuIdList)
          this.$refs.menuTree.setCheckedKeys(roleMenus)
        }
      }
    },
    filterLeafNode(allMenus, roleMenu) {
      var result = []
      var leafNodes = []
      this.getLeafNode(allMenus, leafNodes)
      if (leafNodes && roleMenu) {
        leafNodes.forEach((nodeId) => {
          roleMenu.forEach((roleMenuId) => {
            if (nodeId === roleMenuId) {
              result.push(nodeId)
            }
          })
        })
      }
      return result
    },
    getLeafNode(menuIdList, result) {
      menuIdList.forEach((item) => {
        if (item.children == null || item.children.length === 0) {
          result.push(item.menuId)
          return result
        } else {
          this.getLeafNode(item.children, result)
        }
      })
    },
    saveData() {
      // 获取选中的菜单
      this.roleForm.menuIdList = this.$refs.menuTree.getCheckedKeys().concat(this.$refs.menuTree.getHalfCheckedKeys())
      this.roleForm.menuList = this.$refs.menuTree.getCheckedNodes(false, true)
      this.$refs.roleForm.validate(async (valid) => {
        if (valid) {
          let res = await this.$api.sys_role.savePermissionMenuIds(this.roleForm)
          this.submitOk(res.msg)
          this.$router.push('/system/role')
        }
      })
    },
    handleNodeClick(data) {
      this.currentClickMenu = data
      if (this.hasEditBtnPermission) {
        this.showConfigContainer = true
        this.getCurrentPagePermissionBtns(data.menuId)
        this.getSelectedPermissionBtn(data.menuId)
      } else {
        this.showConfigContainer = false
      }
    },
    async getCurrentPagePermissionBtns(menuId) {
      let res = await this.$api.sys_menu.getPermListByMenuId(menuId)
      this.permissionBtns = res.data
    },
    async getSelectedPermissionBtn(menuId) {
      const roleId = this.$route.query.id
      let res = await this.$api.sys_role.getPermissionBtnsByRoleIdAndMenuId({
        roleId: roleId,
        menuId: menuId,
      })
      this.currentSelectedBtns = res.data
    },
    async handleSavePermissionBtns() {
      const submitObj = {
        roleId: this.$route.query.id,
        menuId: this.currentClickMenu.menuId,
        permissionIdList: this.currentSelectedBtns,
      }
      let res = await this.$api.sys_role.savePermissionBtnIds(submitObj)
      this.submitOk(res.msg)
    },
    handleCheckAllBtnsChange(val) {
      const allBtnsId = this.permissionBtns.map((item) => item.id)
      this.currentSelectedBtns = val ? allBtnsId : []
      this.isIndeterminate = false
    },
    handleCheckedBtnsChange(value) {
      const checkedCount = value.length
      this.checkAll = checkedCount === this.permissionBtns.length
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.permissionBtns.length
    },
  },
}
</script>
<style lang="scss" scoped>
</style>
