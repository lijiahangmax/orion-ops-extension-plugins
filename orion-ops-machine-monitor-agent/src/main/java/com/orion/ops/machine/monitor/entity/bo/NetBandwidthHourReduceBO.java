package com.orion.ops.machine.monitor.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网络带宽使用存储小时指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/3 23:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NetBandwidthHourReduceBO extends HourReduceBO {

    /**
     * 上行流量 kb sentSize
     */
    private Long ss;

    /**
     * 上行包 sentPacket
     */
    private Long sp;

    /**
     * 下行流量 kb receivedSize
     */
    private Long rs;

    /**
     * 下行包 receivedPacket
     */
    private Long rp;
}
