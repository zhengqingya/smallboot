<template>
  <base-wraper>
    <div class="sku-container">
      <div>
        <el-autocomplete v-model="newAttrKey" style="width: 200px; margin-bottom: 10px"
          :fetch-suggestions="getAttrKeyList" value-key="attrKeyName" clearable placeholder="添加新属性 如：颜色"
          @select="selectAttrKey">
          <template #suffix v-if="attrKeyList.length === 0">
            <el-button link @click="addAttrKey">添加</el-button>
          </template>
        </el-autocomplete>
        <el-alert title="属性变动会影响sku数据！" type="warning" :closable="false" show-icon />
      </div>

      <div class="attr-box">
        <el-card v-for="item in attrDbList" :key="item.attrKeyName" class="card">
          <template #header>
            <div>
              <span>{{ item.attrKeyName }}</span>
              <!-- <el-button link style="float: right;">删除</el-button> -->
            </div>
          </template>
          <el-checkbox-group v-model="item.selectedAttrValueIdList" @change="changeCheckedAttrValue">
            <el-checkbox v-for="valueItem in item.attrValueList" :key="valueItem" :label="valueItem.attrValueId"
              size="large">{{ valueItem.attrValueName }}</el-checkbox>
          </el-checkbox-group>



        </el-card>
      </div>

      <div class="sku-box">
        <el-table :data="skuList" border style="width: 100%" height="240px">
          <!-- 额外添加的编号项（可删除） -->
          <el-table-column type="index" :label="'编号'" :width="55"></el-table-column>
          <!-- 自定义表项 -->
          <el-table-column v-for="column in columnList" :key="column.prop">
            <!-- 自定义表头 -->
            <template #header>
              <!-- 段落：show为true -->
              <p>{{ column.label }}</p>
            </template>
            <!-- 自定义单元格内容 -->
            <template #default="scope">
              <base-upload-single v-if="column.prop === 'coverImg'" v-model="scope.row[column.prop]"
                style="width: 100px" />
              <el-input v-else v-model="scope.row[column.prop]" />
            </template>
          </el-table-column>
        </el-table>
      </div>

    </div>
  </base-wraper>
</template>
<script>
export default {
  name: 'SkuForm',
  props: {
    attrList: {
      type: Array,
      default: () => []
    },
    skuList: {
      type: Array,
      default: () => []
    },
  },
  data() {
    return {
      attrDbList: [], // 属性数据
      attrKeyList: [], // 属性key
      newAttrKey: '', // 选择的属性key
      // attrList: []
      // 表头，以键(prop)值(label)存储表头
      columnList: [
        // { prop: 'specList', label: '商品规格' },
        { prop: 'sellPrice', label: '销售价' },
        { prop: 'totalStock', label: '总库存' },
        { prop: 'coverImg', label: '封面图' },
      ],
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    async init() {
      let keyIdList = this.attrList.map(item => item.attrKeyId)
      let res = await this.$api.pms_attr.listByKeyIdList({ idList: keyIdList.join() })
      this.attrDbList = res.data
      let valueIdList = []
      this.attrList.forEach(item => {
        item.attrValueList.forEach(valueItem => valueIdList.push(valueItem.attrValueId))
      })

      this.attrDbList.forEach(item => {
        let selectedAttrValueIdList = []
        item.attrValueList.forEach(valueItem => {
          if (valueIdList.includes(valueItem.attrValueId)) {
            selectedAttrValueIdList.push(valueItem.attrValueId)
          }
        })

        item.selectedAttrValueIdList = selectedAttrValueIdList
      })
      // console.log(this.attrDbList)
    },
    changeCheckedAttrValue(selectedData) {
      console.log(selectedData)
    },
    // 属性
    async getAttrKeyList(name, cb) {
      let res = await this.$api.pms_attr.listKey({ attrKeyName: name })
      this.attrKeyList = res.data
      cb(res.data)
    },
    addAttrKey() {
      this.newAttrKey = this.newAttr.replace(/\s/g, '')
      this.$api.pms_attr.addKey({ attrKeyName: this.newAttrKey })
    },
    selectAttrKey(keyObj) {
      // let attrValueList = this.getAttrValueList(keyObj.id)
      // console.log(attrValueList)
      // this.attrList.push({
      //   attrKeyName: keyObj.attrKeyName,
      //   attrValueList: attrValueList
      // })
      // console.log(this.attrList)
    },
    async getAttrValueList(id) {
      let res = await this.$api.pms_attr.listValue({ attrKeyId: id })
      return res.data
    }
  },
}
</script>

<style lang="scss" scoped>
.sku-container {
  width: 100%;
  height: 100%;
  background: rgba(239, 245, 210, 0.775);

  .attr-box {
    display: flex;

    .card {
      margin: 10px 5px;
      width: 300px;
    }
  }
}
</style>
