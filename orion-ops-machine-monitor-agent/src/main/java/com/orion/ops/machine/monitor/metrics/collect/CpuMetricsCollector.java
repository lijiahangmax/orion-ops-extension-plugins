package com.orion.ops.machine.monitor.metrics.collect;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.metrics.MetricsProvider;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.hardware.CentralProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 处理器指标收集器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 12:27
 */
@Slf4j
@Order(500)
@Component
public class CpuMetricsCollector implements IMetricsCollector<CpuUsingBO> {

    @Resource
    private MetricsProvider metricsProvider;

    private CentralProcessor processor;

    /**
     * 上次采集处理器信息
     */
    private long[] prevCpu;

    /**
     * 上次采集处理器信息时间
     */
    private long prevTime;

    @PostConstruct
    private void initCollector() {
        log.info("初始化处理器指标收集器");
        this.processor = metricsProvider.getHardware().getProcessor();
        this.prevCpu = processor.getSystemCpuLoadTicks();
        this.prevTime = System.currentTimeMillis();
    }

    @Override
    public CpuUsingBO collect() {
        long[] prevCpu = this.prevCpu;
        long prevTime = this.prevTime;
        long[] currentCpu = this.prevCpu = processor.getSystemCpuLoadTicks();
        long currentTime = this.prevTime = System.currentTimeMillis();
        // 计算
        CpuUsingBO cpu = new CpuUsingBO();
        cpu.setU(Utils.computeCpuLoad(prevCpu, currentCpu));
        cpu.setSr(prevTime);
        cpu.setEr(currentTime);
        log.debug("处理器指标: {}", JSON.toJSONString(cpu));
        // 拼接到天级数据
        String path = PathBuilders.getCpuDayDataPath(Utils.getRangeStartTime(cpu.getSr()));
        Utils.appendMetricsData(path, cpu);
        return cpu;
    }

}
