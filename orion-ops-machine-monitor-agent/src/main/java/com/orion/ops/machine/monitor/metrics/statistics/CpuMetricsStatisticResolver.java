package com.orion.ops.machine.monitor.metrics.statistics;

import com.alibaba.fastjson.JSON;
import com.orion.lang.wrapper.Pair;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.constant.DataMetricsType;
import com.orion.ops.machine.monitor.constant.GranularityType;
import com.orion.ops.machine.monitor.entity.bo.BaseRangeBO;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.CpuMetricsStatisticsVO;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.collect.Lists;
import com.orion.utils.time.Dates;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理器数据指标统计
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:51
 */
public class CpuMetricsStatisticResolver extends BaseMetricsStatisticResolver<CpuUsingBO, CpuMetricsStatisticsVO> {

    public CpuMetricsStatisticResolver(MetricsStatisticsRequest request) {
        super(request, DataMetricsType.CPU, new CpuMetricsStatisticsVO());
    }

    @Override
    protected void setMax(List<CpuUsingBO> rows) {
        double max = rows.stream()
                .mapToDouble(CpuUsingBO::getU)
                .max()
                .orElse(Const.D_0);
        metrics.setMax(max);
    }

    @Override
    protected void setMin(List<CpuUsingBO> rows) {
        double min = rows.stream()
                .mapToDouble(CpuUsingBO::getU)
                .min()
                .orElse(Const.D_0);
        metrics.setMin(min);
    }

    @Override
    protected void setAvg(List<CpuUsingBO> rows) {
        double avg = rows.stream()
                .mapToDouble(CpuUsingBO::getU)
                .average()
                .orElse(Const.D_0);
        metrics.setAvg(Utils.roundToDouble(avg, 3));
    }

    @Override
    protected void computeGranularityMetrics(List<CpuUsingBO> rows, List<Long> datelines) {
        System.out.println("-------------- rows ---------------");
        rows.stream().map(JSON::toJSONString).forEach(System.out::println);
        System.out.println("\n------------ datelines ------------");
        datelines.stream()
                // .map(s -> s * 1000)
                // .map(Dates::date)
                // .map(Dates::format)
                .forEach(System.out::println);
        System.out.println();

        List<Pair<Long, Double>> chartMetrics = Lists.newList();
        for (int i = 0; i < datelines.size() - 1; i++) {
            Long start = datelines.get(i);
            Long end = datelines.get(i + 1);
            List<CpuUsingBO> currentRows = Lists.newList();
            // 设置数据到当前区间并且移除元素
            Iterator<CpuUsingBO> iter = rows.iterator();
            while (iter.hasNext()) {
                CpuUsingBO row = iter.next();
                Long sr = row.getSr();
                if (start <= sr && sr < end) {
                    currentRows.add(row);
                    iter.remove();
                }
            }
            // 统计数据
            System.out.print(start + "-" + end + " ");
            System.out.println(currentRows.stream().map(BaseRangeBO::getSr).collect(Collectors.toList()));
            double use = currentRows
                    .stream()
                    .mapToDouble(CpuUsingBO::getU)
                    .average()
                    .orElse(Const.D_0);
            chartMetrics.add(Pair.of(start, Utils.roundToDouble(use)));
        }
        metrics.setMetrics(chartMetrics);

    }

    public static void main(String[] args) {
        long s = Dates.parse("202207051225").getTime();
        long e = Dates.parse("202207051325").getTime();
        MetricsStatisticsRequest r = new MetricsStatisticsRequest();
        r.setStartRange(s);
        r.setEndRange(e);
        r.setGranularity(GranularityType.MINUTE.getType());
        CpuMetricsStatisticResolver res = new CpuMetricsStatisticResolver(r);
        res.statistics();
        System.out.println();
        System.out.println(JSON.toJSONString(res.getMetrics()));
        System.out.println();
    }

}
