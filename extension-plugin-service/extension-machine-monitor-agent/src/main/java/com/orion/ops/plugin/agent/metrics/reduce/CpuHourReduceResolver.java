package com.orion.ops.plugin.agent.metrics.reduce;

import com.alibaba.fastjson.JSON;
import com.orion.ops.plugin.agent.utils.PathBuilders;
import com.orion.ops.plugin.agent.utils.Utils;
import com.orion.ops.plugin.common.entity.agent.bo.CpuUsageBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 处理器时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/2 16:09
 */
@Slf4j
@Component
public class CpuHourReduceResolver extends BaseMetricsHourReduceResolver<CpuUsageBO> {

    public CpuHourReduceResolver() {
        super((prevHour) -> PathBuilders.getCpuMonthDataPath(Utils.getRangeStartMonth(prevHour)));
    }

    @Override
    protected CpuUsageBO computeHourReduceData(String currentHour, String prevHour) {
        // 设置规约数据
        CpuUsageBO reduce = new CpuUsageBO();
        reduce.setU(this.getAvgReduceData(CpuUsageBO::getU, 3));
        log.debug("处理器时级数据指标 {}", JSON.toJSONString(reduce));
        return reduce;
    }

}
