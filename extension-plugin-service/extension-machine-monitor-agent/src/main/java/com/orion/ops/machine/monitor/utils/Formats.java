package com.orion.ops.machine.monitor.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 工具类
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 18:55
 */
@Slf4j
public class Formats {

    private Formats() {
    }

    /**
     * 格式化使用时间为 days, hh:mm:ss.
     *
     * @param secs 秒
     * @return 格式时间
     */
    public static String formatElapsedSecs(long secs) {
        long eTime = secs;
        final long days = TimeUnit.SECONDS.toDays(eTime);
        eTime -= TimeUnit.DAYS.toSeconds(days);
        final long hr = TimeUnit.SECONDS.toHours(eTime);
        eTime -= TimeUnit.HOURS.toSeconds(hr);
        final long min = TimeUnit.SECONDS.toMinutes(eTime);
        eTime -= TimeUnit.MINUTES.toSeconds(min);
        final long sec = eTime;
        return String.format("%d days, %02d:%02d:%02d", days, hr, min, sec);
    }

    /**
     * 四舍五入到整数
     *
     * @param d d
     * @return 整数
     */
    public static int roundToInt(double d) {
        return (int) Math.round(d);
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

}
