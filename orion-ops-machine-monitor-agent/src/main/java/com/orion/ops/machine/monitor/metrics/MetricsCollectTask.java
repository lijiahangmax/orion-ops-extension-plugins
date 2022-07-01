package com.orion.ops.machine.monitor.metrics;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsingBO;
import com.orion.ops.machine.monitor.entity.bo.NetBandwidthBO;
import com.orion.utils.time.Dates;
import lombok.Setter;
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

    /**
     * 机器id
     */
    private Long machineId;

    /**
     * 采集器
     */
    private MetricsCollector metricsCollector;

    /**
     * 计数器
     */
    private AtomicInteger counter;

    @Setter
    private volatile boolean run;

    public MetricsCollectTask(Long machineId, MetricsCollector metricsCollector) {
        this.run = true;
        this.machineId = machineId;
        this.metricsCollector = metricsCollector;
        this.counter = new AtomicInteger();
    }

    @Override
    public void run() {
        if (!run) {
            return;
        }
        int seq = counter.incrementAndGet();
        log.info("第 {} 次采集数据-开始 {}", seq, Dates.current());
        CpuUsingBO cpu = metricsCollector.collectCpu();
        log.info("处理器指标: {}", JSON.toJSONString(cpu));
        MemoryUsingBO mem = metricsCollector.collectMemory();
        log.info("内存指标: {}", JSON.toJSONString(mem));
        NetBandwidthBO net = metricsCollector.collectNetBandwidth();
        log.info("网络带宽指标: {}", JSON.toJSONString(net));
        log.info("第 {} 次采集数据-结束 {}", seq, Dates.current());
    }

}
