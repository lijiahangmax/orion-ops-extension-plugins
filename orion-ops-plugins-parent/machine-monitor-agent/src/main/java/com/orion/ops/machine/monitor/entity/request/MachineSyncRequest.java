package com.orion.ops.machine.monitor.entity.request;

import com.orion.ops.plugin.common.handler.http.vo.MachineAlarmConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 机器同步请求
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/9/1 14:30
 */
@Data
@ApiModel(value = "机器同步请求")
public class MachineSyncRequest {

    @ApiModelProperty(value = "机器id")
    private Long machineId;

    @ApiModelProperty(value = "机器报警配置")
    private List<MachineAlarmConfig> alarmConfig;

}
