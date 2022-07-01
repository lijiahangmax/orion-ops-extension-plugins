package com.orion.ops.machine.monitor.entity.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内存使用率指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 13:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryUsingBO extends BaseRangeBO {

    /**
     * 使用率 usingRate
     */
    private Double ur;

    /**
     * 使用大小 usingSize MB
     */
    private Long us;

}
