package com.orion.ops.machine.monitor.metrics;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;

import java.util.List;

/**
 * 指标数据采集域
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 11:05
 */
@Component
@Getter
@Setter
public class MetricsCollectHolder {

    // TODO 优化为枚举

    /**
     * 上次采集处理器信息
     */
    private long[] prevCpu;

    /**
     * 上次采集处理器信息时间
     */
    private long prevCpuTime;

    /**
     * 上次采集内存信息时间
     */
    private long prevMemoryTime;

    /**
     * 上次采集网卡信息
     */
    private List<NetworkIF> prevNetworks;

    /**
     * 上次采集网卡信息时间
     */
    private long prevNetworksTime;

    /**
     * 上次采集磁盘信息
     */
    private List<HWDiskStore> prevDisks;

    /**
     * 上次采集磁盘信息时间
     */
    private long prevDisksTime;

}
