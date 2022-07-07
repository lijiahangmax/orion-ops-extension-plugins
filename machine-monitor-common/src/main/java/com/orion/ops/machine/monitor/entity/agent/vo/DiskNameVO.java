package com.orion.ops.machine.monitor.entity.agent.vo;

import lombok.Data;

/**
 * 硬盘名称
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 15:51
 */
@Data
public class DiskNameVO {

    /**
     * 硬盘名称
     */
    private String name;

    /**
     * 硬盘序列
     */
    private String seq;

}
