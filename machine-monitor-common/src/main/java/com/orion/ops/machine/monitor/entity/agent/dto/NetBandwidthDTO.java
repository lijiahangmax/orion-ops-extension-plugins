package com.orion.ops.machine.monitor.entity.agent.dto;

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
     * 上行流量流量 byte
     */
    private Long up;

    /**
     * 下行流量流量 byte
     */
    private Long down;

}
