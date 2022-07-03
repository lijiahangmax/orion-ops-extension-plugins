package com.orion.ops.machine.monitor.metrics;

import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.entity.bo.DiskIoUsingBO;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsingBO;
import com.orion.ops.machine.monitor.entity.bo.NetBandwidthBO;
import com.orion.ops.machine.monitor.metrics.reduce.MetricsHourReduceCalculator;
import com.orion.ops.machine.monitor.metrics.reduce.MetricsHourReducer;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.time.Dates;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 机器指标采集任务
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 14:50
 */
@Slf4j
@Component
public class MetricsCollectTask implements Runnable {

    @Resource
    private MetricsCollector metricsCollector;

    @Resource
    private MetricsHourReducer metricsHourReducer;

    /**
     * 计数器
     */
    private final AtomicInteger counter;

    @Getter
    @Setter
    private volatile boolean run;

    public MetricsCollectTask() {
        this.run = true;
        this.counter = new AtomicInteger();
    }

    // TODO 检查监控指标 push
    // TODO pushApi Component

    @Override
    public void run() {
        if (!run) {
            return;
        }
        int seq = counter.incrementAndGet();
        log.info("第 {} 次采集数据-开始 {}", seq, Dates.current());
        // 采集处理器数据
        this.collectCpuData();
        // 采集内存数据
        this.collectMemoryData();
        // 采集网络带宽数据
        this.collectNetData();
        // 采集磁盘读写数据
        this.collectDiskData();
        log.info("第 {} 次采集数据-结束 {}", seq, Dates.current());
    }

    /**
     * 采集处理器数据
     */
    private void collectCpuData() {
        CpuUsingBO cpu = metricsCollector.collectCpu();
        // 拼接到天级数据
        String path = PathBuilders.getCpuDayDataPath(Utils.getRangeStartTime(cpu.getSr()));
        Utils.appendMetricsData(path, cpu);
        // 规约小时数据粒度
        MetricsHourReduceCalculator.CPU.getReduceResolverBean().reduce(cpu);
    }

    /**
     * 采集内存数据
     */
    private void collectMemoryData() {
        MemoryUsingBO mem = metricsCollector.collectMemory();
        // 拼接到天级数据
        String path = PathBuilders.getMemoryDayDataPath(Utils.getRangeStartTime(mem.getSr()));
        Utils.appendMetricsData(path, mem);
        // 规约小时数据粒度
        MetricsHourReduceCalculator.MEMORY.getReduceResolverBean().reduce(mem);
    }

    /**
     * 采集网络带宽数据
     */
    private void collectNetData() {
        NetBandwidthBO net = metricsCollector.collectNetBandwidth();
        // 拼接到天级数据
        String path = PathBuilders.getNetDayDataPath(Utils.getRangeStartTime(net.getSr()));
        Utils.appendMetricsData(path, net);
        // 规约小时数据粒度
        metricsHourReducer.reduceNetData(net);
    }

    /**
     * 采集磁盘读写数据
     */
    private void collectDiskData() {
        List<DiskIoUsingBO> disks = metricsCollector.collectDiskIo();
        for (DiskIoUsingBO disk : disks) {
            // 拼接到天级数据
            String path = PathBuilders.getDiskDayDataPath(Utils.getRangeStartTime(disk.getSr()), disk.getSeq());
            Utils.appendMetricsData(path, disk);
            // 规约小时数据粒度
            metricsHourReducer.reduceDiskData(disk);
        }
    }

}
