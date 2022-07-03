package com.orion.ops.machine.monitor.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内存使用存储小时指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/3 22:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryUsingHourReduceBO extends HourReduceBO {

    /**
     * 最大使用率 maxUsingRate
     */
    private Double maxur;

    /**
     * 最小使用率 minUsingRate
     */
    private Double minur;

    /**
     * 平均使用率 avgUsingRate
     */
    private Double avgur;

    /**
     * 最大使用大小 usingSize MB
     */
    private Long maxus;

    /**
     * 最小使用大小 usingSize MB
     */
    private Long minus;

    /**
     * 平均使用大小 usingSize MB
     */
    private Long avgus;

}
