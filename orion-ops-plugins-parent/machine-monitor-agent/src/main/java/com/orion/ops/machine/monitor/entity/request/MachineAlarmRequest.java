package com.orion.ops.machine.monitor.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 机器报警请求
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/9/1 18:22
 */
@Data
@ApiModel(value = "机器报警请求")
public class MachineAlarmRequest {

    @ApiModelProperty(value = "机器id")
    private Long machineId;

    /**
     * @see com.orion.ops.machine.monitor.constant.MachineAlarmType
     */
    @ApiModelProperty(value = "报警类型 10: cpu使用率 20: 内存使用率")
    private Integer type;

    @ApiModelProperty(value = "报警值")
    private Double alarmValue;

    @ApiModelProperty(value = "报警时间")
    private Date alarmTime;

}
