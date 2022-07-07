package com.orion.ops.machine.monitor.entity.agent.dto;

import lombok.Data;

/**
 * 系统进程
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/28 14:06
 */
@Data
public class SystemProcessDTO {

    /**
     * 进程id
     */
    private Integer pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 用户
     */
    private String user;

    /**
     * cpu 使用率
     */
    private Double cpuLoad;

    /**
     * 使用内存
     */
    private Long memory;

    /**
     * 句柄数
     */
    private Long openFile;

    /**
     * 启用时长
     */
    private Long uptime;

    /**
     * 命令行
     */
    private String commandLine;

}
