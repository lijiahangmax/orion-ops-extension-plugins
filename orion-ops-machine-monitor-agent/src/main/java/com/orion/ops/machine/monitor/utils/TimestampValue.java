package com.orion.ops.machine.monitor.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 时间戳数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 14:57
 */
@Data
@AllArgsConstructor
public class TimestampValue<T> {

    /**
     * 时间戳
     */
    private Long time;

    /**
     * 数据
     */
    private T value;

}
