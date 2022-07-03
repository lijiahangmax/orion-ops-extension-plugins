package com.orion.ops.machine.monitor.metrics.reduce;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingHourReduceBO;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * cpu 时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/2 16:09
 */
@Slf4j
@Component
public class CpuHourReduceResolver implements MetricsHourReduceResolver<CpuUsingBO> {

    /**
     * 当前采集信息粒度时
     */
    public String currentHour;

    /**
     * 当前采集信息
     */
    private final List<CpuUsingBO> currentMetrics;

    public CpuHourReduceResolver() {
        this.currentMetrics = Lists.newList();
    }

    @Override
    public void reduce(CpuUsingBO data) {
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
        double max = currentMetrics.stream()
                .mapToDouble(CpuUsingBO::getU)
                .max()
                .orElse(Const.D_0);
        double min = currentMetrics.stream()
                .mapToDouble(CpuUsingBO::getU)
                .min()
                .orElse(Const.D_0);
        double avg = currentMetrics.stream()
                .mapToDouble(CpuUsingBO::getU)
                .average()
                .orElse(Const.D_0);
        currentMetrics.clear();
        currentMetrics.add(data);
        // 设置规约数据
        CpuUsingHourReduceBO reduce = new CpuUsingHourReduceBO();
        reduce.setMax(Utils.roundToDouble(max, 3));
        reduce.setMin(Utils.roundToDouble(min, 3));
        reduce.setAvg(Utils.roundToDouble(avg, 3));
        Utils.setReduceHourRange(reduce, prevHour, currentHour);
        log.info("计算处理器小时级指标: {}", JSON.toJSONString(reduce));
        // 拼接到月级数据
        String path = PathBuilders.getCpuMonthDataPath(Utils.getRangeStartMonth(prevHour));
        Utils.appendMetricsData(path, reduce);
    }


}
