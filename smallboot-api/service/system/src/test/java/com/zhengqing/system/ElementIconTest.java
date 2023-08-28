package com.zhengqing.system;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * <p> element_icon图标获取 </p>
 *
 * @author zhengqingya
 * @description 可参考WebMagic官网文档：http://webmagic.io/docs/zh/posts/ch4-basic-page-processor/pageprocessor.html
 * @date 2020/7/1 16:43
 */
@Slf4j
public class ElementIconTest implements PageProcessor {

    public static void main(String[] args) {
        Spider.create(new ElementIconTest())
                // 从指定的url地址开始抓
                .addUrl("https://element-plus.org/zh-CN/component/icon.html#icon-collection")
                // 开启5个线程抓取
                .thread(5)
                // 启动爬虫
                .run();
    }


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
        List<Selectable> list = html.xpath("span[@class='icon-name']/text()").nodes();
        List<String> iconList = Lists.newArrayList();
        list.forEach(e -> iconList.add(e.toString()));

        // 2、保存到db中
        
    }

    @Override
    public Site getSite() {
        return Site.me().setRetryTimes(3) // 重试次数
                .setSleepTime(1000)   // 抓取间隔
                .setTimeOut(100 * 1000);  // 超时时间
    }

}
