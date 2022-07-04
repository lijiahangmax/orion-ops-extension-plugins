package com.orion.ops.machine.monitor.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据粒度
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:10
 */
@Getter
@AllArgsConstructor
public enum GranularityType {

    /**
     * 1分钟
     */
    MINUTE(10),

    /**
     * 1小时
     */
    HOUR(20),

    /**
     * 1天
     */
    DAY(30),

    ;

    private final Integer type;

    public static GranularityType of(Integer type) {
        if (type == null) {
            return null;
        }
        for (GranularityType value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }

}
