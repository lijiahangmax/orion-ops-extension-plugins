package com.orion.ops.plugin.common.entity.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/28 14:37
 */
@Data
@ApiModel(value = "系统信息")
public class OsInfoDTO {

    @ApiModelProperty(value = "进程id")
    private Integer pid;

    @ApiModelProperty(value = "系统名称")
    private String osName;

    @ApiModelProperty(value = "启动时间 (秒)")
    private Long uptime;

    @ApiModelProperty(value = "cpu名称")
    private String cpuName;

    @ApiModelProperty(value = "cpu物理核心数")
    private Integer cpuPhysicalCore;

    @ApiModelProperty(value = "cpu逻辑核心数")
    private Integer cpuLogicalCore;

    @ApiModelProperty(value = "总内存")
    private Long totalMemory;

    @ApiModelProperty(value = "主机名")
    private String hostname;

    @ApiModelProperty(value = "用户名")
    private String username;

}
