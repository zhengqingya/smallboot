<template>
  <div class="sku-container">
    <div>
      <el-autocomplete
        v-model="newAttr"
        style="width: 350px; margin-bottom: 20px"
        :fetch-suggestions="getAttrList"
        value-key="attrKeyName"
        clearable
        placeholder="添加新属性 如：颜色"
        @keyup.enter.native="addAttr"
        @select="(item) => (selectNewAttr = item)"
      >
        <template #suffix v-if="attrKeyList.length === 0">
          <el-button link @click="addAttr" v-show="!item">添加</el-button>
        </template>
      </el-autocomplete>
      <el-alert title="属性变动会影响sku数据！" type="warning" :closable="false" show-icon />
    </div>
    <div v-if="!disabled" class="sku-check">
      <div v-if="theme == 1" class="theme-1">
        <el-card v-for="(item, index) in attr" :key="index" class="item" shadow="never">
          <div slot="header" class="ub ubv-c ub-sb">
            <div class="uf1">{{ item.name }}</div>
            <div class="text-gray m-right1">是否SKU:</div>
            <el-switch v-model="item.is_sku" class="m-right1" />
            <el-popconfirm :title="`删除 ${item.name} 属性删除吗？`" @onConfirm="delSourceAttr(index)">
              <i slot="reference" class="el-icon-delete" />
            </el-popconfirm>
          </div>

          <el-checkbox v-for="item2 in item.item" :key="item2.name" v-model="item2.checked" :label="item2.name" size="small" />
          <el-autocomplete
            v-if="item.canAddAttribute"
            v-model="item.addAttribute"
            :fetch-suggestions="querySearchAttrValAsync"
            value-key="name"
            clearable
            class="add-attr"
            size="small"
            placeholder="新增一个规格"
            @focus="addAttrId = index"
            @keyup.enter.native="onAddAttribute(index)"
            @select="(item) => (selectNewAttrVal = item)"
          >
            <el-button slot="append" type="primary" icon="el-icon-plus" @click="onAddAttribute(index)">添加</el-button>
          </el-autocomplete>
        </el-card>
      </div>
      <el-table v-else :data="attr" :show-header="false" class="theme-2">
        <el-table-column prop="name" width="120" :resizable="false" />
        <el-table-column>
          <template v-slot="scope">
            <el-checkbox v-for="(item2, index2) in scope.row.item" :key="index2" v-model="item2.checked" :label="item2.name" size="small" />
          </template>
        </el-table-column>
        <el-table-column width="250">
          <template v-slot="scope">
            <el-input
              v-model="scope.row.addAttribute"
              size="small"
              placeholder="新增一个规格"
              class="add-attr"
              @keyup.enter.native="onAddAttribute(scope.$index)"
            >
              <el-button slot="append" size="small" icon="el-icon-plus" @click="onAddAttribute(scope.$index)">添加</el-button>
            </el-input>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="sku-list">
      <el-form ref="form" :model="form" status-icon inline-message>
        <el-table :data="form.skuData" row-key="sku" stripe border highlight-current-row max-height="600" :span-method="arraySpanMethod">
          <!-- 考虑到异步加载的情况，如果 attribute 数据先加载完成，则表头会立马展示，效果不理想，故使用emitAttribute 数据，该数据为计算属性，通过 attr 生成，结构与 attribute 一致 -->
          <el-table-column v-if="attribute.length > 0" type="index" width="50" align="center" :resizable="false" />

          <el-table-column
            v-for="(eAttr, index) in emitAttribute"
            :key="`attribute-${index}`"
            :label="eAttr.name"
            :prop="eAttr.attr_id"
            :width="staticWidth"
            align="center"
            :resizable="false"
            sortable
          />
          <el-table-column
            v-for="(item, index) in structure"
            :key="`structure-${index}`"
            :label="item.label"
            :prop="item.name"
            align="center"
            :resizable="false"
            :min-width="staticWidth + 'px'"
          >
            <!-- 自定义表头 -->
            <template #header>
              <span :class="{ required_title: item.required }">
                {{ item.label }}
              </span>
              <el-tooltip v-if="item.tip" effect="dark" :content="item.tip" placement="top">
                <i class="el-icon-info" />
              </el-tooltip>
            </template>
            <!-- 自定义单元格内容 -->
            <template #default="scope">
              <!-- 增加是 key 是为了保证异步验证不会出现 skuData 数据变化后无法验证的 bug -->
              <el-form-item
                v-if="item.type == 'input'"
                :key="`structure-input-${index}-${scope.row.sku}`"
                :prop="'skuData.' + scope.$index + '.' + item.name"
                :rules="rules[item.name]"
              >
                <el-input v-model="scope.row[item.name]" :type="item.input_type || ''" clearable :placeholder="`请输入${item.label}`" size="small" />
              </el-form-item>
              <!-- <el-form-item
                v-else-if="item.type == 'slot'"
                :key="`structure-input-${index}-${scope.row.sku}`"
                :prop="'skuData.' + scope.$index + '.' + item.name"
                :rules="rules[item.name]"
              >
                <slot :name="item.name" :$index="scope.$index" :row="scope.row" :column="scope.column" />
              </el-form-item> -->
            </template>
          </el-table-column>
          <!-- 批量设置，当 sku 数超过 2 个时出现 -->
          <template v-if="isBatch && form.skuData.length > 2" slot="append">
            <el-table :data="[{}]" :show-header="false">
              <el-table-column :width="attribute.length * staticWidth + 50" align="center" :resizable="false">批量设置（Enter确定）</el-table-column>
              <el-table-column
                v-for="(item, index) in structure"
                :key="`batch-structure-${index}`"
                align="center"
                :resizable="false"
                min-width="120px"
              >
                <el-input
                  v-if="item.type == 'input' && item.batch != false"
                  v-model="batch[item.name]"
                  clearable
                  :type="item.input_type || ''"
                  :placeholder="`填写一个${item.label}`"
                  size="small"
                  @keyup.enter.native="onBatchSet(item.name)"
                  @change="onBatchSet(item.name)"
                />
              </el-table-column>
            </el-table>
          </template>
        </el-table>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'sku',
  props: {
    sourceAttribute: {
      type: Object,
      default: () => {},
    },
    attribute: {
      type: Array,
      default: () => [],
    },
    sku: {
      type: Array,
      default: () => [],
    },
    structure: {
      type: Array,
      default: () => [
        { name: 'price', type: 'input', label: '价格' },
        { name: 'stock', type: 'input', label: '库存' },
      ],
    },
    // sku 字段分隔符
    separator: {
      type: String,
      default: ';',
    },
    // 无规格的 sku
    emptySku: {
      type: String,
      default: '',
    },
    // 是否显示 sku 选择栏
    disabled: {
      type: Boolean,
      default: false,
    },
    // 主题风格
    theme: {
      type: Number,
      default: 1,
    },
    // 是否开启异步加载
    async: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      attrKeyList: [],
      // -----------------
      newAttr: '',
      isInit: false,
      editorNameIndex: -1,
      attr: {},
      staticWidth: 90,
      form: {
        skuData: [],
      },
      batch: {},
      selectNewAttr: null,
      selectNewAttrVal: null,
      addAttrId: 0,
    }
  },
  computed: {
    rules() {
      // 重新生成验证规则
      const rules = {}
      this.structure.forEach((v) => {
        if (v.type === 'input') {
          rules[v.name] = []
          if (v.required) {
            rules[v.name].push({ required: true, message: `${v.label}不能为空`, trigger: 'blur' })
          }
          if (v.validate) {
            rules[v.name].push({ validator: this.customizeValidate, trigger: 'blur' })
          }
        } else if (v.type === 'slot') {
          rules[v.name] = []
          if (v.required) {
            rules[v.name].push({ required: true, message: `${v.label}不能为空`, trigger: ['change', 'blur'] })
          }
          if (v.validate) {
            rules[v.name].push({ validator: this.customizeValidate, trigger: ['change', 'blur'] })
          }
        }
      })
      return rules
    },
    isBatch() {
      return this.structure.some((item) => {
        return item.type === 'input' && item.batch !== false
      })
    },
    // 将 attr 数据还原会 attribute 数据的结构，用于更新 attribute
    emitAttribute() {
      const attribute = []
      for (const attr_id in this.attr) {
        const v1 = this.attr[attr_id]
        if (!v1.is_sku) continue
        const obj = {
          name: v1.name,
          item: {},
          attr_id,
        }
        let len = 0
        for (const val_id in v1.item) {
          const v2 = v1.item[val_id]
          v2.checked && (obj.item['' + val_id] = { name: v2.name }) && len++
        }

        len && (obj['len'] = len) && attribute.push(obj)
      }
      // attribute.sort(sortObjByField('len'))
      return null
    },
    rowSpan() {
      const obj = {}
      this.attribute.forEach(({ attr_id }, index) => {
        let rowSpan = 1
        for (let i = index + 1; i < this.attribute.length; i++) rowSpan *= this.attribute[i].len
        obj[attr_id] = rowSpan
      })
      return obj
    },
  },
  watch: {
    attr: {
      handler(n) {
        if (!this.isInit) {
          // 更新父组件
          this.$emit('update:attribute', this.emitAttribute)
          this.$emit('update:sourceAttribute', this.attr)
          this.$emit('change')
        }
        // 解决通过 $emit 更新后无法拿到 attribute 最新数据的问题
        this.$nextTick(() => {
          if (this.attribute.length !== 0) {
            this.combinationAttribute()
          } else {
            this.form.skuData = []
            const obj = {
              sku: this.emptySku,
            }
            this.structure.forEach((v) => {
              if (!(v.type === 'slot' && v.skuProperty === false)) {
                obj[v.name] = typeof v.defaultValue !== 'undefined' ? v.defaultValue : ''
              }
            })
            this.form.skuData.push(obj)
          }
          this.clearValidate()
        })
      },
      deep: true,
    },
    'form.skuData': {
      handler(newValue, oldValue) {
        if (!this.isInit || (newValue.length === 1 && newValue[0].sku === this.emptySku)) {
          // 如果有老数据，或者 sku 数据为空，则更新父级 sku 数据
          if (oldValue.length || !this.sku.length) {
            // 更新父组件
            const arr = []
            newValue.forEach((v1) => {
              const obj = { ...v1 }
              this.structure.forEach((v2) => {
                if (!(v2.type === 'slot' && v2.skuProperty === false)) {
                  obj[v2.name] = v1[v2.name] || (typeof v2.defaultValue !== 'undefined' ? v2.defaultValue : '')
                }
              })
              arr.push(obj)
            })
            this.$emit('update:sku', arr)
            // this.$emit('change')
          }
        }
      },
      deep: true,
    },
  },
  mounted() {
    !this.async && this.init()
  },
  methods: {
    async getAttrList(name, cb) {
      let res = await this.$api.pms_attr.list({ attrKeyName: name })
      this.attrKeyList = res.data
      cb(res.data)
    },
    addAttr() {
      this.newAttr = this.newAttr.replace(/\s/g, '')
      this.loading = true
      this.$api.pms_attr.add({ attrKeyName: this.newAttr })
    },

    // ----------------- 下面暂未联调

    arraySpanMethod({ column: { property: attr_id }, rowIndex }) {
      if (this.rowSpan[attr_id]) {
        const rowSpan = rowIndex % this.rowSpan[attr_id] === 0 ? this.rowSpan[attr_id] : 0
        return [rowSpan, 1]
      }
      return [1, 1]
    },

    querySearchAttrValAsync(name, cb) {
      // ATTR_VAL.getList({ name, attr_id: this.addAttrId })
      //   .then(({ data }) => {
      //     cb(data.filter(({ _id }) => !this.attr[this.addAttrId].item[_id]))
      //   })
      //   .catch((err) => console.log('err', err))
    },
    init() {
      this.isInit = true
      this.attr = this.sourceAttribute
      // 通过 sku 更新 skuData，但因为 skuData 是实时监听 attr 变化并自动生成，而 watch 是在 methods 后执行，所以增加 setTimeout 方法，确保 skuData 生成后在执行下面的代码
      setTimeout(() => {
        this.sku.forEach((skuItem) => {
          // skuItem.sku = this.attribute.map(item => skuItem[item.name]).join(this.separator)
          this.form.skuData.forEach((skuDataItem) => {
            if (skuItem.sku === skuDataItem.sku) {
              skuDataItem['_id'] = skuItem._id
              this.structure.forEach((structureItem) => {
                skuDataItem[structureItem.name] = skuItem[structureItem.name]
              })
            }
          })
        })
        this.isInit = false
      }, 50)
    },
    editorName() {
      this.editorNameIndex = -1
    },
    // 根据 attribute 进行排列组合，生成 skuData 数据
    combinationAttribute(index = 0, dataTemp = []) {
      const arrt_id = this.attribute[index]?.attr_id ?? null
      if (arrt_id === null) return
      const attr = this.attribute[index]
      if (index === 0) {
        for (const val_id in attr.item) {
          const obj = {
            [arrt_id]: attr.item[val_id].name,
            attr: {},
          }
          obj['attr'][arrt_id] = +val_id
          obj['sku'] = Object.values(obj['attr']).sort().join(this.separator)
          this.structure.forEach((v) => {
            if (!(v.type === 'slot' && v.skuProperty === false)) {
              obj[v.name] = typeof v.defaultValue !== 'undefined' ? v.defaultValue : ''
            }
          })
          dataTemp.push(obj)
        }
      } else {
        const temp = []
        for (let i = 0; i < dataTemp.length; i++) {
          for (const val_id in attr.item) {
            temp.push({ ...dataTemp[i] })
            temp[temp.length - 1][arrt_id] = attr.item[val_id].name
            const tmpAttr = { ...temp[temp.length - 1]['attr'] }
            tmpAttr[arrt_id] = +val_id
            temp[temp.length - 1]['attr'] = tmpAttr
            temp[temp.length - 1]['sku'] = Object.values(tmpAttr)
              .sort((v1, v2) => v1 - v2)
              .join(this.separator)
          }
        }
        dataTemp = temp
      }

      if (index !== this.attribute.length - 1) {
        this.combinationAttribute(index + 1, dataTemp)
      } else {
        if (!this.isInit || this.async) {
          // 将原有的 sku 数据和新的 sku 数据比较，相同的 sku 则把原有的 sku 数据覆盖到新的 sku 数据里
          for (let i = 0; i < this.form.skuData.length; i++) {
            for (let j = 0; j < dataTemp.length; j++) {
              if (this.form.skuData[i].sku === dataTemp[j].sku) {
                dataTemp[j] = this.form.skuData[i]
              }
            }
          }
        }
        this.form.skuData = dataTemp
      }
    },
    // 新增一个规格
    async onAddAttribute(index) {
      this.attr[index].addAttribute = this.attr[index].addAttribute.trim()
      if (this.attr[index].addAttribute !== '') {
        if (!this.attr[index].addAttribute.includes(this.separator)) {
          const data = { name: this.attr[index].addAttribute, checked: true }

          if (this.newAttr === this?.selectNewAttrVal?.name) {
            this.$set(this.attr[index].item, '' + this.selectNewAttrVal._id, data)
            this.newAttr = ''
            return
          }
          if (this.loading) return
          // 不然就需要把这个新属性加入到数据库中
          this.loading = true
          // const res = await ATTR_VAL.addOne({ name: this.attr[index].addAttribute, attr_id: index })
          //   .then((r) => r.data)
          //   .catch((err) => {
          //     console.log(err)
          //   })
          //   .finally(() => {
          //     this.loading = false
          //   })
          // if (!res) return
          this.$set(this.attr[index].item, '' + res._id, data)
          this.attr[index].addAttribute = ''
        } else {
          this.$message({
            type: 'warning',
            message: `规格里不允许出现「 ${this.separator} 」字符，请检查后重新添加`,
          })
        }
      }
    },
    onBatchSet(type) {
      if (this.batch[type] !== '') {
        this.form.skuData.forEach((v) => {
          v[type] = this.batch[type]
        })
        this.batch[type] = ''
        // 批量设置完成后，触发一次当前列的验证
        this.validateFieldByColumns([type], () => {})
      }
    },
    // 自定义输入框验证，通过调用 structure 里的 validate 方法实现，重点是 callback 要带过去
    customizeValidate(rule, value, callback) {
      const [model, index, name] = rule.field.split('.')
      this.structure.forEach((v) => {
        if (v.name === name) {
          v.validate(this.form[model], index, callback)
        }
      })
    },
    // sku 表单验证
    validate(callback) {
      this.$refs['form'].validate((valid) => {
        callback(valid)
      })
    },
    validateFieldByColumns(colums, callback) {
      const props = []
      this.form.skuData.forEach((v, i) => {
        colums.forEach((v) => {
          props.push(`skuData.${i}.${v}`)
        })
      })
      this.$refs['form'].validateField(props, (valid) => {
        callback(valid)
      })
    },
    validateFieldByRows(index, prop, callback) {
      this.$refs['form'].validateField([`skuData.${index}.${prop}`], (valid) => {
        callback(valid)
      })
    },
    clearValidate() {
      this.$refs['form'].clearValidate()
    },
    delSourceAttr(id) {
      this.$delete(this.attr, id)
    },
  },
}
</script>

