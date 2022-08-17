package com.orion.ops.machine.monitor.metrics.collect;

import com.orion.ops.machine.monitor.constant.SchedulerPools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 机器指标采集调度器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 14:40
 */
@Slf4j
@Order(600)
@Component
public class MetricsCollectScheduler {

    @Value("${collect.period.second}")
    private Integer collectPeriodSecond;

    @Resource
    private MetricsCollectTask metricsCollectTask;

    @PostConstruct
    private void initScheduler() {
        log.info("初始化数据采集调度器 采集周期: {}s", collectPeriodSecond);
        SchedulerPools.COLLECT_SCHEDULER.scheduleAtFixedRate(metricsCollectTask, collectPeriodSecond, collectPeriodSecond, TimeUnit.SECONDS);
    }

}
