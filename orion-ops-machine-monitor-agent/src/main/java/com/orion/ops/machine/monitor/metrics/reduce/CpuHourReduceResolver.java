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
public class CpuHourReduceResolver extends BaseMetricsHourReduceResolver<CpuUsingBO, CpuUsingHourReduceBO> {

    public CpuHourReduceResolver() {
        super((prevHour) -> PathBuilders.getCpuMonthDataPath(Utils.getRangeStartMonth(prevHour)));
    }

    @Override
    protected CpuUsingHourReduceBO computeHourReduceData(String currentHour, String prevHour) {
        // 设置规约数据
        CpuUsingHourReduceBO reduce = new CpuUsingHourReduceBO();
        reduce.setMax(this.getMaxReduceData(CpuUsingBO::getU, 3));
        reduce.setMin(this.getMinReduceData(CpuUsingBO::getU, 3));
        reduce.setAvg(this.getAvgReduceData(CpuUsingBO::getU, 3));
        log.info("计算处理器小时级指标: {}", JSON.toJSONString(reduce));
        return reduce;
    }

}
