package com.orion.ops.machine.monitor.metrics.reduce;

import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.entity.bo.BaseRangeBO;
import com.orion.ops.machine.monitor.entity.bo.HourReduceBO;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsingBO;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.collect.Lists;

import java.util.List;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

/**
 * 时级数据规约器基类
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/3 22:23
 */
public abstract class BaseMetricsHourReduceResolver<T extends BaseRangeBO, R extends HourReduceBO> implements IMetricsHourReduceResolver<T> {

    /**
     * 当前采集信息粒度时
     */
    protected String currentHour;

    /**
     * 当前采集信息
     */
    protected final List<T> currentMetrics;

    /**
     * 指标路径获取器
     */
    private final Function<String, String> metricsPathGetter;

    public BaseMetricsHourReduceResolver(Function<String, String> metricsPathGetter) {
        this.currentMetrics = Lists.newList();
        this.metricsPathGetter = metricsPathGetter;
    }

    @Override
    public void reduce(T data) {
        String currentHour = Utils.getRangeStartHour(data);
        if (this.currentHour == null) {
            this.currentHour = currentHour;
        }
        // 同一时间
        if (currentHour.equals(this.currentHour)) {
            currentMetrics.add(data);
            return;
        }
        // 不同时间则规约
        String prevHour = this.currentHour;
        this.currentHour = currentHour;
        // 计算数据
        R reduceData = this.computeHourReduceData(currentHour, prevHour);
        Utils.setReduceHourRange(reduceData, prevHour, currentHour);
        currentMetrics.clear();
        currentMetrics.add(data);
        // 拼接到月级数据
        Utils.appendMetricsData(metricsPathGetter.apply(prevHour), reduceData);
    }

    /**
     * 计算规约数据
     *
     * @param currentHour currentHour
     * @param prevHour    prevHour
     * @return reduce
     */
    protected abstract R computeHourReduceData(String currentHour, String prevHour);

    /**
     * 获取数据最大值
     *
     * @param f     f
     * @param scale 小数位
     * @return data
     */
    protected double getMaxReduceData(ToDoubleFunction<T> f, int scale) {
        double v = currentMetrics.stream()
                .mapToDouble(f)
                .max()
                .orElse(Const.D_0);
        return Utils.roundToDouble(v, scale);
    }

    /**
     * 获取数据最小值
     *
     * @param f     f
     * @param scale 小数位
     * @return data
     */
    protected double getMinReduceData(ToDoubleFunction<T> f, int scale) {
        double v = currentMetrics.stream()
                .mapToDouble(f)
                .min()
                .orElse(Const.D_0);
        return Utils.roundToDouble(v, scale);
    }

    /**
     * 获取数据平均值
     *
     * @param f     f
     * @param scale 小数位
     * @return data
     */
    protected double getAvgReduceData(ToDoubleFunction<T> f, int scale) {
        double v = currentMetrics.stream()
                .mapToDouble(f)
                .average()
                .orElse(Const.D_0);
        return Utils.roundToDouble(v, scale);
    }

}
