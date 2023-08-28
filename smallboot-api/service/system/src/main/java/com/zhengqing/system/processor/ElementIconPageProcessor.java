package com.zhengqing.system.processor;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import com.zhengqing.common.base.enums.YesNoEnum;
import com.zhengqing.common.core.custom.validator.common.ValidList;
import com.zhengqing.system.enums.SysDictTypeEnum;
import com.zhengqing.system.model.dto.SysDictSaveBatchDTO;
import com.zhengqing.system.service.ISysDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.HashMap;
import java.util.List;

/**
 * <p> element_icon图标获取 </p>
 *
 * @author zhengqingya
 * @description 可参考WebMagic官网文档：http://webmagic.io/docs/zh/posts/ch4-basic-page-processor/pageprocessor.html
 * @date 2020/7/1 16:43
 */
@Slf4j
@RequiredArgsConstructor
public class ElementIconPageProcessor implements PageProcessor {

    private final ISysDictService sysDictService;


    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     *
     * @param page: 页面数据
     * @return void
     * @author zhengqingya
     * @date 2020/7/1 16:43
     */
    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        // 1、拿到所有 icon-name
        List<String> elIconList = Lists.newArrayList();
        List<Selectable> nodeList = html.xpath("span[@class='icon-name']/text()").nodes();
        nodeList.forEach(e -> elIconList.add(e.toString()));

        // 2、持久化至db
        ValidList<SysDictSaveBatchDTO> list = new ValidList<>();
        if (CollUtil.isEmpty(elIconList)) {
            return;
        }
        for (int i = 0; i < elIconList.size(); i++) {
            String elIcon = elIconList.get(i);
            list.add(
                    SysDictSaveBatchDTO.builder()
                            .dictTypeName(SysDictTypeEnum.Element_Icon图标.getDesc())
                            .code(elIcon)
                            .name(elIcon)
                            .value(elIcon)
                            .sort(i + 20)
                            .status(YesNoEnum.YES.getValue())
                            .build()
            );
        }
        this.sysDictService.addOrUpdateBatch(new HashMap<String, ValidList<SysDictSaveBatchDTO>>(16) {{
            this.put(SysDictTypeEnum.Element_Icon图标.getCode(), list);
        }}, true);
    }

    @Override
    public Site getSite() {
        return Site.me()
                // 重试次数
                .setRetryTimes(3)
                // 抓取间隔
                .setSleepTime(1000)
                // 超时时间
                .setTimeOut(100 * 1000);
    }

}
