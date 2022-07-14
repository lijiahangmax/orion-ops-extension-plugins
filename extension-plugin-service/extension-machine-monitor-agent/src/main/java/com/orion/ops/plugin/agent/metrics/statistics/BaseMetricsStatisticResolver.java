package com.orion.ops.plugin.agent.metrics.statistics;

import com.orion.lang.define.wrapper.TimestampValue;
import com.orion.lang.utils.collect.Lists;
import com.orion.lang.utils.time.Dates;
import com.orion.ops.plugin.agent.constant.Const;
import com.orion.ops.plugin.agent.constant.DataMetricsType;
import com.orion.ops.plugin.common.constant.GranularityType;
import com.orion.ops.plugin.common.entity.agent.bo.BaseRangeBO;
import com.orion.ops.plugin.common.entity.agent.request.MetricsStatisticsRequest;
import com.orion.ops.plugin.common.entity.agent.vo.BaseMetricsStatisticsEntity;
import com.orion.ops.plugin.common.utils.Formats;
import lombok.Getter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

/**
 * 数据指标统计基类
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 10:26
 */
public abstract class BaseMetricsStatisticResolver<T extends BaseRangeBO, S extends BaseMetricsStatisticsEntity> implements IMetricsStatisticResolver<S> {

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

    @Override
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
    protected double calcDataAgg(List<TimestampValue<Double>> data, Function<DoubleStream, OptionalDouble> calc, int scale) {
        DoubleStream stream = data.stream().mapToDouble(TimestampValue::getValue);
        double max = calc.apply(stream).orElse(Const.D_0);
        return Formats.roundToDouble(max, scale);
    }

    /**
     * 计算聚合数据
     *
     * @param data data
     * @param calc 计算
     * @return data
     */
    protected long calcDataAggLong(List<TimestampValue<Long>> data, Function<LongStream, OptionalLong> calc) {
        LongStream stream = data.stream().mapToLong(TimestampValue::getValue);
        return calc.apply(stream).orElse(Const.L_0);
    }

    /**
     * 计算平均值
     *
     * @param data data
     * @return data
     */
    protected double calcDataAvgLong(List<TimestampValue<Long>> data) {
        double d = data.stream()
                .mapToLong(TimestampValue::getValue)
                .average()
                .orElse(Const.D_0);
        return Formats.roundToDouble(d, 3);
    }

}
