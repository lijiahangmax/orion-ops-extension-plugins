package com.orion.ops.machine.monitor.metrics.statistics;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.constant.DataMetricsType;
import com.orion.ops.machine.monitor.constant.GranularityType;
import com.orion.ops.machine.monitor.entity.bo.BaseRangeBO;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsingBO;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.MemoryMetricsStatisticsVO;
import com.orion.ops.machine.monitor.entity.vo.MemorySizeMetricsStatisticsVO;
import com.orion.ops.machine.monitor.entity.vo.MemoryUsingMetricsStatisticsVO;
import com.orion.ops.machine.monitor.utils.TimestampValue;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.collect.Lists;
import com.orion.utils.time.Dates;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * 内存数据指标统计
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 14:24
 */
public class MemoryMetricsStatisticResolver extends BaseMetricsStatisticResolver<MemoryUsingBO, MemoryMetricsStatisticsVO> {

    /**
     * 使用量
     */
    private final MemorySizeMetricsStatisticsVO size;

    /**
     * 使用率
     */
    private final MemoryUsingMetricsStatisticsVO using;

    public MemoryMetricsStatisticResolver(MetricsStatisticsRequest request) {
        super(request, DataMetricsType.MEMORY, new MemoryMetricsStatisticsVO());
        this.size = new MemorySizeMetricsStatisticsVO();
        this.using = new MemoryUsingMetricsStatisticsVO();
        metrics.setSize(size);
        metrics.setUsing(using);
    }

    @Override
    protected void computeGranularityMetrics(List<MemoryUsingBO> rows, List<Long> datelines) {
        System.out.println("-------------- rows ---------------");
        rows.stream().map(JSON::toJSONString).forEach(System.out::println);
        System.out.println("\n------------ datelines ------------");
        datelines.stream()
                // .map(s -> s * 1000)
                // .map(Dates::date)
                // .map(Dates::format)
                .forEach(System.out::println);
        System.out.println();

        List<TimestampValue<Double>> sizeMetrics = Lists.newList();
        List<TimestampValue<Double>> usingMetrics = Lists.newList();
        for (int i = 0; i < datelines.size() - 1; i++) {
            Long start = datelines.get(i);
            Long end = datelines.get(i + 1);
            List<MemoryUsingBO> currentRows = Lists.newList();
            // 设置数据到当前区间并且移除元素
            Iterator<MemoryUsingBO> iter = rows.iterator();
            while (iter.hasNext()) {
                MemoryUsingBO row = iter.next();
                Long sr = row.getSr();
                if (start <= sr && sr < end) {
                    currentRows.add(row);
                    iter.remove();
                }
            }
            // 统计数据
            System.out.print(start + "-" + end + " ");
            System.out.println(currentRows.stream().map(BaseRangeBO::getSr).collect(Collectors.toList()));
            double size = currentRows
                    .stream()
                    .mapToDouble(MemoryUsingBO::getUs)
                    .average()
                    .orElse(Const.D_0);
            double use = currentRows
                    .stream()
                    .mapToDouble(MemoryUsingBO::getUr)
                    .average()
                    .orElse(Const.D_0);
            sizeMetrics.add(new  TimestampValue<>(start, Utils.roundToDouble(size, 3)));
            usingMetrics.add(new TimestampValue<>(start, Utils.roundToDouble(use, 3)));
        }
        size.setMetrics(sizeMetrics);
        using.setMetrics(usingMetrics);
    }

    @Override
    protected void computeMetricsMax() {
        double sizeMax = super.calcDataAgg(size.getMetrics(), DoubleStream::max);
        double usingMax = super.calcDataAgg(using.getMetrics(), DoubleStream::max);
        size.setMax(sizeMax);
        using.setMax(usingMax);
    }

    @Override
    protected void computeMetricsMin() {
        double sizeMin = super.calcDataAgg(size.getMetrics(), DoubleStream::min);
        double usingMin = super.calcDataAgg(using.getMetrics(), DoubleStream::min);
        size.setMin(sizeMin);
        using.setMin(usingMin);
    }

    @Override
    protected void computeMetricsAvg() {
        double sizeAvg = super.calcDataAgg(size.getMetrics(), DoubleStream::average);
        double usingAvg = super.calcDataAgg(using.getMetrics(), DoubleStream::average);
        size.setAvg(sizeAvg);
        using.setAvg(usingAvg);
    }

    public static void main(String[] args) {
        long s = Dates.parse("202207051225").getTime();
        long e = Dates.parse("202207051325").getTime();
        MetricsStatisticsRequest r = new MetricsStatisticsRequest();
        r.setStartRange(s);
        r.setEndRange(e);
        r.setGranularity(GranularityType.MINUTE_1.getType());
        MemoryMetricsStatisticResolver res = new MemoryMetricsStatisticResolver(r);
        res.statistics();
        System.out.println();
        System.out.println(JSON.toJSONString(res.getMetrics()));
        System.out.println();
    }

}
