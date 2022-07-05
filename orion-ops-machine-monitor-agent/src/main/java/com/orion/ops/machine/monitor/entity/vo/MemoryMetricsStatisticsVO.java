package com.orion.ops.machine.monitor.entity.vo;

import lombok.Data;

/**
 * 内存监控指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 14:15
 */
@Data
public class MemoryMetricsStatisticsVO implements BaseMetricsStatisticsVO {

    /**
     * 使用量
     */
    private MemorySizeMetricsStatisticsVO size;

    /**
     * 使用率
     */
    private MemoryUsingMetricsStatisticsVO using;

}
