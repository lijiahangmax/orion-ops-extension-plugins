package com.orion.ops.machine.monitor.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 网络指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 15:35
 */
@Data
@ApiModel(value = "网络指标统计数据")
public class NetBandwidthMetricsStatisticVO implements BaseMetricsStatisticsEntity {

    @ApiModelProperty(value = "上行速率 mpb/s")
    private MetricsStatisticsVO<Double> sentSpeed;

    @ApiModelProperty(value = "下行速率 mpb/s")
    private MetricsStatisticsVO<Double> recvSpeed;

    @ApiModelProperty(value = "上行包数")
    private MetricsStatisticsVO<Long> sentPacket;

    @ApiModelProperty(value = "下行包数")
    private MetricsStatisticsVO<Long> recvPacket;

}
