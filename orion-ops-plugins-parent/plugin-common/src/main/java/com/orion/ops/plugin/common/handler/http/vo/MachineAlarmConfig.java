package com.orion.ops.plugin.common.handler.http.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 机器报警配置
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/9/1 10:38
 */
@Data
@ApiModel(value = "机器报警配置")
@SuppressWarnings("ALL")
public class MachineAlarmConfig {

    /**
     * @see com.orion.ops.machine.monitor.constant.MachineAlarmType
     */
    @ApiModelProperty(value = "报警类型 10: cpu使用率 20: 内存使用率")
    private Integer type;

    @ApiModelProperty(value = "报警阈值")
    private Double alarmThreshold;

    @ApiModelProperty(value = "触发报警阈值 次")
    private Integer triggerThreshold;

    @ApiModelProperty(value = "报警通知沉默时间 分")
    private Integer notifySilence;

}
