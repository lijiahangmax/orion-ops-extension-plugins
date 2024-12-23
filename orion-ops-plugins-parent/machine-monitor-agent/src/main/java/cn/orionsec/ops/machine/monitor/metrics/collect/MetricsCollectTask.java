/*
 * Copyright (c) 2021 - present Jiahang Li, All rights reserved.
 *
 *   https://om.orionsec.cn
 *
 * Members:
 *   Jiahang Li - ljh1553488six@139.com - author
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.orionsec.ops.machine.monitor.metrics.collect;

import cn.orionsec.kit.lang.utils.time.Dates;
import cn.orionsec.ops.machine.monitor.constant.MachineAlarmType;
import cn.orionsec.ops.machine.monitor.entity.bo.CpuUsageBO;
import cn.orionsec.ops.machine.monitor.entity.bo.DiskIoUsageBO;
import cn.orionsec.ops.machine.monitor.entity.bo.MemoryUsageBO;
import cn.orionsec.ops.machine.monitor.entity.bo.NetBandwidthBO;
import cn.orionsec.ops.machine.monitor.handler.AlarmChecker;
import cn.orionsec.ops.machine.monitor.metrics.reduce.MetricsHourReduceCalculator;
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

    /**
     * 计数器
     */
    private final AtomicInteger counter;

    @Resource
    private AlarmChecker alarmChecker;

    @Getter
    @Setter
    private volatile boolean run;

    public MetricsCollectTask() {
        this.run = true;
        this.counter = new AtomicInteger();
    }

    @Override
    public void run() {
        if (!run) {
            return;
        }
        int seq = counter.incrementAndGet();
        log.info("第 {} 次采集数据-开始 {}", seq, Dates.current());
        try {
            // 采集处理器数据
            this.collectCpuData();
            // 采集内存数据
            this.collectMemoryData();
            // 采集网络带宽数据
            this.collectNetData();
            // 采集硬盘读写数据
            this.collectDiskData();
            log.info("第 {} 次采集数据-结束 {}", seq, Dates.current());
        } catch (Exception e) {
            log.error("第 {} 次采集数据-异常 {}", seq, Dates.current(), e);
        }
    }

    /**
     * 采集处理器数据
     */
    private void collectCpuData() {
        // 采集处理器数据
        CpuUsageBO cpu = ((CpuMetricsCollector) MetricsCollectorType.CPU.getCollectBean()).collect();
        // 规约小时数据粒度
        MetricsHourReduceCalculator.CPU.getReduceResolverBean().reduce(cpu);
        // 检测报警
        alarmChecker.check(MachineAlarmType.CPU_USAGE, cpu.getU());
    }

    /**
     * 采集内存数据
     */
    private void collectMemoryData() {
        // 采集内存数据
        MemoryUsageBO mem = ((MemoryMetricsCollector) MetricsCollectorType.MEMORY.getCollectBean()).collect();
        // 规约小时数据粒度
        MetricsHourReduceCalculator.MEMORY.getReduceResolverBean().reduce(mem);
        // 检测报警
        alarmChecker.check(MachineAlarmType.MEMORY_USAGE, mem.getUr());
    }

    /**
     * 采集网络带宽数据
     */
    private void collectNetData() {
        // 采集网络带宽数据
        NetBandwidthBO net = ((NetBandwidthCollector) MetricsCollectorType.NET.getCollectBean()).collect();
        // 规约小时数据粒度
        MetricsHourReduceCalculator.NET.getReduceResolverBean().reduce(net);
    }

    /**
     * 采集硬盘读写数据
     */
    private void collectDiskData() {
        // 采集硬盘数据
        List<DiskIoUsageBO> disks = ((DiskMetricsCollector) MetricsCollectorType.DISK.getCollectBean()).collectAsList();
        for (DiskIoUsageBO disk : disks) {
            // 规约小时数据粒度
            MetricsHourReduceCalculator.DISK.getReduceResolverBean().reduce(disk);
        }
    }

}
