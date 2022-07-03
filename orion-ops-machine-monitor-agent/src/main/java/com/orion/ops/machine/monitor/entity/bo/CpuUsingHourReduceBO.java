package com.orion.ops.machine.monitor.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * cpu 使用存储小时指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/2 15:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CpuUsingHourReduceBO extends HourReduceBO {

    /**
     * 最大使用率 maxUsing
     */
    private Double max;

    /**
     * 最小使用率 minUsing
     */
    private Double min;

    /**
     * 平均使用率 avgUsing
     */
    private Double avg;

}
