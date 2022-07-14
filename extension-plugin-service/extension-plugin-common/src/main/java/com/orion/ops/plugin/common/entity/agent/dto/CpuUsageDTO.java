package com.orion.ops.plugin.common.entity.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * cpu 使用率
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/27 17:53
 */
@Data
@ApiModel(value = "cpu使用率")
public class CpuUsageDTO {

    @ApiModelProperty(value = "使用率")
    private Double usage;

    @ApiModelProperty(value = "核心使用率")
    private List<Double> coreUsage;

}
