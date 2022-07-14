package com.orion.ops.monitor.common.entity.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 硬盘 IO 使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/28 10:25
 */
@Data
@ApiModel(value = "硬盘io使用信息")
public class DiskIoUsageDTO {

    @ApiModelProperty(value = "名称")
    private String model;

    @ApiModelProperty(value = "读取次数")
    private Long readCount;

    @ApiModelProperty(value = "读取字节数")
    private Long readBytes;

    @ApiModelProperty(value = "写入次数")
    private Long writeCount;

    @ApiModelProperty(value = "写入字节数")
    private Long writeBytes;

    @ApiModelProperty(value = "使用时间")
    private Long usageTime;

}
