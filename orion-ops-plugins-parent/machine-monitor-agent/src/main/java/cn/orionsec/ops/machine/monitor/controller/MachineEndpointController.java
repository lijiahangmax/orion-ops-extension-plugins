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

import cn.orionsec.kit.lang.define.wrapper.HttpWrapper;
import cn.orionsec.kit.lang.utils.collect.Lists;
import cn.orionsec.ops.machine.monitor.constant.MachineAlarmType;
import cn.orionsec.ops.machine.monitor.constant.PropertiesConst;
import cn.orionsec.ops.machine.monitor.entity.request.MachineSyncRequest;
import cn.orionsec.ops.machine.monitor.handler.AlarmChecker;
import cn.orionsec.ops.machine.monitor.metrics.collect.MetricsCollectTask;
import cn.orionsec.ops.plugin.common.annotation.RestWrapper;
import cn.orionsec.ops.plugin.common.handler.http.vo.MachineAlarmConfig;
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
        return 1;
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
    public String syncMachineInfo(@RequestBody MachineSyncRequest request) {
        // 设置机器id
        PropertiesConst.MACHINE_ID = request.getMachineId();
        // 设置报警配置
        List<MachineAlarmConfig> config = request.getAlarmConfig();
        if (!Lists.isEmpty(config)) {
            config.forEach(s -> alarmChecker.getAlarmContext().put(MachineAlarmType.of(s.getType()), s));
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
