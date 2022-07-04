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
     * 最大流量上行速率 mpb/s maxSentMpbRate
     */
    private Double maxsmr;

    /**
     * 最小流量上行速率 mpb/s minSentMpbRate
     */
    private Double minsmr;

    /**
     * 平均流量上行速率 mpb/s avgSentMpbRate
     */
    private Double avgsmr;

    /**
     * 最大流量下行速率 mpb/s maxReceivedMpbRate
     */
    private Double maxrmr;

    /**
     * 最小流量下行速率 mpb/s minReceivedMpbRate
     */
    private Double minrmr;

    /**
     * 平均流量下行速率 mpb/s avgReceivedMpbRate
     */
    private Double avgrmr;

    /**
     * 最大上行包速率 p/s maxSentPacketRate
     */
    private Double maxspr;

    /**
     * 最小上行包速率 p/s minSentPacketRate
     */
    private Double minspr;

    /**
     * 平均上行包速率 p/s avgSentPacketRate
     */
    private Double avgspr;

    /**
     * 最大下行包速率 p/s maxReceivedPacketRate
     */
    private Double maxrpr;

    /**
     * 最小下行包速率 p/s minReceivedPacketRate
     */
    private Double minrpr;

    /**
     * 平均下行包速率 p/s avgReceivedPacketRate
     */
    private Double avgrpr;

}
