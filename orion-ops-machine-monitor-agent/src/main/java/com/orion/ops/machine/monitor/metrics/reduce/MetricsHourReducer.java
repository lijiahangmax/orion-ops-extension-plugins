package com.orion.ops.machine.monitor.metrics.reduce;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.entity.bo.*;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 时级粒度 数据指标规约
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 18:23
 */
@Slf4j
@Component
public class MetricsHourReducer {

    /**
     * 当前采集内存信息小时
     */
    public String currMemoryHour;

    /**
     * 当前采集网卡信息小时
     */
    public String currNetworkHour;

    /**
     * 当前采集磁盘信息小时
     */
    public String currDiskHour;


    private final List<MemoryUsingBO> currentMemoryMetrics;

    private final List<NetBandwidthBO> currentNetMetrics;

    private final List<DiskIoUsingBO> currentDiskMetrics;

    public MetricsHourReducer() {
        this.currentMemoryMetrics = Lists.newList();
        this.currentNetMetrics = Lists.newList();
        this.currentDiskMetrics = Lists.newList();
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
