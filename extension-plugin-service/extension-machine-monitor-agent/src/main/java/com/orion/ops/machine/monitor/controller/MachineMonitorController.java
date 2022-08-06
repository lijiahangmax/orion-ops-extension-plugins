package com.orion.ops.machine.monitor.controller;

import com.orion.lang.utils.Strings;
import com.orion.lang.utils.Valid;
import com.orion.ops.machine.monitor.constant.Currents;
import com.orion.ops.machine.monitor.constant.GranularityType;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.CpuMetricsStatisticsVO;
import com.orion.ops.machine.monitor.entity.vo.DiskMetricsStatisticVO;
import com.orion.ops.machine.monitor.entity.vo.MemoryMetricsStatisticsVO;
import com.orion.ops.machine.monitor.entity.vo.NetBandwidthMetricsStatisticVO;
import com.orion.ops.machine.monitor.metrics.MetricsProvider;
import com.orion.ops.machine.monitor.metrics.statistics.CpuMetricsStatisticResolver;
import com.orion.ops.machine.monitor.metrics.statistics.DiskMetricsStatisticResolver;
import com.orion.ops.machine.monitor.metrics.statistics.MemoryMetricsStatisticResolver;
import com.orion.ops.machine.monitor.metrics.statistics.NetMetricsStatisticResolver;
import com.orion.ops.plugin.common.annotation.IgnoreLog;
import com.orion.ops.plugin.common.annotation.RestWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 监控统计 api
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 16:59
 */
@Api(tags = "监控统计")
@RestWrapper
@RestController
@RequestMapping("/orion/machine-monitor-agent/api/monitor-statistic")
public class MachineMonitorController {

    @Resource
    private MetricsProvider metricsProvider;

    @IgnoreLog
    @PostMapping("/cpu")
    @ApiOperation(value = "获取cpu数据")
    public CpuMetricsStatisticsVO getCpuData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        // 统计
        CpuMetricsStatisticResolver resolver = new CpuMetricsStatisticResolver(request);
        resolver.statistics();
        return resolver.getMetrics();
    }

    @IgnoreLog
    @PostMapping("/memory")
    @ApiOperation(value = "获取内存数据")
    public MemoryMetricsStatisticsVO getMemoryData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        // 统计
        MemoryMetricsStatisticResolver resolver = new MemoryMetricsStatisticResolver(request);
        resolver.statistics();
        return resolver.getMetrics();
    }

    @IgnoreLog
    @PostMapping("/net")
    @ApiOperation(value = "获取网络数据")
    public NetBandwidthMetricsStatisticVO getNetBandwidthData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        // 统计
        NetMetricsStatisticResolver resolver = new NetMetricsStatisticResolver(request);
        resolver.statistics();
        return resolver.getMetrics();
    }

    @IgnoreLog
    @PostMapping("/disk")
    @ApiOperation(value = "获取磁盘数据")
    public DiskMetricsStatisticVO getDiskData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        String seq = request.getSeq();
        if (Strings.isBlank(seq)) {
            seq = metricsProvider.getDiskName().get(0).getSeq();
        }
        Currents.setDiskSeq(seq);
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
