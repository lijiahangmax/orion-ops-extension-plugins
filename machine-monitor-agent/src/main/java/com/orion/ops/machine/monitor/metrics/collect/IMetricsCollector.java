package com.orion.ops.machine.monitor.metrics.collect;

import com.orion.lang.utils.collect.Lists;
import com.orion.ops.machine.monitor.entity.agent.bo.BaseRangeBO;

import java.util.List;

/**
 * 数据采集器接口
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 11:28
 */
public interface IMetricsCollector<T extends BaseRangeBO> {

    /**
     * 采集数据
     *
     * @return 数据
     */
    T collect();

    /**
     * 采集数据
     *
     * @return 数据集合
     */
    default List<T> collectAsList() {
        return Lists.singleton(this.collect());
    }

}
