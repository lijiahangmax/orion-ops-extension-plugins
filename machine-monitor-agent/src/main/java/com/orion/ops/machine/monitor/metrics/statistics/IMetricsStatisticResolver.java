package com.orion.ops.machine.monitor.metrics.statistics;

import com.orion.ops.machine.monitor.entity.agent.vo.BaseMetricsStatisticsEntity;

/**
 * 数据指标统计接口
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/6 10:40
 */
public interface IMetricsStatisticResolver<S extends BaseMetricsStatisticsEntity> {

    /**
     * 统计数据
     */
    void statistics();

    /**
     * 获取统计指标
     *
     * @return 统计指标
     */
    S getMetrics();

}
