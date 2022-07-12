package com.orion.ops.monitor.common.entity.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统进程
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/28 14:06
 */
@Data
@ApiModel(value = "系统进程")
public class SystemProcessDTO {

    @ApiModelProperty(value = "进程id")
    private Integer pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "用户")
    private String user;

    @ApiModelProperty(value = "cpu使用率")
    private Double cpuUsage;

    @ApiModelProperty(value = "使用内存")
    private Long memoryUsage;

    @ApiModelProperty(value = "句柄数")
    private Long openFile;

    @ApiModelProperty(value = "启用时长")
    private Long uptime;

    @ApiModelProperty(value = "命令行")
    private String commandLine;

}
