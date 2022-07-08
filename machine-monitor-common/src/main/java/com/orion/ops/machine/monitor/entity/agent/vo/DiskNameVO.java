package com.orion.ops.machine.monitor.entity.agent.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 硬盘名称
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 15:51
 */
@Data
@ApiModel(value = "硬盘名称")
public class DiskNameVO {

    @ApiModelProperty(value = "硬盘名称")
    private String name;

    @ApiModelProperty(value = "硬盘序列")
    private String seq;

}
