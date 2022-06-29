package com.orion.ops.machine.monitor.controller;

import com.orion.lang.wrapper.HttpWrapper;
import com.orion.ops.machine.monitor.collect.MetricsCollector;
import com.orion.ops.machine.monitor.entity.vo.OsInfoVO;
import com.orion.utils.convert.Converts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 机器监控指标 api
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 16:23
 */
@RestController
@RequestMapping("/orion/machine-monitor-agent/api/metrics")
public class MonitorMetricsController {

    @Resource
    private MetricsCollector metricsCollector;

    /**
     * 获取机器信息
     */
    @GetMapping("/info")
    public HttpWrapper<OsInfoVO> getOsInfo() {
        return HttpWrapper.ok(Converts.to(metricsCollector.getOsInfo(), OsInfoVO.class));
    }


}
