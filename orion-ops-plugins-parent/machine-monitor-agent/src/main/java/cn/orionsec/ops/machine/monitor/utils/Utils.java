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
package cn.orionsec.ops.machine.monitor.utils;

import cn.orionsec.kit.lang.constant.Letters;
import cn.orionsec.kit.lang.utils.crypto.Signatures;
import cn.orionsec.kit.lang.utils.io.Files1;
import cn.orionsec.kit.lang.utils.time.Dates;
import cn.orionsec.ops.machine.monitor.entity.bo.BaseRangeBO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

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

    private static final String YMDH = "yyyyMMddHH";

    private static final String YM = "yyyyMM";

    private static final int MBP = 128;

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
        return Formats.roundToDouble(use * 100, 3);
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
     * 计算速率 mbp/s
     *
     * @param second second
     * @param kb     kb
     * @return mbp/s
     */
    public static Double computeMbpSecondSpeed(long second, long kb) {
        return Formats.roundToDouble((double) kb / MBP / second, 5);
    }

    /**
     * 计算速率 mb/s
     *
     * @param second second
     * @param kb     kb
     * @return mb/s
     */
    public static Double computeMbSecondSpeed(long second, long kb) {
        return Formats.roundToDouble((double) kb / second, 5);
    }

    /**
     * 获取 long stream
     *
     * @param list list
     * @param func func
     * @param <T>  T
     * @return stream
     */
    public static <T> LongStream getLongStream(List<T> list, Function<T, Long> func) {
        return list.stream()
                .map(func)
                .filter(Objects::nonNull)
                .mapToLong(s -> s);
    }

    /**
     * 获取 double stream
     *
     * @param list list
     * @param func func
     * @param <T>  T
     * @return stream
     */
    public static <T> DoubleStream getDoubleStream(List<T> list, Function<T, Double> func) {
        return list.stream()
                .map(func)
                .filter(Objects::nonNull)
                .mapToDouble(s -> s);
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
        t.setSr(Dates.getSecondTime(Dates.parse(startHour, YMDH).getTime()));
        t.setEr(Dates.getSecondTime(Dates.parse(endHour, YMDH).getTime()));
    }

    /**
     * 拼接数据
     *
     * @param path path
     * @param data data
     * @param <T>  data type
     */
    public static <T extends BaseRangeBO> void appendMetricsData(String path, T data) {
        try (OutputStream out = Files1.openOutputStreamFast(path, true)) {
            out.write(JSON.toJSONBytes(data));
            out.write(Letters.LF);
            out.flush();
        } catch (IOException e) {
            log.error("数据持久化失败", e);
        }
    }

}
