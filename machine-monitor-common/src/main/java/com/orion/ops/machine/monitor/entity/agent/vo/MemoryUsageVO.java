package com.orion.ops.machine.monitor.entity.agent.vo;

import com.orion.lang.utils.convert.TypeStore;
import com.orion.lang.utils.io.Files1;
import com.orion.ops.machine.monitor.entity.agent.dto.MemoryUsageDTO;
import com.orion.ops.machine.monitor.utils.Formats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内存使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 16:35
 */
@Data
@ApiModel(value = "内存使用信息")
public class MemoryUsageVO {

    @ApiModelProperty(value = "总内存")
    private String totalMemory;

    @ApiModelProperty(value = "使用内存")
    private String usageMemory;

    @ApiModelProperty(value = "空闲内存")
    private String freeMemory;

    @ApiModelProperty(value = "内存使用率")
    private Double usage;

    static {
        TypeStore.STORE.register(MemoryUsageDTO.class, MemoryUsageVO.class, p -> {
            MemoryUsageVO vo = new MemoryUsageVO();
            vo.setTotalMemory(Files1.getSize(p.getTotalMemory()));
            vo.setUsageMemory(Files1.getSize(p.getUsageMemory()));
            vo.setFreeMemory(Files1.getSize(p.getFreeMemory()));
            vo.setUsage(Formats.roundToDouble(p.getUsage()));
            return vo;
        });
    }

}
