import { pinyin } from 'pinyin-pro';

export const filters = {
  // 获取性别值
  sexName: (sex) => {
    // 拿到mixin混入的属性值
    const { proxy } = getCurrentInstance();
    return proxy.sexList.find((obj) => obj.value == sex).name;
  },
  // 取中文首字母大写 eg: 北京市 -> B
  getInitialsToUpperCase: (chineseText) => {
    const initials = pinyin(chineseText.substring(0, 1), { toneType: 'none' })
      .substring(0, 1)
      .toUpperCase();
    // console.log('首字母大写:', initials);
    return initials;
  },
  // 四舍五入保留2位小数
  keepTwoDecimal(num) {
    return num.toFixed(2);
  },
  // 计算位置距离
  calDistance(distanceMeters) {
    if (!distanceMeters) {
      return '?m';
    }
    let d = this.keepTwoDecimal(distanceMeters / 1000);
    if (d.indexOf(0) == 0) {
      return this.unifyNumber(this.keepTwoDecimal(distanceMeters)) + 'm';
    }
    return this.unifyNumber(d) + 'km';
  },
  // 小数位为0的舍弃 eg: 1.10 -> 1.1
  unifyNumber(num) {
    if (num === '') {
      return 0;
    } else {
      let handleNum = parseFloat(num);
      let isToFixed =
        handleNum.toString().includes('.') && handleNum.toString().split('.')[1].length > 2;
      if (isToFixed) {
        if (handleNum.toFixed(2).toString().split('.')[1] == '00') {
          return handleNum.toFixed(0);
        } else {
          return handleNum.toFixed(2);
        }
      } else {
        return handleNum;
      }
    }
  },
};
