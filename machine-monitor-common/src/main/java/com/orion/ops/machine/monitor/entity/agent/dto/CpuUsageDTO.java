package com.orion.ops.machine.monitor.entity.agent.dto;

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
public class CpuUsageDTO {

    /**
     * 使用率
     */
    private Double usage;

    /**
     * 核心使用率
     */
    private List<Double> coreUsage;

}
