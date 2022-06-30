package com.orion.ops.machine.monitor.collect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 机器指标采集器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 18:31
 */
@Slf4j
@Order(410)
@Component
public class MetricsCollector {

    @Resource
    private MetricsProvider metricsProvider;

    @Resource
    private MetricsHolder metricsHolder;

    /**
     * 硬件
     */
    private HardwareAbstractionLayer hardware;

    /**
     * 系统
     */
    private OperatingSystem os;

    @PostConstruct
    public void initCollector() {
        log.info("初始化数据采集器");
        // 设置数据集
        this.hardware = metricsProvider.getHardware();
        this.os = metricsProvider.getOs();
        // 设置原始数据
        CentralProcessor processor = hardware.getProcessor();
        metricsHolder.setCurrentCpuLoadTicks(processor.getSystemCpuLoadTicks());
        metricsHolder.setCurrentNetwork(hardware.getNetworkIFs());
        metricsHolder.setCurrentDisk(hardware.getDiskStores());
    }

}
