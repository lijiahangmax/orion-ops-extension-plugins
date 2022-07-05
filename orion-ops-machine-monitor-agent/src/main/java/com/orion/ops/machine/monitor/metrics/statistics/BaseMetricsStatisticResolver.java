package com.orion.ops.machine.monitor.metrics.statistics;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.constant.DataMetricsType;
import com.orion.ops.machine.monitor.constant.GranularityType;
import com.orion.ops.machine.monitor.entity.bo.BaseRangeBO;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.utils.TimestampValue;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.collect.Lists;
import com.orion.utils.time.Dates;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * 数据指标统计基类
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 10:26
 */
public abstract class BaseMetricsStatisticResolver<T extends BaseRangeBO, S extends BaseMetricsStatisticsEntity> {

    /**
     * 访问器
     */
    private final MetricsFileAccessor<T> accessor;

    /**
     * 请求
     */
    protected final MetricsStatisticsRequest request;

    /**
     * 监控指标
     */
    @Getter
    protected final S metrics;

    public BaseMetricsStatisticResolver(MetricsStatisticsRequest request, DataMetricsType metricsType, S metrics) {
        this.request = request;
        this.metrics = metrics;
        this.accessor = new MetricsFileAccessor<>(request.getStartRange(),
                request.getEndRange(),
                metricsType,
                GranularityType.of(request.getGranularity()));
    }

    /**
     * 执行统计
     */
    public void statistics() {
        // 读取数据
        List<T> rows = accessor.access();
        if (rows.isEmpty()) {
            return;
        }
        // 获取粒度时间线
        List<Long> datelines = this.getMetricsDatelines();
        // 计算指标粒度数据
        this.groupingGranularityMetrics(rows, datelines);
        // 计算指标聚合数据
        this.computeMetricsMax();
        this.computeMetricsMin();
        this.computeMetricsAvg();
    }

    /**
     * 获取指标时间线
     * <p>
     * 长度会比时间粒度大1最后一次的结束区间
     *
     * @return 时间线
     */
    protected List<Long> getMetricsDatelines() {
        Long startRange = request.getStartRange();
        Long endRange = request.getEndRange();
        GranularityType granularity = GranularityType.of(request.getGranularity());
        List<Long> list = Lists.newList();
        // 累加时间线直到大于等于结束时间
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(startRange * Dates.SECOND_STAMP));
        while (startRange < endRange) {
            list.add(startRange);
            granularity.getAdder().accept(c);
            startRange = c.getTimeInMillis() / Dates.SECOND_STAMP;
        }
        list.add(startRange);
        return list;
    }

    /**
     * 粒度数据指标分组
     *
     * @param rows      rows
     * @param datelines datelines
     */
    protected void groupingGranularityMetrics(List<T> rows, List<Long> datelines) {
        System.out.println("-------------- rows ---------------");
        rows.stream().map(JSON::toJSONString).forEach(System.out::println);
        System.out.println("\n------------ datelines ------------");
        datelines.forEach(System.out::println);
        System.out.println();

        // 数据分组
        for (int i = 0; i < datelines.size() - 1; i++) {
            Long start = datelines.get(i);
            Long end = datelines.get(i + 1);
            List<T> currentRows = Lists.newList();
            // 设置数据到当前区间并且移除元素
            Iterator<T> iter = rows.iterator();
            while (iter.hasNext()) {
                T row = iter.next();
                Long sr = row.getSr();
                if (start <= sr && sr < end) {
                    currentRows.add(row);
                    iter.remove();
                }
            }
            // 计算数据
            System.out.print(start + "-" + end + " ");
            System.out.println(currentRows.stream().map(BaseRangeBO::getSr).collect(Collectors.toList()));
            this.computeMetricsData(currentRows, start, end);
        }
    }

    /**
     * 计算粒度指标数据
     *
     * @param rows  rows
     * @param start start
     * @param end   end
     */
    protected abstract void computeMetricsData(List<T> rows, Long start, Long end);

    /**
     * 计算最大值
     */
    protected abstract void computeMetricsMax();

    /**
     * 计算最小值
     */
    protected abstract void computeMetricsMin();

    /**
     * 计算平均值
     */
    protected abstract void computeMetricsAvg();

    protected double calcDataAgg(List<TimestampValue<Double>> data, Function<DoubleStream, OptionalDouble> calc) {
        return this.calcDataAgg(data, calc, 3);
    }

    /**
     * 计算聚合数据
     *
     * @param data  data
     * @param calc  计算
     * @param scale 小数位
     * @return data
     */
    protected double calcDataAgg(List<TimestampValue<Double>> data, Function<DoubleStream, OptionalDouble> calc, Integer scale) {
        DoubleStream stream = data.stream().mapToDouble(TimestampValue::getValue);
        double max = calc.apply(stream).orElse(Const.D_0);
        return Utils.roundToDouble(max, scale);
    }

}
