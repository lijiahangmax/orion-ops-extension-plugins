package com.orion.ops.plugin.common.entity.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统负载信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/27 18:43
 */
@Data
@ApiModel(value = "系统负载信息")
public class SystemLoadDTO {

    @ApiModelProperty(value = "系统1分钟负载")
    private Double oneMinuteLoad;

    @ApiModelProperty(value = "系统5分钟负载")
    private Double fiveMinuteLoad;

    @ApiModelProperty(value = "系统15分钟负载")
    private Double fifteenMinuteLoad;

}