<style lang="scss" scoped>
.sku-container {
  width: 100%;
  height: 200px;
  ::v-deep .el-card {
    margin: 10px 0;
    .el-card__header {
      line-height: initial;
      padding: 10px 20px;
    }
    .el-card__body {
      padding: 10px 20px 20px;
    }
  }
  .sku-check {
    .theme-1 {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
      margin-bottom: 10px;
      .item {
        width: 32%;
        &:last-child:nth-child(3n - 1) {
          margin-right: calc(100% - 32% * 2 - 4% / 2) !important;
        }
        .add-attr {
          width: 100%;
          margin-top: 10px;
        }
      }
    }
    .theme-2 {
      border: 1px solid #ebeef5;
      border-bottom: 0;
      margin-bottom: 20px;
    }
  }
  .sku-name {
    text-align: right;
  }
  .batch-set {
    width: 100%;
    margin-top: 5px;
  }
  .sku-list {
    line-height: initial;
    ::v-deep .el-input__inner {
      text-align: center;
    }
    ::v-deep .el-table__append-wrapper {
      overflow: initial;
      .el-table {
        overflow: initial;
        .el-table__body-wrapper {
          overflow: initial;
        }
      }
    }
    ::v-deep .el-form-item {
      margin-bottom: 0;
      .el-form-item__content {
        line-height: initial;
        .el-form-item__error {
          margin-left: 0;
        }
      }
    }
    .required_title::before {
      content: '*';
      color: #f56c6c;
    }
  }
}
.el-icon-delete {
  cursor: pointer;
  transition: color 0.3s;
  &:hover {
    color: red;
  }
}
</style>
