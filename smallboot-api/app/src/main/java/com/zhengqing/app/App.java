package com.zhengqing.app;

import com.zhengqing.common.base.constant.ServiceConstant;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {ServiceConstant.SERVICE_BASE_PACKAGE})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
