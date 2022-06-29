package com.orion.ops.machine.monitor.collect;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 机器指标采集任务
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 14:50
 */
@Slf4j
public class MetricsCollectTask implements Runnable {

    private Long machineId;

    private MetricsCollector metricsCollector;

    private AtomicInteger counter;

    public MetricsCollectTask(Long machineId, MetricsCollector metricsCollector) {
        this.machineId = machineId;
        this.metricsCollector = metricsCollector;
        this.counter = new AtomicInteger();
    }

    @Override
    public void run() {

    }

}
