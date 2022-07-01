package com.orion.ops.machine.monitor.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * cpu 使用存储指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 18:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CpuUsingBO extends BaseRangeBO {

    /**
     * 使用率 using
     */
    private Double u;

}
