package com.orion.ops.machine.monitor.metrics;

import com.alibaba.fastjson.JSON;
import com.orion.constant.Letters;
import com.orion.ops.machine.monitor.entity.bo.*;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.io.Files1;
import com.orion.utils.time.Dates;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
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
    private final Long machineId;

    /**
     * 采集器
     */
    private final MetricsCollector metricsCollector;

    /**
     * 计数器
     */
    private final AtomicInteger counter;

    @Getter
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
        log.info("处理器指标: {}", JSON.toJSONString(cpu));
        String path = PathBuilders.getCpuDataPath(Utils.getRangeStartTime(cpu.getSr()));
        this.appendMetricsData(path, cpu);
    }

    /**
     * 采集内存数据
     */
    private void collectMemoryData() {
        MemoryUsingBO mem = metricsCollector.collectMemory();
        log.info("内存指标: {}", JSON.toJSONString(mem));
        String path = PathBuilders.getMemoryDataPath(Utils.getRangeStartTime(mem.getSr()));
        this.appendMetricsData(path, mem);
    }

    /**
     * 采集网络带宽数据
     */
    private void collectNetData() {
        NetBandwidthBO net = metricsCollector.collectNetBandwidth();
        log.info("网络带宽指标: {}", JSON.toJSONString(net));
        String path = PathBuilders.getNetDataPath(Utils.getRangeStartTime(net.getSr()));
        this.appendMetricsData(path, net);
    }

    /**
     * 采集磁盘读写数据
     */
    private void collectDiskData() {
        Map<String, DiskIoUsingBO> disks = metricsCollector.collectDiskIo();
        log.info("磁盘读写指标: {}", JSON.toJSONString(disks));
        disks.forEach((k, v) -> {
            String path = PathBuilders.getDiskDataPath(k, Utils.getRangeStartTime(v.getSr()));
            this.appendMetricsData(path, v);
        });
    }

    /**
     * 拼接数据
     *
     * @param path path
     * @param data data
     * @param <T>  data type
     */
    private <T extends BaseRangeBO> void appendMetricsData(String path, T data) {
        // FIXME 升级KIT后需要把这个改为 fast
        try (OutputStream out = Files1.openOutputStream(path, true)) {
            out.write(JSON.toJSONBytes(data));
            out.write(Letters.LF);
            out.flush();
        } catch (IOException e) {
            log.error("数据持久化失败", e);
        }
    }

}
