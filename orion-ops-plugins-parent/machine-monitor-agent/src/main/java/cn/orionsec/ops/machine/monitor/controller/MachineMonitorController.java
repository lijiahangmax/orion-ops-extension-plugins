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
package cn.orionsec.ops.machine.monitor.controller;

import cn.orionsec.kit.lang.utils.Strings;
import cn.orionsec.kit.lang.utils.Valid;
import cn.orionsec.ops.machine.monitor.constant.Currents;
import cn.orionsec.ops.machine.monitor.constant.GranularityType;
import cn.orionsec.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import cn.orionsec.ops.machine.monitor.entity.vo.CpuMetricsStatisticsVO;
import cn.orionsec.ops.machine.monitor.entity.vo.DiskMetricsStatisticVO;
import cn.orionsec.ops.machine.monitor.entity.vo.MemoryMetricsStatisticsVO;
import cn.orionsec.ops.machine.monitor.entity.vo.NetBandwidthMetricsStatisticVO;
import cn.orionsec.ops.machine.monitor.metrics.MetricsProvider;
import cn.orionsec.ops.machine.monitor.metrics.statistics.CpuMetricsStatisticResolver;
import cn.orionsec.ops.machine.monitor.metrics.statistics.DiskMetricsStatisticResolver;
import cn.orionsec.ops.machine.monitor.metrics.statistics.MemoryMetricsStatisticResolver;
import cn.orionsec.ops.machine.monitor.metrics.statistics.NetMetricsStatisticResolver;
import cn.orionsec.ops.plugin.common.annotation.RestWrapper;
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

    @PostMapping("/cpu")
    @ApiOperation(value = "获取cpu数据")
    public CpuMetricsStatisticsVO getCpuData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        // 统计
        CpuMetricsStatisticResolver resolver = new CpuMetricsStatisticResolver(request);
        resolver.statistics();
        return resolver.getMetrics();
    }

    @PostMapping("/memory")
    @ApiOperation(value = "获取内存数据")
    public MemoryMetricsStatisticsVO getMemoryData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        // 统计
        MemoryMetricsStatisticResolver resolver = new MemoryMetricsStatisticResolver(request);
        resolver.statistics();
        return resolver.getMetrics();
    }

    @PostMapping("/net")
    @ApiOperation(value = "获取网络数据")
    public NetBandwidthMetricsStatisticVO getNetBandwidthData(@RequestBody MetricsStatisticsRequest request) {
        this.validRequest(request);
        // 统计
        NetMetricsStatisticResolver resolver = new NetMetricsStatisticResolver(request);
        resolver.statistics();
        return resolver.getMetrics();
    }

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
