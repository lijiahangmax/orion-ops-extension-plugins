package com.orion.ops.machine.monitor.entity.dto;

import lombok.Data;

/**
 * 系统负载信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/27 18:43
 */
@Data
public class SystemLoadDTO {

    /**
     * 1分 负载
     */
    private Double oneMinuteLoad;

    /**
     * 5分 负载
     */
    private Double fiveMinuteLoad;

    /**
     * 15分 负载
     */
    private Double fifteenMinuteLoad;

}
