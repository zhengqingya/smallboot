package com.zhengqing.mall.schedule;

import com.zhengqing.mall.service.OmsLogisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>
 * 数据定时任务-更新物流信息
 * </p>
 *
 * @author zhengqingya
 * @description FIXME 定时任务技术使用xxl-job
 * @date 2020/4/12 0:48
 */
@Slf4j
@Component
public class WebMallUpdateLogisticSchedule {
    @Resource
    private OmsLogisticService omsLogisticService;

    /**
     * 每天凌晨1点触发执行1次
     *
     * @SchedulerLock 注解参数
     * name：锁的名称，同一时间只能执行一个同名的任务
     * lockAtMostFor：该属性指定在执行节点死亡的情况下应保持锁定多长时间。这只是一个回退，在正常情况下，一旦任务完成就会释放锁。 您必须设置lockAtMostFor一个比正常执行时间长得多的值。如果任务花费的时间超过 lockAtMostFor所产生的行为可能是不可预测的（多个进程将有效地持有锁）
     * lockAtLeastFor：该属性指定应保留锁的最短时间。它的主要目的是在节点之间的任务和时钟差异非常短的情况下防止从多个节点执行。
     * <p>
     * 通过设置lockAtMostFor我们确保即使节点死亡也会释放锁，通过设置lockAtLeastFor 我们确保它在指定3分钟内不会执行超过一次。请注意，这lockAtMostFor只是一个安全网，以防执行任务的节点死亡，因此将其设置为明显大于最大估计执行时间的时间。 如果任务花费的时间超过lockAtMostFor，它可能会再次执行并且结果将是不可预测的（更多的进程将持有锁）。
     */
//    @Scheduled(cron = "*/5 * * * * ?") // 每5秒执行1次
    @Scheduled(cron = "0 0 01 * * ?")
//    @SchedulerLock(name = "updateLogisticForDb", lockAtMostFor = "5m", lockAtLeastFor = "5m")
    public void updateLogisticForDb() {
        this.omsLogisticService.updateDb();
    }

}
