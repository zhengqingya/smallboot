import { ElMessage, ElMessageBox } from 'element-plus';

//一键复制
import useClipboard from 'vue-clipboard3';
const { toClipboard } = useClipboard();

// 抽取公用的实例 - 操作成功与失败消息提醒内容等
export default {
  data() {
    return {
      sexList: [
        { name: '未知', value: 0 },
        { name: '男', value: 1 },
        { name: '女', value: 2 },
      ],
      // 弹出框标题
      dialogTitleObj: {
        add: '添加',
        update: '编辑',
        detail: '详情',
      },
    };
  },
  methods: {
    // 操作成功消息提醒内容
    submitOk(msg, cb) {
      this.$notify({
        title: '成功',
        message: msg || '操作成功！',
        type: 'success',
        duration: 2000,
        onClose: function () {
          cb && cb();
        },
      });
    },
    // 操作失败消息提醒内容
    submitFail(msg, cb) {
      this.$message({
        message: msg || '网络异常，请稍后重试！',
        type: 'error',
        onClose: function () {
          cb && cb();
        },
      });
    },
    // 消息确认后执行
    submitConfirm(msg, cb) {
      ElMessageBox.alert(msg, '', {
        confirmButtonText: '确认',
        showClose: false,
        callback: (action) => {
          if (action == 'confirm') {
            cb && cb();
          }
        },
      });
    },
     // 封装复制方法
     async doCopyUrl(url) {
      try {
        console.log('111:', url);
        await toClipboard(url);
        this.submitOk('复制成功！');
      } catch (e) {
        console.error(e);
        this.submitFail('复制失败！您的浏览器不支持复制功能！');
      }
    },
  },
};
