package com.orion.ops.machine.monitor.entity.agent.vo;

import lombok.Data;

/**
 * 网络指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 15:35
 */
@Data
public class NetBandwidthMetricsStatisticVO implements BaseMetricsStatisticsEntity {

    /**
     * 上行sud mpb/s
     */
    private MetricsStatisticsVO<Double> sentSpeed;

    /**
     * 下行速率 mpb/s
     */
    private MetricsStatisticsVO<Double> recvSpeed;

    /**
     * 上行包数
     */
    private MetricsStatisticsVO<Long> sentPacket;

    /**
     * 下行包数
     */
    private MetricsStatisticsVO<Long> recvPacket;

}
