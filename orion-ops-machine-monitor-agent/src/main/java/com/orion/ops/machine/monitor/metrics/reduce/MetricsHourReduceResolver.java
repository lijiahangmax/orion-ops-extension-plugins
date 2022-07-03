package com.orion.ops.machine.monitor.metrics.reduce;

import com.orion.ops.machine.monitor.entity.bo.BaseRangeBO;

/**
 * 时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/2 16:09
 */
public interface MetricsHourReduceResolver<T extends BaseRangeBO> {

    /**
     * 规约数据
     *
     * @param data data
     */
    void reduce(T data);

}
