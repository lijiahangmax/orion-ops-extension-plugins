package com.orion.ops.plugin.agent.metrics.reduce;

import com.alibaba.fastjson.JSON;
import com.orion.ops.plugin.agent.constant.Const;
import com.orion.ops.plugin.agent.utils.PathBuilders;
import com.orion.ops.plugin.agent.utils.Utils;
import com.orion.ops.plugin.common.entity.agent.bo.NetBandwidthBO;
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
public class NetBandwidthHourReduceResolver extends BaseMetricsHourReduceResolver<NetBandwidthBO> {

    public NetBandwidthHourReduceResolver() {
        super((prevHour) -> PathBuilders.getNetMonthDataPath(Utils.getRangeStartMonth(prevHour)));
    }

    @Override
    protected NetBandwidthBO computeHourReduceData(String currentHour, String prevHour) {
        // 设置规约数据
        NetBandwidthBO reduce = new NetBandwidthBO();
        reduce.setSs(this.getSumReduceData(NetBandwidthBO::getSs) / Const.BUFFER_KB_1);
        reduce.setRs(this.getSumReduceData(NetBandwidthBO::getRs) / Const.BUFFER_KB_1);
        reduce.setSp(this.getSumReduceData(NetBandwidthBO::getSp));
        reduce.setRp(this.getSumReduceData(NetBandwidthBO::getRp));
        log.debug("网络带宽时级数据指标 {}", JSON.toJSONString(reduce));
        return reduce;
    }

}
