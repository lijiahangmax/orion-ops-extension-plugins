package com.orion.ops.machine.monitor.metrics.reduce;

import com.alibaba.fastjson.JSON;
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
        // 设置规约数据
        NetBandwidthHourReduceBO reduce = new NetBandwidthHourReduceBO();
        reduce.setMaxsmr(this.getMaxReduceData(NetBandwidthBO::getSmr, 3));
        reduce.setMinsmr(this.getMinReduceData(NetBandwidthBO::getSmr, 3));
        reduce.setAvgsmr(this.getAvgReduceData(NetBandwidthBO::getSmr, 3));
        reduce.setMaxspr(this.getMaxReduceData(NetBandwidthBO::getSpr, 3));
        reduce.setMinspr(this.getMinReduceData(NetBandwidthBO::getSpr, 3));
        reduce.setAvgspr(this.getAvgReduceData(NetBandwidthBO::getSpr, 3));
        reduce.setMaxrmr(this.getMaxReduceData(NetBandwidthBO::getRmr, 3));
        reduce.setMinrmr(this.getMinReduceData(NetBandwidthBO::getRmr, 3));
        reduce.setAvgrmr(this.getAvgReduceData(NetBandwidthBO::getRmr, 3));
        reduce.setMaxrpr(this.getMaxReduceData(NetBandwidthBO::getRpr, 3));
        reduce.setMinrpr(this.getMinReduceData(NetBandwidthBO::getRpr, 3));
        reduce.setAvgrpr(this.getAvgReduceData(NetBandwidthBO::getRpr, 3));
        log.info("计算网络带宽小时级指标: {}", JSON.toJSONString(reduce));
        return reduce;
    }

}
