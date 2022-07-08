package com.orion.ops.machine.monitor.controller;

import com.orion.lang.define.wrapper.HttpWrapper;
import com.orion.ops.machine.monitor.annotation.RestWrapper;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.metrics.collect.MetricsCollectTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 机器监控端点 api
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 19:00
 */
@Api(tags = "应用端点")
@RestWrapper
@RestController
@RequestMapping("/orion/machine-monitor-agent/api/endpoint")
public class MachineEndpointController {

    @Resource
    private MetricsCollectTask metricsCollectTask;

    @GetMapping("/ping")
    @ApiOperation(value = "检测心跳")
    public Integer ping() {
        return Const.ENABLE;
    }

    @GetMapping("/version")
    @ApiOperation(value = "获取版本")
    public String getVersion() {
        return Const.AGENT_VERSION;
    }

    @GetMapping("/status")
    @ApiOperation(value = "获取监控启动状态")
    public Boolean getRunStatus() {
        return metricsCollectTask.isRun();
    }

    @GetMapping("/start")
    @ApiOperation(value = "开启监控")
    public HttpWrapper<?> startMonitor() {
        metricsCollectTask.setRun(true);
        return HttpWrapper.ok();
    }

    @GetMapping("/stop")
    @ApiOperation(value = "停止监控")
    public HttpWrapper<?> stopMonitor() {
        metricsCollectTask.setRun(false);
        return HttpWrapper.ok();
    }

}
