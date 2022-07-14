package com.orion.ops.plugin.common.entity.agent.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.orion.lang.define.wrapper.TimestampValue;
import com.orion.lang.utils.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "指标统计")
public class MetricsStatisticsVO<T> {

    @JSONField(ordinal = 0)
    @ApiModelProperty(value = "最大值")
    private T max;

    @JSONField(ordinal = 1)
    @ApiModelProperty(value = "最小值")
    private T min;

    @JSONField(ordinal = 2)
    @ApiModelProperty(value = "平均值")
    private Double avg;

    @JSONField(ordinal = 3)
    @ApiModelProperty(value = "指标")
    private List<TimestampValue<T>> metrics;

    public MetricsStatisticsVO() {
        this.metrics = Lists.newList();
    }

}
