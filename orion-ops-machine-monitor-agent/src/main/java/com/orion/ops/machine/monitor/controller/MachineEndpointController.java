package com.orion.ops.machine.monitor.controller;

import com.orion.lang.wrapper.HttpWrapper;
import com.orion.ops.machine.monitor.annotation.RestWrapper;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.metrics.MetricsCollectScheduler;
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
@RestWrapper
@RestController
@RequestMapping("/orion/machine-monitor-agent/api/endpoint")
public class MachineEndpointController {

    @Resource
    private MetricsCollectScheduler metricsCollectScheduler;

    /**
     * 发送心跳
     */
    @GetMapping("/ping")
    public Integer ping() {
        return Const.ENABLE;
    }

    /**
     * 获取版本
     */
    @GetMapping("/version")
    public String getVersion() {
        return Const.VERSION;
    }

    /**
     * 获取监控启动状态
     */
    @GetMapping("/status")
    public Boolean getRunStatus() {
        return metricsCollectScheduler.getTask().isRun();
    }

    /**
     * 开启监控
     */
    @GetMapping("/start")
    public HttpWrapper<?> startMonitor() {
        metricsCollectScheduler.getTask().setRun(true);
        return HttpWrapper.ok();
    }

    /**
     * 停止监控
     */
    @GetMapping("/stop")
    public HttpWrapper<?> stopMonitor() {
        metricsCollectScheduler.getTask().setRun(false);
        return HttpWrapper.ok();
    }

}
