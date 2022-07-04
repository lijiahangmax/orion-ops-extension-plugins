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



    //
    //   近一小时 1分钟粒度
    // 近24小时 1分钟粒度 1小时粒度
    // 近30天	 1小时粒度 1天粒度
    // 自定义
    // 	大于24h只能 小时粒度 1天粒度
    // 	大于2m 只能 天级粒度
    //
    //
    // CPU 使用率 3位小数
    //
    // 内存使用量 MB avg 3位小数
    // 内存使用率 3位小数
    //
    // 入网带宽 MPB 5位小数
    // 出网带宽 MPB 5位小数
    // 入网包数 avg 3位小数
    // 出网包数 avg 3位小数
    //
    // 硬盘读KB 3位小数
    // 硬盘写KB 3位小数
    // 硬盘读次数 avg 3位小数
    // 硬盘写次数 avg 3位小数
    // IO 使用时间 MS avg 3位小数


}
