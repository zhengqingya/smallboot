<template>
  <base-wrapper class="bg-color-lightgrey">
    <h1 class="flex-center-center">{{ proxy.$route.query.isAdd ? '添加店铺' : '编辑店铺' }}</h1>

    <div class="flex m-t-10">
      <base-card title="基本信息" class="flex-1 bg-color-white">
        <el-form ref="dataFormRef" :model="form" label-width="100px">
          <el-form-item label="名称:">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="所属地区:">
            <province-city-area v-model="form.provinceCityAreaList" />
          </el-form-item>
          <el-form-item label="详细地址:">
            <el-input v-model="form.address" />
          </el-form-item>
          <el-form-item label="联系人:">
            <el-input v-model="form.contactName" />
          </el-form-item>
          <el-form-item label="联系手机号:">
            <el-input v-model="form.contactPhone" />
          </el-form-item>
          <el-form-item label="是否显示:">
            <el-radio-group v-model="form.isShow">
              <el-radio :label="false">隐藏</el-radio>
              <el-radio :label="true">显示</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </base-card>

      <base-card title="营业信息" class="flex-1 m-l-10 bg-color-white">
        <el-form ref="dataFormRef" :model="form" label-width="100px">
          <el-form-item label="堂食:">
            <el-radio-group v-model="form.snackStatus">
              <el-radio :label="false">关闭</el-radio>
              <el-radio :label="true">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="外卖:">
            <el-radio-group v-model="form.snackStatus">
              <el-radio :label="false">关闭</el-radio>
              <el-radio :label="true">开启</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="营业:">
            <el-radio-group v-model="form.openStatus">
              <el-radio :label="false">未营业</el-radio>
              <el-radio :label="true">营业中</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="营业时间:">
            <el-input v-model="form.openTimeJson" />
          </el-form-item>
        </el-form>
      </base-card>
    </div>

    <div class="flex-center-center m-t-10">
      <router-link to="/mall/shop"> <el-button>返回</el-button></router-link>
      <el-button type="primary" class="m-l-20" @click="submitForm">确定</el-button>
    </div>
  </base-wrapper>
</template>

<script setup>
const { proxy } = getCurrentInstance();
let shopId = $ref(null);
let form = $ref({});

onMounted(() => {
  if (proxy.$route.query.isAdd) {
    form = { isShow: true, type: 1 };
  } else {
    shopId = proxy.$route.query.shopId;
    if (!shopId) {
      proxy.$router.push('/mall/shop');
      return;
    }
    initData();
  }
});

async function initData() {
  let res = await proxy.$api.sms_shop.detail({ shopId });
  form = res.data;
}

function submitForm() {
  proxy.$refs.dataFormRef.validate(async (valid) => {
    if (valid) {
      let res = await proxy.$api.sms_shop[apiForm.shopId ? 'update' : 'add'](apiForm);
      proxy.submitOk(res.message);
      proxy.$router.push('/mall/shop');
    }
  });
}
</script>
<style lang="scss" scoped></style>
