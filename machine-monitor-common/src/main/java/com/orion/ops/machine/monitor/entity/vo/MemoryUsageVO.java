package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.entity.dto.MemoryUsageDTO;
import com.orion.ops.machine.monitor.utils.CommonUtils;
import com.orion.utils.convert.TypeStore;
import com.orion.utils.io.Files1;
import lombok.Data;

/**
 * 内存使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 16:35
 */
@Data
public class MemoryUsageVO {

    /**
     * 总内存
     */
    private String totalMemory;

    /**
     * 使用内存
     */
    private String usageMemory;

    /**
     * 空闲内存
     */
    private String freeMemory;

    /**
     * 内存使用率
     */
    private Double usage;

    static {
        TypeStore.STORE.register(MemoryUsageDTO.class, MemoryUsageVO.class, p -> {
            MemoryUsageVO vo = new MemoryUsageVO();
            vo.setTotalMemory(Files1.getSize(p.getTotalMemory()));
            vo.setUsageMemory(Files1.getSize(p.getUsageMemory()));
            vo.setFreeMemory(Files1.getSize(p.getFreeMemory()));
            vo.setUsage(CommonUtils.roundToDouble(p.getUsage()));
            return vo;
        });
    }

}
