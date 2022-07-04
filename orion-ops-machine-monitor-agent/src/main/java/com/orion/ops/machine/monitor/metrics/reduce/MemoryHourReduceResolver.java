package com.orion.ops.machine.monitor.metrics.reduce;

import com.alibaba.fastjson.JSON;
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
        // 设置规约数据
        MemoryUsingHourReduceBO reduce = new MemoryUsingHourReduceBO();
        reduce.setMaxur(this.getMaxReduceData(MemoryUsingBO::getUr, 3));
        reduce.setMinur(this.getMinReduceData(MemoryUsingBO::getUr, 3));
        reduce.setAvgur(this.getAvgReduceData(MemoryUsingBO::getUr, 3));
        reduce.setMaxus((long) this.getMaxReduceData(MemoryUsingBO::getUs, 0));
        reduce.setMinus((long) this.getMinReduceData(MemoryUsingBO::getUs, 0));
        reduce.setAvgus((long) this.getAvgReduceData(MemoryUsingBO::getUs, 0));
        log.info("计算内存小时级指标: {}", JSON.toJSONString(reduce));
        return reduce;
    }

}
