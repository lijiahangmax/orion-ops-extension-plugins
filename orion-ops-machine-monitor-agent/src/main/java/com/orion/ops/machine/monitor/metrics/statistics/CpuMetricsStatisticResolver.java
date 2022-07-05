package com.orion.ops.machine.monitor.metrics.statistics;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.constant.DataMetricsType;
import com.orion.ops.machine.monitor.constant.GranularityType;
import com.orion.ops.machine.monitor.entity.bo.CpuUsageBO;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.CpuMetricsStatisticsVO;
import com.orion.ops.machine.monitor.entity.vo.MetricsStatisticsVO;
import com.orion.ops.machine.monitor.utils.TimestampValue;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.time.Dates;

import java.util.List;
import java.util.stream.DoubleStream;

/**
 * 处理器数据指标统计
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:51
 */
public class CpuMetricsStatisticResolver extends BaseMetricsStatisticResolver<CpuUsageBO, CpuMetricsStatisticsVO> {

    /**
     * 使用率
     */
    private final MetricsStatisticsVO<Double> usage;

    public CpuMetricsStatisticResolver(MetricsStatisticsRequest request) {
        super(request, DataMetricsType.CPU, new CpuMetricsStatisticsVO());
        this.usage = new MetricsStatisticsVO<>();
        metrics.setUsage(usage);
    }

    @Override
    protected void computeMetricsData(List<CpuUsageBO> rows, Long start, Long end) {
        double avgUsage = rows.stream()
                .mapToDouble(CpuUsageBO::getU)
                .average()
                .orElse(Const.D_0);
        usage.getMetrics().add(new TimestampValue<>(start, Utils.roundToDouble(avgUsage, 3)));
    }

    @Override
    protected void computeMetricsMax() {
        double max = super.calcDataAgg(usage.getMetrics(), DoubleStream::max);
        usage.setMax(max);
    }

    @Override
    protected void computeMetricsMin() {
        double min = super.calcDataAgg(usage.getMetrics(), DoubleStream::min);
        usage.setMin(min);
    }

    @Override
    protected void computeMetricsAvg() {
        double avg = super.calcDataAgg(usage.getMetrics(), DoubleStream::average);
        usage.setAvg(avg);
    }

    public static void main(String[] args) {
        long s = Dates.parse("202207051225").getTime();
        long e = Dates.parse("202207051325").getTime();
        MetricsStatisticsRequest r = new MetricsStatisticsRequest();
        r.setStartRange(s);
        r.setEndRange(e);
        r.setGranularity(GranularityType.MINUTE_1.getType());
        CpuMetricsStatisticResolver res = new CpuMetricsStatisticResolver(r);
        res.statistics();
        System.out.println();
        System.out.println(JSON.toJSONString(res.getMetrics()));
        System.out.println();
    }

}
