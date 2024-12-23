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
package cn.orionsec.ops.machine.monitor.entity.vo;

import cn.orionsec.kit.lang.utils.Systems;
import cn.orionsec.kit.spring.SpringHolder;
import cn.orionsec.ops.machine.monitor.constant.PropertiesConst;
import cn.orionsec.ops.machine.monitor.metrics.collect.MetricsCollectTask;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 获取基本监控信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/8/1 15:19
 */
@Data
@ApiModel(value = "获取基本监控信息")
public class BaseMetricsVO {

    @ApiModelProperty(value = "机器id")
    private Long machineId;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "监控状态")
    private Boolean status;

    @ApiModelProperty(value = "是否为windows系统")
    private Boolean isWindows;

    @ApiModelProperty(value = "系统信息")
    private OsInfoVO os;

    @ApiModelProperty(value = "系统负载")
    private SystemLoadVO load;

    @ApiModelProperty(value = "硬盘名称")
    private List<DiskStoreUsageVO> disks;

    @ApiModelProperty(value = "top进程信息")
    private List<SystemProcessVO> processes;

    public BaseMetricsVO() {
        this.machineId = PropertiesConst.MACHINE_ID;
        this.version = PropertiesConst.AGENT_VERSION;
        this.status = SpringHolder.getBean(MetricsCollectTask.class).isRun();
        this.isWindows = Systems.BE_WINDOWS;
    }

}
