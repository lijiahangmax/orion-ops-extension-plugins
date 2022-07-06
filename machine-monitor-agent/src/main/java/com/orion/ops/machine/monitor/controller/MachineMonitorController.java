package com.orion.ops.machine.monitor.controller;

import com.orion.ops.machine.monitor.annotation.IgnoreLog;
import com.orion.ops.machine.monitor.annotation.RestWrapper;
import com.orion.ops.machine.monitor.constant.Currents;
import com.orion.ops.machine.monitor.constant.GranularityType;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.CpuMetricsStatisticsVO;
import com.orion.ops.machine.monitor.entity.vo.DiskMetricsStatisticVO;
import com.orion.ops.machine.monitor.entity.vo.MemoryMetricsStatisticsVO;
import com.orion.ops.machine.monitor.entity.vo.NetBandwidthMetricsStatisticVO;
import com.orion.ops.machine.monitor.metrics.statistics.CpuMetricsStatisticResolver;
import com.orion.ops.machine.monitor.metrics.statistics.DiskMetricsStatisticResolver;
import com.orion.ops.machine.monitor.metrics.statistics.MemoryMetricsStatisticResolver;
import com.orion.ops.machine.monitor.metrics.statistics.NetMetricsStatisticResolver;
import com.orion.utils.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机器监控 api
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 16:59
 */
@RestWrapper
@RestController
@RequestMapping("/orion/machine-monitor-agent/api/monitor-statistic")
public class MachineMonitorController {

    /**
     * 获取处理器数据
     */
    @IgnoreLog
    @PostMapping("/get-cpu")
    public CpuMetricsStatisticsVO getCpuData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        // 统计
        CpuMetricsStatisticResolver resolver = new CpuMetricsStatisticResolver(request);
        resolver.statistics();
        return resolver.getMetrics();
    }

    /**
     * 获取内存数据
     */
    @IgnoreLog
    @PostMapping("/get-memory")
    public MemoryMetricsStatisticsVO getMemoryData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        // 统计
        MemoryMetricsStatisticResolver resolver = new MemoryMetricsStatisticResolver(request);
        resolver.statistics();
        return resolver.getMetrics();
    }

    /**
     * 获取网络数据
     */
    @IgnoreLog
    @PostMapping("/get-net")
    public NetBandwidthMetricsStatisticVO getNetBandwidthData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        // 统计
        NetMetricsStatisticResolver resolver = new NetMetricsStatisticResolver(request);
        resolver.statistics();
        return resolver.getMetrics();
    }

    /**
     * 获取磁盘数据
     */
    @IgnoreLog
    @PostMapping("/get-disk")
    public DiskMetricsStatisticVO getDiskData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        Currents.setDiskSeq(request.getSeq());
        try {
            // 统计
            DiskMetricsStatisticResolver resolver = new DiskMetricsStatisticResolver(request);
            resolver.statistics();
            return resolver.getMetrics();
        } finally {
            Currents.removeDiskSeq();
        }
    }

    /**
     * 检查请求参数
     *
     * @param request 请求参数
     */
    private void validRequest(MetricsStatisticsRequest request) {
        Long start = Valid.notNull(request.getStartRange());
        Long end = Valid.notNull(request.getEndRange());
        Valid.gt(end, start);
        Valid.notNull(GranularityType.of(request.getGranularity()));
    }

}
