package com.orion.ops.machine.monitor.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * cpu 使用率
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/27 17:53
 */
@Data
public class CpuUsingDTO {

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 用户使用率
     */
    private Double userUsing;

    /**
     * 系统使用率
     */
    private Double systemUsing;

    /**
     * 总使用率
     */
    private Double totalUsing;

    /**
     * 核心使用率
     */
    private List<Double> coreUsing;

}
