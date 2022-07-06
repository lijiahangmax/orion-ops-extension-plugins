package com.orion.ops.machine.monitor.entity.request;

import lombok.Data;

/**
 * 监控请求
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:20
 */
@Data
public class MetricsStatisticsRequest {

    /**
     * 粒度
     *
     * @see com.orion.ops.machine.monitor.constant.GranularityType
     */
    private Integer granularity;

    /**
     * 开始区间 秒
     */
    private Long startRange;

    /**
     * 结束区间 秒
     */
    private Long endRange;

    /**
     * 序列
     */
    private String seq;

}
