package com.orion.ops.machine.monitor.entity.vo;

import lombok.Data;

/**
 * 监控指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 17:53
 */
@Data
public class DiskMetricsStatisticVO implements BaseMetricsStatisticsEntity {

    /**
     * 硬盘写速度 mb/s
     */
    private MetricsStatisticsVO<Double> readSpeed;

    /**
     * 硬盘读速度 mb/s
     */
    private MetricsStatisticsVO<Double> writeSpeed;

    /**
     * 硬盘读次数
     */
    private MetricsStatisticsVO<Long> readCount;

    /**
     * 硬盘写次数
     */
    private MetricsStatisticsVO<Long> writeCount;

    /**
     * 使用时间
     */
    private MetricsStatisticsVO<Long> usageTime;

}
