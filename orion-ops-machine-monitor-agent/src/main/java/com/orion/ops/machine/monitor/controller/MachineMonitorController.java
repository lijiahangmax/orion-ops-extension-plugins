package com.orion.ops.machine.monitor.controller;

import com.orion.ops.machine.monitor.annotation.RestWrapper;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.CpuMetricsStatisticsVO;
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
@RequestMapping("/orion/machine-monitor-agent/api/monitor")
public class MachineMonitorController {

    /**
     * 获取处理器数据
     */
    @PostMapping("/get-cpu")
    public CpuMetricsStatisticsVO getCpuData(@RequestBody MetricsStatisticsRequest request) {

        return null;
    }

    /**
     * 获取内存数据
     */
    @PostMapping("/get-memory")
    public void getMemoryData(@RequestBody MetricsStatisticsRequest request) {

    }



}
