package com.orion.ops.machine.monitor.metrics.statistics;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.Exceptions;
import com.orion.utils.collect.Lists;
import com.orion.utils.io.Files1;
import com.orion.utils.time.Dates;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:51
 */
public class BaseMetricsStatisticResolver {

    void statistics() {

    }

    public static void main(String[] args) {
        BaseMetricsStatisticResolver r = new BaseMetricsStatisticResolver();
        Date start = Dates.parse("202207041840");
        Date end = Dates.parse("202207041843");
        List<CpuUsingBO> datas = r.readCpuDataDay(start.getTime() / 1000, end.getTime() / 1000);
        datas.stream().map(JSON::toJSONString).forEach(System.out::println);
    }

    public List<CpuUsingBO> readCpuDataDay(long startRange, long endRange) {
        // 计算出数据所在的文件
        List<String> blocks = getRangeDays(startRange, endRange);
        List<String> files = this.getDayDataBlockFileNames(blocks);
        // 读取并且过滤数据
        return this.readFileLines(files, startRange, endRange);
    }


    /**
     * 获取时间区间 天
     *
     * @param startRange startRange
     * @param endRange   endRange
     * @return 天数
     */
    protected List<String> getRangeDays(long startRange, long endRange) {
        List<String> list = Lists.newList();
        // 获取文件名
        String startRangeTime = Utils.getRangeStartTime(startRange);
        String endRangeTime = Utils.getRangeStartTime(endRange);
        list.add(startRangeTime);
        // 无文件跨度 直接返回
        if (startRangeTime.equals(endRangeTime)) {
            return list;
        }
        // 累加天数 直到相同
        while (!startRangeTime.equals(endRangeTime)) {
            startRange += (Dates.DAY_STAMP / Dates.SECOND_STAMP);
            startRangeTime = Utils.getRangeStartTime(startRange);
            list.add(startRangeTime);
        }
        return list;
    }

    /**
     * 获取时间区间 月
     *
     * @param startRange startRange
     * @param endRange   endRange
     * @return 天数
     */
    protected List<String> getRangeMonths(long startRange, long endRange) {
        List<String> list = Lists.newList();
        // 获取文件名
        String startRangeTime = Utils.getRangeStartMonth(startRange);
        String endRangeTime = Utils.getRangeStartMonth(endRange);
        list.add(startRangeTime);
        // 无文件跨度 直接返回
        if (startRangeTime.equals(endRangeTime)) {
            return list;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(startRange * Dates.SECOND_STAMP));
        // 累加月份 直到相同
        while (!startRangeTime.equals(endRangeTime)) {
            calendar.add(Calendar.MONTH, 1);
            startRange = calendar.getTimeInMillis() / Dates.SECOND_STAMP;
            startRangeTime = Utils.getRangeStartMonth(startRange);
            list.add(startRangeTime);
        }
        return list;
    }

    protected List<String> getDayDataBlockFileNames(List<String> dates) {
        return dates.stream()
                .map(PathBuilders::getCpuDayDataPath)
                .collect(Collectors.toList());
    }

    protected List<String> getMonthDataBlockFileNames(List<String> dates) {
        return dates.stream()
                .map(PathBuilders::getCpuMonthDataPath)
                .collect(Collectors.toList());
    }

    protected List<CpuUsingBO> readFileLines(List<String> files, long startRange, long endRange) {
        List<CpuUsingBO> list = Lists.newList();
        for (String file : files) {
            Path path = Paths.get(file);
            // 不存在则跳过
            if (!Files.exists(path)) {
                continue;
            }
            // 存在则读取
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    CpuUsingBO bo = JSON.parseObject(line, CpuUsingBO.class);
                    if (startRange >= bo.getU() && bo.getU() <= endRange) {
                        list.add(bo);
                    }
                }
            } catch (IOException e) {
                throw Exceptions.ioRuntime(e);
            }
        }
        return list;
    }

}
