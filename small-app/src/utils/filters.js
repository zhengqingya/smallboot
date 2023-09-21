import pinyin from 'pinyin';
export const filters = {
  // 获取性别值
  sexName: (sex) => {
    // 拿到mixin混入的属性值
    const { proxy } = getCurrentInstance();
    return proxy.sexList.find((obj) => obj.value == sex).name;
  },
  // 取中文首字母大写 eg: 北京市 -> B
  getInitialsToUpperCase: (chineseText) => {
    const pinyinArray = pinyin(chineseText, { style: pinyin.STYLE_NORMAL });
    const initials = pinyinArray.map((pinyin) => pinyin[0].charAt(0));
    return initials.join('').substring(0, 1).toUpperCase();
  },
};
