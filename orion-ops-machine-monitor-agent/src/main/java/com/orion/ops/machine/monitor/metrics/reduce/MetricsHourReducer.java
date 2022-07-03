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
     * 当前采集处理器信息小时
     */
    public String currentCpuHour;

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

    private final List<CpuUsingBO> currentCpuMetrics;

    private final List<MemoryUsingBO> currentMemoryMetrics;

    private final List<NetBandwidthBO> currentNetMetrics;

    private final List<DiskIoUsingBO> currentDiskMetrics;

    public MetricsHourReducer() {
        this.currentCpuMetrics = Lists.newList();
        this.currentMemoryMetrics = Lists.newList();
        this.currentNetMetrics = Lists.newList();
        this.currentDiskMetrics = Lists.newList();
    }

    /**
     * 规约处理器数据到小时粒度
     *
     * @param cpu cpu
     */
    public void reduceCpuData(CpuUsingBO cpu) {
        String currentHour = Utils.getRangeStartHour(cpu);
        if (currentCpuHour == null) {
            currentCpuHour = currentHour;
        }
        // 同一时间
        if (currentHour.equals(currentCpuHour)) {
            currentCpuMetrics.add(cpu);
            return;
        }
        // 不同时间则规约
        String prevHour = this.currentCpuHour;
        this.currentCpuHour = currentHour;
        double max = currentCpuMetrics.stream()
                .mapToDouble(CpuUsingBO::getU)
                .max()
                .orElse(Const.D_0);
        double min = currentCpuMetrics.stream()
                .mapToDouble(CpuUsingBO::getU)
                .min()
                .orElse(Const.D_0);
        double avg = currentCpuMetrics.stream()
                .mapToDouble(CpuUsingBO::getU)
                .average()
                .orElse(Const.D_0);
        currentCpuMetrics.clear();
        currentCpuMetrics.add(cpu);
        // 设置规约数据
        CpuUsingHourReduceBO reduce = new CpuUsingHourReduceBO();
        reduce.setMax(Utils.roundToDouble(max, 3));
        reduce.setMin(Utils.roundToDouble(min, 3));
        reduce.setAvg(Utils.roundToDouble(avg, 3));
        Utils.setReduceHourRange(reduce, prevHour, currentHour);
        log.info("计算处理器小时级指标: {}", JSON.toJSONString(reduce));
        // 拼接到月级数据
        String path = PathBuilders.getCpuMonthDataPath(Utils.getRangeStartMonth(prevHour));
        Utils.appendMetricsData(path, reduce);
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
