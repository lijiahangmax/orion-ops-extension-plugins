package com.orion.ops.machine.monitor.metrics.reduce;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsingBO;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsingHourReduceBO;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 内存时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/3 22:45
 */
@Slf4j
@Component
public class MemoryHourReduceResolver extends BaseMetricsHourReduceResolver<MemoryUsingBO, MemoryUsingHourReduceBO> {

    public MemoryHourReduceResolver() {
        super((prevHour) -> PathBuilders.getMemoryMonthDataPath(Utils.getRangeStartMonth(prevHour)));
    }

    @Override
    protected MemoryUsingHourReduceBO computeHourReduceData(String currentHour, String prevHour) {
        double maxur = currentMetrics.stream()
                .mapToDouble(MemoryUsingBO::getUr)
                .max()
                .orElse(Const.D_0);
        double minur = currentMetrics.stream()
                .mapToDouble(MemoryUsingBO::getUr)
                .min()
                .orElse(Const.D_0);
        double avgur = currentMetrics.stream()
                .mapToDouble(MemoryUsingBO::getUr)
                .average()
                .orElse(Const.D_0);
        long maxus = currentMetrics.stream()
                .mapToLong(MemoryUsingBO::getUs)
                .max()
                .orElse(Const.L_0);
        long minus = currentMetrics.stream()
                .mapToLong(MemoryUsingBO::getUs)
                .min()
                .orElse(Const.L_0);
        double avgus = currentMetrics.stream()
                .mapToLong(MemoryUsingBO::getUs)
                .average()
                .orElse(Const.D_0);
        // 设置规约数据
        MemoryUsingHourReduceBO reduce = new MemoryUsingHourReduceBO();
        reduce.setMaxur(Utils.roundToDouble(maxur, 3));
        reduce.setMinur(Utils.roundToDouble(minur, 3));
        reduce.setAvgur(Utils.roundToDouble(avgur, 3));
        reduce.setMaxus(maxus);
        reduce.setMinus(minus);
        reduce.setAvgus((long) avgus);
        log.info("计算内存小时级指标: {}", JSON.toJSONString(reduce));
        return reduce;
    }

}
