package com.orion.ops.machine.monitor.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Calendar;
import java.util.function.Consumer;

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
    MINUTE(10, false, c -> c.add(Calendar.MINUTE, 1)),

    /**
     * 1小时
     */
    HOUR(20, true, c -> c.add(Calendar.HOUR_OF_DAY, 1)),

    /**
     * 1天
     */
    DAY(30, true, c -> c.add(Calendar.DAY_OF_MONTH, 1)),

    ;

    private final Integer type;

    /**
     * 是否查询时级粒度数据
     */
    private final boolean queryMonth;

    /**
     * 时间累加器
     */
    private final Consumer<Calendar> adder;

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
