package com.orion.ops.machine.monitor.metrics;

import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.entity.bo.DiskIoUsingBO;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsingBO;
import com.orion.ops.machine.monitor.entity.bo.NetBandwidthBO;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 时级粒度 数据指标规约
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 18:23
 */
@Component
public class MetricsHourReducer {

    @Resource
    private MetricsCollectHolder metricsCollectHolder;

    /**
     * 规约处理器数据到小时粒度
     *
     * @param cpu cpu
     */
    public void reduceCpuData(CpuUsingBO cpu) {

    }

    /**
     * 规约内存数据到小时粒度
     *
     * @param memory memory
     */
    public void reduceMemoryData(MemoryUsingBO memory) {

    }

    /**
     * 规约网络数据到小时粒度
     *
     * @param net net
     */
    public void reduceNetData(NetBandwidthBO net) {

    }

    /**
     * 规约磁盘数据到小时粒度
     *
     * @param disk disk
     */
    public void reduceDiskData(DiskIoUsingBO disk) {

    }

}
