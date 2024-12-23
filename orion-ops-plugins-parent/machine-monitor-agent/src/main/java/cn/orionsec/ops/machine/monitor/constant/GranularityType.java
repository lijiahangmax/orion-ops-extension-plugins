/*
 * Copyright (c) 2021 - present Jiahang Li, All rights reserved.
 *
 *   https://om.orionsec.cn
 *
 * Members:
 *   Jiahang Li - ljh1553488six@139.com - author
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.orionsec.ops.machine.monitor.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Calendar;
import java.util.function.Consumer;

/**
 * 数据粒度
 * <p>
 * 数据粒度与采集周期挂钩
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:10
 */
@Getter
@AllArgsConstructor
public enum GranularityType {

    /**
     * 10秒
     */
    SECOND_10(10, false, c -> c.add(Calendar.SECOND, 10)),

    /**
     * 30秒
     */
    SECOND_30(12, false, c -> c.add(Calendar.SECOND, 30)),

    /**
     * 1分钟
     */
    MINUTE_1(20, false, c -> c.add(Calendar.MINUTE, 1)),

    /**
     * 5分钟
     */
    MINUTE_5(22, false, c -> c.add(Calendar.MINUTE, 5)),

    /**
     * 10分钟
     */
    MINUTE_10(24, false, c -> c.add(Calendar.MINUTE, 10)),

    /**
     * 30分钟
     */
    MINUTE_30(26, false, c -> c.add(Calendar.MINUTE, 30)),

    /**
     * 1小时
     */
    HOUR_1(30, true, c -> c.add(Calendar.HOUR_OF_DAY, 1)),

    /**
     * 6小时
     */
    HOUR_6(32, true, c -> c.add(Calendar.HOUR_OF_DAY, 6)),

    /**
     * 12小时
     */
    HOUR_12(34, true, c -> c.add(Calendar.HOUR_OF_DAY, 12)),

    /**
     * 1天
     */
    DAY(40, true, c -> c.add(Calendar.DAY_OF_MONTH, 1)),

    /**
     * 1周
     */
    WEEK(50, true, c -> c.add(Calendar.WEEK_OF_MONTH, 1)),

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
