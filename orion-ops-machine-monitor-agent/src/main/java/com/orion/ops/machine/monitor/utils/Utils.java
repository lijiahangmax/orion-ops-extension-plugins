package com.orion.ops.machine.monitor.utils;

import com.alibaba.fastjson.JSON;
import com.orion.constant.Letters;
import com.orion.ops.machine.monitor.entity.bo.BaseRangeBO;
import com.orion.utils.crypto.Signatures;
import com.orion.utils.io.Files1;
import com.orion.utils.time.Dates;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import static oshi.hardware.CentralProcessor.TickType;

/**
 * 工具类
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 18:55
 */
@Slf4j
public class Utils {

    private Utils() {
    }

    // FIXME
    // private static final String YMDH = "yyyyMMddHH";

    private static final String YMDH = "yyyyMMddHHmm";

    private static final String YM = "yyyyMM";

    private static final int MPB = 128 * 1024;

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
        return roundToDouble(use * 100, 3);
    }

    /**
     * 获取硬盘序列
     *
     * @param model model
     * @return seq
     */
    public static String getDiskSeq(String model) {
        return Signatures.md5(model).substring(0, 8);
    }

    /**
     * 时间戳毫秒转时间戳秒
     *
     * @param timestamp 时间戳毫秒
     * @return 时间戳秒
     */
    // FIXME: 放到kit
    public static long getSecondTime(long timestamp) {
        return timestamp / Dates.SECOND_STAMP;
    }

    /**
     * 获取区间开始时间
     *
     * @param sr 开始时间
     * @return 时间
     */
    public static String getRangeStartTime(long sr) {
        return Dates.format(new Date(sr * Dates.SECOND_STAMP), Dates.YMD2);
    }

    /**
     * 获取时间区间月份
     *
     * @param sr 开始时间
     * @return 月份
     */
    public static String getRangeStartMonth(long sr) {
        return Dates.format(new Date(sr * Dates.SECOND_STAMP), YM);
    }

    /**
     * 获取时间区间月份
     *
     * @param hour 开始时间小时
     * @return 月份
     */
    public static String getRangeStartMonth(String hour) {
        return Dates.format(Dates.parse(hour, YMDH), YM);
    }

    /**
     * 获取时间区间小时
     *
     * @param t 开始时间
     * @return 小时
     */
    public static <T extends BaseRangeBO> String getRangeStartHour(T t) {
        return Dates.format(new Date(t.getSr() * Dates.SECOND_STAMP), YMDH);
    }

    /**
     * 设置规约小时区间
     *
     * @param t         t
     * @param startHour 开始时间
     * @param endHour   结束时间
     * @param <T>       T
     */
    public static <T extends BaseRangeBO> void setReduceHourRange(T t, String startHour, String endHour) {
        t.setSr(getSecondTime(Dates.parse(startHour, YMDH).getTime()));
        t.setEr(getSecondTime(Dates.parse(endHour, YMDH).getTime()));
    }

    /**
     * 计算 mpb/s
     *
     * @param range range
     * @param bytes bytes
     * @return mpb/s
     */
    public static Double computeMpbSecondRate(BaseRangeBO range, long bytes) {
        long s = range.getEr() - range.getSr();
        return roundToDouble((double) bytes / s / MPB, 5);
    }

    /**
     * 计算 pecket/s
     *
     * @param range  range
     * @param packet packet
     * @return mpb/s
     */
    public static Double computePacketSecondRate(BaseRangeBO range, long packet) {
        long s = range.getEr() - range.getSr();
        return roundToDouble((double) packet / s / MPB, 3);
    }

    /**
     * 拼接数据
     *
     * @param path path
     * @param data data
     * @param <T>  data type
     */
    public static <T extends BaseRangeBO> void appendMetricsData(String path, T data) {
        // FIXME 升级KIT后需要把这个改为 fast
        try (OutputStream out = Files1.openOutputStream(path, true)) {
            out.write(JSON.toJSONBytes(data));
            out.write(Letters.LF);
            out.flush();
        } catch (IOException e) {
            log.error("数据持久化失败", e);
        }
    }

}
