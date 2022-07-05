package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.utils.TimestampValue;
import lombok.Data;

import java.util.List;

/**
 * cpu 监控指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:32
 */
@Data
public class CpuMetricsStatisticsVO implements BaseMetricsStatisticsVO {

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
     * 使用率
     */
    private List<TimestampValue<Double>> metrics;

}
