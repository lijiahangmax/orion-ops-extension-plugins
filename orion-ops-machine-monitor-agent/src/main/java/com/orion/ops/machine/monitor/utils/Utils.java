package com.orion.ops.machine.monitor.utils;

import java.math.BigDecimal;

/**
 * 工具类
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 18:55
 */
public class Utils {

    private Utils() {
    }

    /**
     * 四舍五入为整数
     *
     * @param d d
     * @return 整数
     */
    public static Double roundToDouble(double d) {
        return Double.valueOf(String.format("%.2f", d));
    }

}
