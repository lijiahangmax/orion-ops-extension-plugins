package com.orion.ops.machine.monitor.collect;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;

import java.util.List;

/**
 * 指标数据存储
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 11:05
 */
@Component
@Getter
@Setter
public class MetricsHolder {

    /**
     * 当前 cpu 使用情况
     */
    private long[] currentCpuLoadTicks;

    /**
     * 当前 cpu 核心使用情况
     */
    private long[][] currentProcTicks;

    /**
     * 当前网卡信息
     */
    private List<NetworkIF> currentNetwork;

    /**
     * 当前磁盘信息
     */
    private List<HWDiskStore> currentDisk;

}
