package com.orion.ops.plugin.common.entity.agent.vo;

import com.orion.lang.utils.convert.TypeStore;
import com.orion.ops.plugin.common.entity.agent.dto.CpuUsageDTO;
import com.orion.ops.plugin.common.utils.Formats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * cpu 使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 19:04
 */
@Data
@ApiModel(value = "cpu使用信息")
public class CpuUsageVO {

    @ApiModelProperty(value = "使用率")
    private Double usage;

    @ApiModelProperty(value = "核心使用率")
    private List<Double> coreUsage;

    static {
        TypeStore.STORE.register(CpuUsageDTO.class, CpuUsageVO.class, p -> {
            CpuUsageVO vo = new CpuUsageVO();
            vo.setUsage(Formats.roundToDouble(p.getUsage()));
            vo.setCoreUsage(p.getCoreUsage().stream()
                    .map(Formats::roundToDouble)
                    .collect(Collectors.toList()));
            return vo;
        });
    }

}
