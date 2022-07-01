package com.orion.ops.machine.monitor.utils;

import static oshi.hardware.CentralProcessor.TickType;

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

    public static Double roundToDouble(double d) {
        return roundToDouble(d, 2);
    }

    /**
     * 四舍五入
     *
     * @param d     d
     * @param scale 小数位
     * @return 四舍五入
     */
    public static Double roundToDouble(double d, int scale) {
        return Double.valueOf(String.format("%." + scale + "f", d));
    }

    /**
     * 计算 cpu 使用率
     *
     * @param prevTicks    prevTicks
     * @param currentTicks currentTicks
     * @return cpu使用率
     */
    public static Double computeCpuLoad(long[] prevTicks, long[] currentTicks) {
        long total = 0;
        for (int i = 0; i < currentTicks.length; i++) {
            total += currentTicks[i] - prevTicks[i];
        }
        long idle = currentTicks[TickType.IDLE.getIndex()]
                + currentTicks[TickType.IOWAIT.getIndex()]
                - prevTicks[TickType.IDLE.getIndex()]
                - prevTicks[TickType.IOWAIT.getIndex()];
        double use = total > 0 ? (double) (total - idle) / total : 0D;
        return roundToDouble(use, 3);
    }

}
