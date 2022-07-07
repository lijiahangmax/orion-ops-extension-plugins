package com.orion.ops.machine.monitor.entity.agent.vo;

import lombok.Data;

/**
 * cpu 监控指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:32
 */
@Data
public class CpuMetricsStatisticsVO implements BaseMetricsStatisticsEntity {

    /**
     * 使用率
     */
    private MetricsStatisticsVO<Double> usage;

}
