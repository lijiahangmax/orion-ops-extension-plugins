package com.orion.ops.machine.monitor.entity.agent.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内存使用率指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 13:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "内存使用率指标")
public class MemoryUsageBO extends BaseRangeBO {

    @ApiModelProperty(value = "使用率 usageRate")
    private Double ur;

    @ApiModelProperty(value = "使用大小 usageSize MB")
    private Long us;

}
