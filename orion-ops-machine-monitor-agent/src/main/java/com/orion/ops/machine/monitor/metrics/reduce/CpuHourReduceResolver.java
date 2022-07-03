package com.orion.ops.machine.monitor.metrics.reduce;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingHourReduceBO;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * cpu 时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/2 16:09
 */
@Slf4j
@Component
public class CpuHourReduceResolver extends BaseMetricsHourReduceResolver<CpuUsingBO> {

    public CpuHourReduceResolver() {
        super((prevHour) -> PathBuilders.getCpuMonthDataPath(Utils.getRangeStartMonth(prevHour)));
    }

    @Override
    protected CpuUsingHourReduceBO computeHourReduceData(String currentHour, String prevHour) {
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
        // 设置规约数据
        CpuUsingHourReduceBO reduce = new CpuUsingHourReduceBO();
        reduce.setMax(Utils.roundToDouble(max, 3));
        reduce.setMin(Utils.roundToDouble(min, 3));
        reduce.setAvg(Utils.roundToDouble(avg, 3));
        log.info("计算处理器小时级指标: {}", JSON.toJSONString(reduce));
        return reduce;
    }

}
