package com.orion.ops.machine.monitor.entity.agent.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * cpu 使用存储指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 18:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "cpu使用存储指标")
public class CpuUsageBO extends BaseRangeBO {

    @ApiModelProperty(value = "使用率 usage")
    private Double u;

}
