package com.orion.ops.plugin.agent.constant;

import com.orion.ops.plugin.agent.utils.PathBuilders;
import com.orion.ops.plugin.common.entity.agent.bo.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * 数据指标类型
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 22:51
 */
@Getter
@AllArgsConstructor
public enum DataMetricsType {

    /**
     * 处理器
     */
    CPU(CpuUsageBO.class,
            PathBuilders::getCpuDayDataPath,
            PathBuilders::getCpuMonthDataPath),

    /**
     * 内存
     */
    MEMORY(MemoryUsageBO.class,
            PathBuilders::getMemoryDayDataPath,
            PathBuilders::getMemoryMonthDataPath),

    /**
     * 网络带宽
     */
    NET(NetBandwidthBO.class,
            PathBuilders::getNetDayDataPath,
            PathBuilders::getNetMonthDataPath),

    /**
     * 磁盘
     */
    DISK(DiskIoUsageBO.class,
            d -> PathBuilders.getDiskDayDataPath(d, Currents.getDiskSeq()),
            m -> PathBuilders.getDiskMonthDataPath(m, Currents.getDiskSeq())),

    ;

    private final Class<? extends BaseRangeBO> boClass;

    private final Function<String, String> dayPathGetter;

    private final Function<String, String> monthPathGetter;

}
