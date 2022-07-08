package com.orion.ops.machine.monitor.entity.agent.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * cpu 监控指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:32
 */
@Data
@ApiModel(value = "cpu监控指标统计数据")
public class CpuMetricsStatisticsVO implements BaseMetricsStatisticsEntity {

    @ApiModelProperty(value = "使用率")
    private MetricsStatisticsVO<Double> usage;

}
