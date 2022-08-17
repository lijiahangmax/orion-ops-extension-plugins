package com.orion.ops.machine.monitor.entity.vo;

import com.orion.lang.utils.convert.TypeStore;
import com.orion.lang.utils.io.Files1;
import com.orion.ops.machine.monitor.entity.dto.DiskStoreUsageDTO;
import com.orion.ops.machine.monitor.utils.Formats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 硬盘空间使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 17:30
 */
@Data
@ApiModel(value = "硬盘空间使用信息")
public class DiskStoreUsageVO {

    @ApiModelProperty(value = "硬盘名称")
    private String name;

    @ApiModelProperty(value = "硬盘总空间")
    private String totalSpace;

    @ApiModelProperty(value = "使用空间")
    private String usageSpace;

    @ApiModelProperty(value = "空闲空间")
    private String freeSpace;

    @ApiModelProperty(value = "硬盘使用率")
    private Double usage;

    static {
        TypeStore.STORE.register(DiskStoreUsageDTO.class, DiskStoreUsageVO.class, p -> {
            DiskStoreUsageVO vo = new DiskStoreUsageVO();
            vo.setName(p.getName());
            vo.setTotalSpace(Files1.getSize(p.getTotalSpace()));
            vo.setUsageSpace(Files1.getSize(p.getUsageSpace()));
            vo.setFreeSpace(Files1.getSize(p.getFreeSpace()));
            vo.setUsage(Formats.roundToDouble(p.getUsage() * 100));
            return vo;
        });
    }

}
