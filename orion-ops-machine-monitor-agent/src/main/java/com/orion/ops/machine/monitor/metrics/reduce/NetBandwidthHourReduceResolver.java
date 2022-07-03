package com.orion.ops.machine.monitor.metrics.reduce;

import com.orion.ops.machine.monitor.entity.bo.NetBandwidthBO;
import com.orion.ops.machine.monitor.entity.bo.NetBandwidthHourReduceBO;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 网络带宽时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/3 23:04
 */
@Slf4j
@Component
public class NetBandwidthHourReduceResolver extends BaseMetricsHourReduceResolver<NetBandwidthBO, NetBandwidthHourReduceBO> {

    public NetBandwidthHourReduceResolver() {
        super((prevHour) -> PathBuilders.getNetMonthDataPath(Utils.getRangeStartMonth(prevHour)));
    }

    @Override
    protected NetBandwidthHourReduceBO computeHourReduceData(String currentHour, String prevHour) {
        return null;
    }

}
