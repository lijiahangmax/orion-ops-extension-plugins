package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.entity.dto.SystemLoadDTO;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.convert.TypeStore;
import lombok.Data;

/**
 * 系统负载
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 18:16
 */
@Data
public class SystemLoadVO {

    /**
     * 1分 负载
     */
    private Double oneMinuteLoad;

    /**
     * 5分 负载
     */
    private Double fiveMinuteLoad;

    /**
     * 15分 负载
     */
    private Double fifteenMinuteLoad;

    static {
        TypeStore.STORE.register(SystemLoadDTO.class, SystemLoadVO.class, p -> {
            SystemLoadVO vo = new SystemLoadVO();
            vo.setOneMinuteLoad(Utils.roundToDouble(p.getOneMinuteLoad()));
            vo.setFiveMinuteLoad(Utils.roundToDouble(p.getFiveMinuteLoad()));
            vo.setFifteenMinuteLoad(Utils.roundToDouble(p.getFifteenMinuteLoad()));
            return vo;
        });
    }

}
