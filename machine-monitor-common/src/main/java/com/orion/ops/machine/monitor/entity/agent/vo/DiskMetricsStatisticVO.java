package com.orion.ops.machine.monitor.entity.agent.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 监控指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 17:53
 */
@Data
@ApiModel(value = "监控指标统计数据")
public class DiskMetricsStatisticVO implements BaseMetricsStatisticsEntity {

    @ApiModelProperty(value = "硬盘写速度 mb/s")
    private MetricsStatisticsVO<Double> readSpeed;

    @ApiModelProperty(value = "硬盘读速度 mb/s")
    private MetricsStatisticsVO<Double> writeSpeed;

    @ApiModelProperty(value = "硬盘读次数")
    private MetricsStatisticsVO<Long> readCount;

    @ApiModelProperty(value = "硬盘写次数")
    private MetricsStatisticsVO<Long> writeCount;

    @ApiModelProperty(value = "使用时间")
    private MetricsStatisticsVO<Long> usageTime;

}
