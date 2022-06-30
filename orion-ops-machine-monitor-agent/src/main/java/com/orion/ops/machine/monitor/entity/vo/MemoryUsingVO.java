package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.entity.dto.MemoryUsingDTO;
import com.orion.ops.machine.monitor.utils.Utils;
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
public class MemoryUsingVO {

    /**
     * 总内存
     */
    private String totalMemory;

    /**
     * 使用内存
     */
    private String usingMemory;

    /**
     * 空闲内存
     */
    private String freeMemory;

    /**
     * 内存使用率
     */
    private Double usingRate;

    static {
        TypeStore.STORE.register(MemoryUsingDTO.class, MemoryUsingVO.class, p -> {
            MemoryUsingVO vo = new MemoryUsingVO();
            vo.setTotalMemory(Files1.getSize(p.getTotalMemory()));
            vo.setUsingMemory(Files1.getSize(p.getUsingMemory()));
            vo.setFreeMemory(Files1.getSize(p.getFreeMemory()));
            vo.setUsingRate(Utils.roundToDouble(p.getUsingRate()));
            return vo;
        });
    }

}
