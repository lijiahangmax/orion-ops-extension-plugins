package com.orion.ops.machine.monitor.metrics.reduce;

import com.orion.ops.machine.monitor.entity.bo.NetBandwidthBO;
import com.orion.ops.machine.monitor.utils.PathBuilders;
import com.orion.ops.machine.monitor.utils.Utils;
import org.springframework.stereotype.Component;

/**
 * 网络带宽时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/3 23:04
 */
@Component
public class NetBandwidthHourReduceResolver extends BaseMetricsHourReduceResolver<NetBandwidthBO> {

    public NetBandwidthHourReduceResolver() {
        super((prevHour) -> PathBuilders.getNetMonthDataPath(Utils.getRangeStartMonth(prevHour)));
    }

    @Override
    protected NetBandwidthBO computeHourReduceData(String currentHour, String prevHour) {
        // 设置规约数据
        NetBandwidthBO reduce = new NetBandwidthBO();
        reduce.setSk(this.getSumReduceData(NetBandwidthBO::getSk));
        reduce.setRk(this.getSumReduceData(NetBandwidthBO::getRk));
        reduce.setSp(this.getSumReduceData(NetBandwidthBO::getSp));
        reduce.setRp(this.getSumReduceData(NetBandwidthBO::getRp));
        return reduce;
    }

}
