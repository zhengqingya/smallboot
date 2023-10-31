package com.zhengqing.common.web.util;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 多次读写BODY用HTTP REQUEST - 解决流只能读一次问题
 * </p>
 *
 * @author zhengqingya
 * @description
 * @date 2019/10/12 15:42
 */
@Slf4j
public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

    /**
     * 用于将流保存下来
     */
    private final byte[] body;

    // private String bodyString;

    public MultiReadHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        this.body = StreamUtils.copyToByteArray(request.getInputStream());
        // this.bodyString = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        // body = bodyString.getBytes("UTF-8");
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(this.body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    /**
     * 将前端请求的表单数据转换成json字符串
     *
     * @return java.lang.String
     */
    public String getBodyJsonStrByForm() {
        Map<String, Object> bodyMap = new HashMap<>(16);
        try {
            // 参数定义
            String paraName = null;
            // 获取请求参数并转换
            Enumeration<String> e = this.getParameterNames();
            while (e.hasMoreElements()) {
                paraName = e.nextElement();
                bodyMap.put(paraName, this.getParameter(paraName));
            }
        } catch (Exception e) {
            log.error("请求参数转换错误!", e);
        }
        return JSONUtil.toJsonStr(bodyMap);
    }

    /**
     * 将前端传递的json数据转换成json字符串
     *
     * @return java.lang.String
     */
    public String getBodyJsonStrByJson() {
        StringBuilder json = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = this.getReader();
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
        } catch (Exception e) {
            log.error("请求参数转换错误!", e);
        }
        return json.toString();
    }

}
