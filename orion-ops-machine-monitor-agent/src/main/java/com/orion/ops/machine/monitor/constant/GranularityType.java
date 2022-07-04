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
    MINUTE(10, false),

    /**
     * 1小时
     */
    HOUR(20, true),

    /**
     * 1天
     */
    DAY(30, true),

    ;

    private final Integer type;

    /**
     * 是否查询时级粒度数据
     */
    private final boolean queryMonth;

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
