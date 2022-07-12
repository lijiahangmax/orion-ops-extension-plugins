package com.orion.ops.monitor.common.entity.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内存使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/27 18:09
 */
@Data
@ApiModel(value = "内存使用信息")
public class MemoryUsageDTO {

    @ApiModelProperty(value = "总内存")
    private Long totalMemory;

    @ApiModelProperty(value = "使用内存")
    private Long usageMemory;

    @ApiModelProperty(value = "空闲内存")
    private Long freeMemory;

    @ApiModelProperty(value = "内存使用率")
    private Double usage;

}
