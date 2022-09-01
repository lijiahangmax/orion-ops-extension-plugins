package com.orion.ops.machine.monitor.controller;

import com.orion.lang.define.wrapper.HttpWrapper;
import com.orion.lang.utils.collect.Lists;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.constant.MachineAlarmType;
import com.orion.ops.machine.monitor.constant.PropertiesConst;
import com.orion.ops.machine.monitor.entity.request.MachineSyncRequest;
import com.orion.ops.machine.monitor.handler.AlarmChecker;
import com.orion.ops.machine.monitor.metrics.collect.MetricsCollectTask;
import com.orion.ops.plugin.common.annotation.RestWrapper;
import com.orion.ops.plugin.common.handler.http.vo.MachineAlarmConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private AlarmChecker alarmChecker;

    @GetMapping("/ping")
    @ApiOperation(value = "检测心跳")
    public Integer ping() {
        return Const.ENABLE;
    }

    @GetMapping("/version")
    @ApiOperation(value = "获取版本")
    public String getVersion() {
        return PropertiesConst.AGENT_VERSION;
    }

    @GetMapping("/get-machine-id")
    @ApiOperation(value = "获取机器id")
    public Long getMachineId() {
        return PropertiesConst.MACHINE_ID;
    }

    @GetMapping("/set-machine-id")
    @ApiOperation(value = "设置机器id")
    public Long setMachineId(@RequestParam("machineId") Long machineId) {
        Long before = PropertiesConst.MACHINE_ID;
        PropertiesConst.MACHINE_ID = machineId;
        return before;
    }

    @PostMapping("/sync")
    @ApiOperation(value = "同步机器信息")
    public String setMachineId(@RequestBody MachineSyncRequest request) {
        // 设置机器id
        PropertiesConst.MACHINE_ID = request.getMachineId();
        // 设置报警配置
        List<MachineAlarmConfig> config = request.getAlarmConfig();
        if (!Lists.isEmpty(config)) {
            config.forEach(s -> alarmChecker.getConfig().put(MachineAlarmType.of(s.getType()), s));
        }
        return PropertiesConst.AGENT_VERSION;
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
