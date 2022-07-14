package com.orion.ops.plugin.agent.metrics.collect;

import com.orion.ops.plugin.common.entity.agent.bo.BaseRangeBO;
import com.orion.spring.SpringHolder;
import lombok.AllArgsConstructor;

/**
 * 数据指标采集类型
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 11:28
 */
@AllArgsConstructor
public enum MetricsCollectorType {

    /**
     * 处理器
     */
    CPU(CpuMetricsCollector.class),

    /**
     * 内存
     */
    MEMORY(MemoryMetricsCollector.class),

    /**
     * 网络带宽
     */
    NET(NetBandwidthCollector.class),

    /**
     * 硬盘
     */
    DISK(DiskMetricsCollector.class),

    ;

    private final Class<? extends IMetricsCollector<?>> beanClass;

    /**
     * 获取采集器 bean
     */
    @SuppressWarnings("unchecked")
    public <T extends IMetricsCollector<? extends BaseRangeBO>> T getCollectBean() {
        return (T) SpringHolder.getBean(beanClass);
    }

}
