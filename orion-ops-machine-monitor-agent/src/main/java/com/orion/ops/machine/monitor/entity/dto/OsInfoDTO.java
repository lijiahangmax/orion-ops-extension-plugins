package com.orion.ops.machine.monitor.entity.dto;

import lombok.Data;

/**
 * 系统信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/28 14:37
 */
@Data
public class OsInfoDTO {

    /**
     * 系统名称
     */
    private String osName;

    /**
     * 启动时间 (秒)
     */
    private Long uptime;

    /**
     * cpu 名称
     */
    private String cpuName;

    /**
     * cpu 核心数
     */
    private Integer cpuCore;

    /**
     * 总内存
     */
    private Long totalMemory;

}
