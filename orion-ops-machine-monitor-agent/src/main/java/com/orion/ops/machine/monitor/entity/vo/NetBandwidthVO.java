package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.entity.dto.NetBandwidthDTO;
import com.orion.utils.convert.TypeStore;
import com.orion.utils.io.Files1;
import lombok.Data;

/**
 * 网络带宽流量信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 18:41
 */
@Data
public class NetBandwidthVO {

    /**
     * 上行流量速率
     */
    private String upstream;

    /**
     * 下行流量速率
     */
    private String downstream;

    static {
        TypeStore.STORE.register(NetBandwidthDTO.class, NetBandwidthVO.class, p -> {
            NetBandwidthVO vo = new NetBandwidthVO();
            vo.setUpstream(Files1.getSize(p.getUpstream()));
            vo.setDownstream(Files1.getSize(p.getDownstream()));
            return vo;
        });
    }

}
