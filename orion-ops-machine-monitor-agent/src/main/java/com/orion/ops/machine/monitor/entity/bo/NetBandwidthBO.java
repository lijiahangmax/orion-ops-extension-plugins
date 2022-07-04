package com.orion.ops.machine.monitor.entity.bo;

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
     * 上行流量 kb sentKB
     */
    private Long sk;

    /**
     * 下行流量 kb receivedKB
     */
    private Long rk;

    /**
     * 上行包 sentPacket
     */
    private Long sp;

    /**
     * 下行包 receivedPacket
     */
    private Long rp;

}
