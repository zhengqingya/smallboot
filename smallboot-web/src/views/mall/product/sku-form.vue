<template>
  <base-wraper>
    <div class="sku-container">
      <div>
        <el-autocomplete
          v-model="newAttrKey"
          style="width: 200px; margin-bottom: 10px"
          :fetch-suggestions="getAttrKeyList"
          value-key="attrKeyName"
          clearable
          placeholder="添加新属性 如：颜色"
          @select="selectAttrKey"
        >
          <template #suffix v-if="newAttrKey && attrKeyList.length === 0">
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
          <el-checkbox-group v-model="item.selectedAttrValueIdList" @change="changeCheckedAttrValue(item)">
            <el-checkbox v-for="valueItem in item.attrValueList" :key="valueItem" :label="valueItem.attrValueId" size="large">{{
              valueItem.attrValueName
            }}</el-checkbox>
          </el-checkbox-group>
        </el-card>
      </div>

      <div class="sku-box">
        <el-table :data="skuList" border style="width: 100%" height="350px">
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
              <div v-if="column.isSpec">
                <div>{{ scope.row[column.prop] }}</div>
              </div>
              <div v-else>
                <base-upload-single v-if="column.prop === 'coverImg'" v-model="scope.row[column.prop]" style="width: 100px" />
                <el-input v-else v-model="scope.row[column.prop]" />
              </div>
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
    // attrList: {
    //   type: Array,
    //   default: () => [],
    // },
    // sku值
    modelValue: {
      type: Array,
      default: () => [],
    },
  },
  computed: {
    skuList: {
      set: function (val) {
        // console.log(111, val)
        this.$emit('update:modelValue', val)
      },
      get: function () {
        return this.modelValue
      },
    },
  },
  // watch: {
  //   skuList(val) {
  //     console.log(222, val)
  //     // this.skuList = val
  //     this.$emit('update:modelValue', val)
  //   },
  // },
  data() {
    return {
      attrDbList: [], // 属性数据
      attrKeyList: [], // 属性key
      newAttrKey: '', // 选择的属性key
      attrList: [], // 属性列表
      // 表头，以键(prop)值(label)存储表头
      columnList: [
        // { isSpec: true, prop: '商品规格', label: '规格1' },
        { isSpec: false, prop: 'sellPrice', label: '销售价' },
        { isSpec: false, prop: 'totalStock', label: '总库存' },
        { isSpec: false, prop: 'coverImg', label: '封面图' },
      ],
    }
  },
  created() {
    this.init()
  },
  methods: {
    async init() {
      if (this.skuList.length === 0) {
        return
      }
      // 拿到选中的属性key，value 对应的ids
      let keyIdList = []
      // 拿到选中的属性value
      let valueIdList = []
      this.skuList.forEach((item) => {
        item.specList.forEach((specItem) => {
          keyIdList.push(specItem.attrKeyId)
          valueIdList.push(specItem.attrValueId)
        })
      })
      // 去重
      keyIdList = Array.from(new Set(keyIdList))
      valueIdList = Array.from(new Set(valueIdList))

      let res = await this.$api.pms_attr.listByKeyIdList({ idList: keyIdList.join() })
      this.attrDbList = res.data

      this.attrDbList.forEach((item) => {
        let selectedAttrValueIdList = []
        item.attrValueList.forEach((valueItem) => {
          if (valueIdList.includes(valueItem.attrValueId)) {
            selectedAttrValueIdList.push(valueItem.attrValueId)
          }
        })

        item.selectedAttrValueIdList = selectedAttrValueIdList
      })

      this.handleTableColumn(false)
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
    // 监听选中属性
    changeCheckedAttrValue(item) {
      this.handleTableColumn(true)
    },
    // 动态表格项数据
    handleTableColumn(isSelectedAttr) {
      let specList = []

      if (isSelectedAttr) {
        // 说明是选择属性时触发的，这里就需要重新计算表头数据
        this.columnList = this.columnList.filter((item) => !item.isSpec)
        this.attrDbList.forEach((item) => {
          item.selectedAttrValueIdList.forEach((selectedValueId) => {
            item.attrValueList.forEach((valueItem) => {
              if (valueItem.attrValueId === selectedValueId) {
                specList.push({
                  attrKeyId: item.attrKeyId,
                  attrKeyName: item.attrKeyName,
                  attrValueId: valueItem.attrValueId,
                  attrValueName: valueItem.attrValueName,
                })
              }
            })
          })
        })
      } else {
        // 第一次初始化数据表头
        if (this.skuList.length > 0) {
          this.skuList.forEach((item) => {
            item.specList.forEach((specItem) => {
              specList.push(specItem)
            })
          })
          // 去重
          const unique = (arrs) => {
            const res = new Map()
            return arrs.filter((arr) => !res.has(arr.attrValueName) && res.set(arr.attrValueName, 1))
          }
          specList = unique(specList)
          // console.log('去重后:', specList)
        }
      }

      // 处理表头
      let attrKeyList = Array.from(new Set(specList.map((item) => item.attrKeyName)))
      attrKeyList.forEach((attrKeyName) => {
        // 头部插入数据
        this.columnList.unshift({
          isSpec: true,
          prop: attrKeyName,
          label: attrKeyName,
        })
      })

      let attrList = this.getGroupObject(specList, 'attrKeyName')
      this.handleSkuCartesian(attrList)
    },
    // 分组-对象
    getGroupObject(list, attr) {
      const map = new Map()
      list.forEach((item, index, arr) => {
        if (!map.has(item[attr])) {
          map.set(
            item[attr],
            arr.filter((a) => a[attr] == item[attr]),
          )
        }
      })
      return Object.fromEntries(Array.from(map).map((item) => [item[0], item[1]]))
    },
    // sku 笛卡尔积  -- 支持数组和对象
    handleSkuCartesian(attrList) {
      const attrValueList = Object.values(attrList).map((attr) => attr)
      const cartesianSku = (...arrays) => arrays.reduce((acc, curr) => acc.flatMap((a) => curr.map((c) => [...a, c])), [[]])
      const skuCombinationList = cartesianSku(...attrValueList)
      // 为每一个sku属性加上规格值
      let newSkuList = []
      skuCombinationList.forEach((specList) => {
        let newSkuItem = {}

        let isExistOldSku = false
        this.skuList.forEach((dbSkuItem) => {
          if (JSON.stringify(dbSkuItem.specList) == JSON.stringify(specList)) {
            isExistOldSku = true
            newSkuItem = { ...dbSkuItem }
            return
          }
        })

        specList.forEach((specItem) => {
          newSkuItem[`${specItem.attrKeyName}`] = specItem.attrValueName
          newSkuItem.sort = 1
          newSkuItem.specList = specList
        })

        if (isExistOldSku) {
          // 一一对应
        } else {
          // 加入新sku数据
        }
        newSkuList.push(newSkuItem)
      })
      this.skuList = JSON.parse(JSON.stringify(newSkuList))
      // console.log(3, this.skuList)
    },
    // 选中属性时触发
    selectAttrKey(keyObj) {
      let attrKeyName = keyObj.attrKeyName
      let attrKeyNameList = this.attrList.map((attr) => attr.attrKeyName)
      console.log(22, this.attrList)
      console.log(attrKeyNameList)
      if (attrKeyNameList.includes(attrKeyName)) {
        console.log(1111)
        return
      }
      let attrValueList = this.getAttrValueList(keyObj.id)
      console.log(attrValueList)

      this.attrList.push({
        attrKeyName: attrKeyName,
        attrValueList: attrValueList,
      })
      // console.log(this.attrList)
    },
    async getAttrValueList(id) {
      let res = await this.$api.pms_attr.listValue({ attrKeyId: id })
      return res.data
    },
  },
}
</script>

<style lang="scss" scoped>
.sku-container {
  width: 800px;
  height: 100%;
  background: rgba(239, 245, 210, 0.775);

  .attr-box {
    display: flex;
    width: 600px;
    .card {
      margin: 10px 5px;
      width: 300px;
    }
  }
}
</style>
