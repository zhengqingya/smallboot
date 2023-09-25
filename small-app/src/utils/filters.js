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
};
