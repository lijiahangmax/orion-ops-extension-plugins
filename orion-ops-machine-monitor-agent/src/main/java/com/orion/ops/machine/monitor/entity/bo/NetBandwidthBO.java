package com.orion.ops.machine.monitor.entity.bo;

import com.orion.ops.machine.monitor.constant.Const;
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


    public void setSs(Long ss) {
        this.ss = ss / Const.BUFFER_KB_1;
    }

    public void setRs(Long rs) {
        this.rs = rs / Const.BUFFER_KB_1;
    }

}
