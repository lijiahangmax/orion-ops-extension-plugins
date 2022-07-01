package com.orion.ops.machine.monitor.entity.dto;

import lombok.Data;

/**
 * 磁盘空间使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/27 18:37
 */
@Data
public class DiskStoreUsingDTO {

    /**
     * 磁盘名称
     */
    private String name;

    /**
     * 磁盘总空间
     */
    private Long totalSpace;

    /**
     * 使用空间
     */
    private Long usingSpace;

    /**
     * 空闲空间
     */
    private Long freeSpace;

    /**
     * 磁盘使用率
     */
    private Double usingRate;

}
