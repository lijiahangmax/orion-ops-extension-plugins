package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.entity.dto.DiskStoreUsingDTO;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.convert.TypeStore;
import com.orion.utils.io.Files1;
import lombok.Data;

/**
 * 磁盘空间使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 17:30
 */
@Data
public class DiskStoreUsingVO {

    /**
     * 磁盘名称
     */
    private String name;

    /**
     * 磁盘总空间
     */
    private String totalSpace;

    /**
     * 使用空间
     */
    private String usingSpace;

    /**
     * 空闲空间
     */
    private String freeSpace;

    /**
     * 磁盘使用率
     */
    private Double usingRate;

    static {
        TypeStore.STORE.register(DiskStoreUsingDTO.class, DiskStoreUsingVO.class, p -> {
            DiskStoreUsingVO vo = new DiskStoreUsingVO();
            vo.setName(p.getName());
            vo.setTotalSpace(Files1.getSize(p.getTotalSpace()));
            vo.setUsingSpace(Files1.getSize(p.getUsingSpace()));
            vo.setFreeSpace(Files1.getSize(p.getFreeSpace()));
            vo.setUsingRate(Utils.roundToDouble(p.getUsingRate()));
            return vo;
        });
    }

}
