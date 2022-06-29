package com.orion.ops.machine.monitor.entity.dto;

import lombok.Data;

/**
 * 网络带宽流量信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/27 18:23
 */
@Data
public class NetBandwidthDTO {

    /**
     * 上行流量速率
     */
    private Long upstreamFlowRate;

    /**
     * 下行流量速率
     */
    private Long downstreamFlowRate;

}
