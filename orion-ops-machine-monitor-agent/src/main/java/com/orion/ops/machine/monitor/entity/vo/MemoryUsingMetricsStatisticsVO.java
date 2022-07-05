package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.utils.TimestampValue;
import lombok.Data;

import java.util.List;

/**
 * 内存监控指标统计数据 (使用率)
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 14:19
 */
@Data
public class MemoryUsingMetricsStatisticsVO {

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
