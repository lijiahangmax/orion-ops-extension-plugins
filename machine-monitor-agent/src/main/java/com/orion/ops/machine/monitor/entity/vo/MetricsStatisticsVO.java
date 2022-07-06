package com.orion.ops.machine.monitor.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.orion.ops.machine.monitor.utils.TimestampValue;
import com.orion.utils.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 指标统计
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 15:07
 */
@Data
public class MetricsStatisticsVO<T> {

    /**
     * 最大值
     */
    @JSONField(ordinal = 0)
    private T max;

    /**
     * 最小值
     */
    @JSONField(ordinal = 1)
    private T min;

    /**
     * 平均值
     */
    @JSONField(ordinal = 2)
    private Double avg;

    /**
     * 指标
     */
    @JSONField(ordinal = 3)
    private List<TimestampValue<T>> metrics;

    public MetricsStatisticsVO() {
        this.metrics = Lists.newList();
    }

}
