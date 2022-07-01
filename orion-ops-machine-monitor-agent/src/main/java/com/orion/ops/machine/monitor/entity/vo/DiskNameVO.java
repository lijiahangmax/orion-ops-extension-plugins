package com.orion.ops.machine.monitor.entity.vo;

import lombok.Data;

/**
 * 磁盘名称
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 15:51
 */
@Data
public class DiskNameVO {

    /**
     * 磁盘名称
     */
    private String name;

    /**
     * 磁盘序列
     */
    private String seq;

}
