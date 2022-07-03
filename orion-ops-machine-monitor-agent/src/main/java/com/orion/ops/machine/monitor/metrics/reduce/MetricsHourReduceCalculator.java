package com.orion.ops.machine.monitor.metrics.reduce;

import com.orion.ops.machine.monitor.entity.bo.BaseRangeBO;
import com.orion.spring.SpringHolder;
import lombok.AllArgsConstructor;

/**
 * 数据指标时机粒度计算器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/2 16:02
 */
@AllArgsConstructor
public enum MetricsHourReduceCalculator {

    /**
     * 处理器
     */
    CPU(CpuHourReduceResolver.class),

    /**
     * 内存
     */
    MEMORY(MemoryHourReduceResolver.class),

    /**
     * 网络带宽
     */
    NET(NetBandwidthHourReduceResolver.class),

    ;

    private final Class<? extends IMetricsHourReduceResolver<?>> beanClass;

    /**
     * 获取处理器 bean
     */
    @SuppressWarnings("unchecked")
    public <T extends IMetricsHourReduceResolver<? super BaseRangeBO>> T getReduceResolverBean() {
        return (T) SpringHolder.getBean(beanClass);
    }

}
