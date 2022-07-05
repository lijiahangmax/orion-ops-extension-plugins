package com.orion.ops.machine.monitor.entity.vo;

import com.orion.lang.wrapper.Pair;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * cpu 监控指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CpuMetricsStatisticsVO extends BaseMetricsStatisticsVO {

    /**
     * 最大值
     */
    private Double max;

    /**
     * 最小值
     */
    private Double min;

    /**
     * 平均值
     */
    private Double avg;

    /**
     * 数据指标
     */
    private List<Pair<Long, Double>> metrics;

}
