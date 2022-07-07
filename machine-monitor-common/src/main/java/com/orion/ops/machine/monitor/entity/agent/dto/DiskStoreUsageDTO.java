package com.orion.ops.machine.monitor.entity.agent.dto;

import lombok.Data;

/**
 * 硬盘空间使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/27 18:37
 */
@Data
public class DiskStoreUsageDTO {

    /**
     * 硬盘名称
     */
    private String name;

    /**
     * 硬盘总空间
     */
    private Long totalSpace;

    /**
     * 使用空间
     */
    private Long usageSpace;

    /**
     * 空闲空间
     */
    private Long freeSpace;

    /**
     * 硬盘使用率
     */
    private Double usage;

}
