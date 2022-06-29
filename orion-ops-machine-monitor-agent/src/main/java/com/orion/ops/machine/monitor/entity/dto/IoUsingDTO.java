package com.orion.ops.machine.monitor.entity.dto;

import lombok.Data;

/**
 * IO 使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/28 10:25
 */
@Data
public class IoUsingDTO {

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 名称
     */
    private String name;

    /**
     * 读取次数
     */
    private Long readCount;

    /**
     * 读取字节数
     */
    private Long readBytes;

    /**
     * 写入次数
     */
    private Long writeCount;

    /**
     * 写入字节数
     */
    private Long writeBytes;

    /**
     * 使用时间
     */
    private Long usingTime;

}
