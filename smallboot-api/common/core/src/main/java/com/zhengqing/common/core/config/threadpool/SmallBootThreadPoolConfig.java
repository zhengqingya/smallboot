package com.zhengqing.common.core.config.threadpool;

import com.zhengqing.common.base.constant.ThreadPoolConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p> 自定义线程池配置 </p>
 *
 * @author zhengqingya
 * @description
 * @date 2021/5/27 10:03
 */
@Configuration
public class SmallBootThreadPoolConfig {

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    @Bean(ThreadPoolConstant.SMALL_BOOT_THREAD_POOL)
    public Executor threadPoolExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程池大小
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        // 最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(corePoolSize + 1);
        // 队列容量
        threadPoolTaskExecutor.setQueueCapacity(200);
        // 活跃时间 60s
        threadPoolTaskExecutor.setKeepAliveSeconds(60);
        // 线程名字前缀
        threadPoolTaskExecutor.setThreadNamePrefix(ThreadPoolConstant.SMALL_BOOT_THREAD_NAME_PREFIX);
        // 设置在关闭线程池时是否等待任务完成
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 允许核心线程超时
        threadPoolTaskExecutor.setAllowCoreThreadTimeOut(true);
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPoolTaskExecutor.setAwaitTerminationSeconds(60);
        // 修改拒绝策略为使用当前线程执行
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //初始化线程池
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
