package com.orion.ops.monitor.agent.metrics.reduce;

import com.orion.lang.utils.collect.Lists;
import com.orion.ops.monitor.agent.constant.Const;
import com.orion.ops.monitor.agent.utils.Utils;
import com.orion.ops.monitor.common.entity.agent.bo.BaseRangeBO;
import com.orion.ops.monitor.common.utils.Formats;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

/**
 * 时级数据规约器基类
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/3 22:23
 */
public abstract class BaseMetricsHourReduceResolver<T extends BaseRangeBO> implements IMetricsHourReduceResolver<T> {

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
    private final Function<String, String> pathGetter;

    public BaseMetricsHourReduceResolver(Function<String, String> pathGetter) {
        this.currentMetrics = Lists.newList();
        this.pathGetter = pathGetter;
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
        T reduceData = this.computeHourReduceData(currentHour, prevHour);
        Utils.setReduceHourRange(reduceData, prevHour, currentHour);
        currentMetrics.clear();
        currentMetrics.add(data);
        // 拼接到月级数据
        Utils.appendMetricsData(pathGetter.apply(prevHour), reduceData);
    }

    /**
     * 计算规约数据
     *
     * @param currentHour currentHour
     * @param prevHour    prevHour
     * @return reduce
     */
    protected abstract T computeHourReduceData(String currentHour, String prevHour);

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
        return Formats.roundToDouble(v, scale);
    }

    /**
     * 获取数据平均值
     *
     * @param f f
     * @return data
     */
    protected long getAvgReduceData(ToLongFunction<T> f) {
        return (long) currentMetrics.stream()
                .mapToLong(f)
                .average()
                .orElse(Const.D_0);
    }

    /**
     * 获取数据累计值
     *
     * @param f f
     * @return data
     */
    protected long getSumReduceData(ToLongFunction<T> f) {
        return currentMetrics.stream()
                .mapToLong(f)
                .sum();
    }

}
