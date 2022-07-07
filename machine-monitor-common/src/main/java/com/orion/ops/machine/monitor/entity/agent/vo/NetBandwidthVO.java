package com.orion.ops.machine.monitor.entity.agent.vo;

import com.orion.lang.utils.convert.TypeStore;
import com.orion.lang.utils.io.Files1;
import com.orion.ops.machine.monitor.entity.agent.dto.NetBandwidthDTO;
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
    private String up;

    /**
     * 下行流量速率
     */
    private String down;

    static {
        TypeStore.STORE.register(NetBandwidthDTO.class, NetBandwidthVO.class, p -> {
            NetBandwidthVO vo = new NetBandwidthVO();
            vo.setUp(Files1.getSize(p.getUp()));
            vo.setDown(Files1.getSize(p.getDown()));
            return vo;
        });
    }

}
