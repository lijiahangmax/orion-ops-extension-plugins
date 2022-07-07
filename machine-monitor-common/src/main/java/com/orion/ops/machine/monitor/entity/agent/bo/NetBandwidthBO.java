package com.orion.ops.machine.monitor.entity.agent.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网络带宽指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 14:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NetBandwidthBO extends BaseRangeBO {

    /**
     * 上行流量 kb sentSize
     */
    private Long ss;

    /**
     * 下行流量 kb receivedSize
     */
    private Long rs;

    /**
     * 上行包 sentPacket
     */
    private Long sp;

    /**
     * 下行包 receivedPacket
     */
    private Long rp;

}
