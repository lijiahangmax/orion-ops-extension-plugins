package com.orion.ops.machine.monitor.metrics.statistics;

import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.constant.DataMetricsType;
import com.orion.ops.machine.monitor.constant.GranularityType;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.CpuMetricsStatisticsVO;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.time.Dates;

import java.util.List;

/**
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:51
 */
public class CpuMetricsStatisticResolver {

    /**
     * 访问器
     */
    private final MetricsFileAccessor<CpuUsingBO> accessor;

    /**
     * 监控指标
     */
    private final CpuMetricsStatisticsVO metrics;

    public CpuMetricsStatisticResolver(MetricsStatisticsRequest request) {
        this.accessor = new MetricsFileAccessor<>(request.getStartRange(),
                request.getEndRange(),
                DataMetricsType.CPU,
                GranularityType.of(request.getGranularity()));
        this.metrics = new CpuMetricsStatisticsVO();
    }

    /**
     * 执行统计
     *
     * @return 统计信息
     */
    public CpuMetricsStatisticsVO statistics() {
        // 查询数据
        List<CpuUsingBO> rows = accessor.access();
        // 计算数据指标
        this.setMax(rows);
        this.setMin(rows);
        this.setAvg(rows);
        // 绘制图表数据
        this.computeMetricsChart();
        return metrics;
    }

    /**
     * 设置最大值
     *
     * @param rows rows
     */
    protected void setMax(List<CpuUsingBO> rows) {
        double max = rows.stream()
                .mapToDouble(CpuUsingBO::getU)
                .max()
                .orElse(Const.D_0);
        metrics.setMax(max);
    }

    /**
     * 设置最小值
     *
     * @param rows rows
     */
    protected void setMin(List<CpuUsingBO> rows) {
        double min = rows.stream()
                .mapToDouble(CpuUsingBO::getU)
                .min()
                .orElse(Const.D_0);
        metrics.setMax(min);
    }

    /**
     * 设置平均值
     *
     * @param rows rows
     */
    protected void setAvg(List<CpuUsingBO> rows) {
        double avg = rows.stream()
                .mapToDouble(CpuUsingBO::getU)
                .average()
                .orElse(Const.D_0);
        metrics.setMax(Utils.roundToDouble(avg, 3));
    }

    /**
     * 计算表格数据
     */
    protected void computeMetricsChart() {

    }

    public static void main(String[] args) {
        long s = Dates.parse("202207042239").getTime() / 1000;
        long e = Dates.parse("202207042240").getTime() / 1000;
        MetricsStatisticsRequest r = new MetricsStatisticsRequest();
        r.setStartRange(s);
        r.setEndRange(e);
        r.setGranularity(GranularityType.MINUTE.getType());
        CpuMetricsStatisticResolver res = new CpuMetricsStatisticResolver(r);
        System.out.println(res.statistics());
    }

}
