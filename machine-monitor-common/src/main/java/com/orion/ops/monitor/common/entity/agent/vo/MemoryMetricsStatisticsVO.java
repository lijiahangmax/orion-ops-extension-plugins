package com.orion.ops.monitor.common.entity.agent.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内存监控指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 14:15
 */
@Data
@ApiModel(value = "内存监控指标统计数据")
public class MemoryMetricsStatisticsVO implements BaseMetricsStatisticsEntity {

    @ApiModelProperty(value = "使用量")
    private MetricsStatisticsVO<Double> size;

    @ApiModelProperty(value = "使用率")
    private MetricsStatisticsVO<Double> usage;

}
