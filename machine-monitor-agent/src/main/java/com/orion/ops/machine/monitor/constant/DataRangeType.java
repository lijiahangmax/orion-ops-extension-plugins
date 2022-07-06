package com.orion.ops.machine.monitor.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据区间类型
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:15
 */
@Getter
@AllArgsConstructor
public enum DataRangeType {

    /**
     * 实时 (当前一小时)
     */
    CURRENT(10),

    /**
     * 24小时
     */
    DAY(20),

    /**
     * 30天
     */
    MONTH(30),

    /**
     * 自定义
     */
    CUSTOM(40),

    ;

    private final Integer type;

    public static DataRangeType of(Integer type) {
        if (type == null) {
            return null;
        }
        for (DataRangeType value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }

}
