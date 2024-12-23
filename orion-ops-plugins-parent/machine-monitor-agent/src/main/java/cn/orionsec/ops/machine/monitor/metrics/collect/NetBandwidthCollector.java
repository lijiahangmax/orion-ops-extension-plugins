/*
 * Copyright (c) 2021 - present Jiahang Li, All rights reserved.
 *
 *   https://om.orionsec.cn
 *
 * Members:
 *   Jiahang Li - ljh1553488six@139.com - author
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.orionsec.ops.machine.monitor.metrics.collect;

import cn.orionsec.kit.lang.utils.time.Dates;
import cn.orionsec.ops.machine.monitor.constant.Const;
import cn.orionsec.ops.machine.monitor.entity.bo.NetBandwidthBO;
import cn.orionsec.ops.machine.monitor.metrics.MetricsProvider;
import cn.orionsec.ops.machine.monitor.utils.PathBuilders;
import cn.orionsec.ops.machine.monitor.utils.Utils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 网络带宽指标收集器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 13:43
 */
@Slf4j
@Order(520)
@Component
public class NetBandwidthCollector implements IMetricsCollector<NetBandwidthBO> {

    @Resource
    private MetricsProvider metricsProvider;

    private HardwareAbstractionLayer hardware;

    /**
     * 上次采集网卡信息
     */
    private List<NetworkIF> prevNetworks;

    /**
     * 上次采集网卡信息时间
     */
    private long prevTime;

    @PostConstruct
    private void initCollector() {
        log.info("初始化网络带宽指标收集器");
        this.hardware = metricsProvider.getHardware();
        this.prevNetworks = hardware.getNetworkIFs();
        this.prevTime = System.currentTimeMillis();
    }

    @Override
    public NetBandwidthBO collect() {
        List<NetworkIF> prevNetwork = this.prevNetworks;
        long prevTime = this.prevTime;
        List<NetworkIF> currentNetwork = this.prevNetworks = hardware.getNetworkIFs();
        long currentTime = this.prevTime = System.currentTimeMillis();
        // 统计流量信息
        long beforeReceiveSize = prevNetwork.stream().mapToLong(NetworkIF::getBytesRecv).sum();
        long beforeReceivePacket = prevNetwork.stream().mapToLong(NetworkIF::getPacketsRecv).sum();
        long beforeSentSize = prevNetwork.stream().mapToLong(NetworkIF::getBytesSent).sum();
        long beforeSentPacket = prevNetwork.stream().mapToLong(NetworkIF::getPacketsSent).sum();
        long currentReceiveSize = currentNetwork.stream().mapToLong(NetworkIF::getBytesRecv).sum();
        long currentReceivePacket = currentNetwork.stream().mapToLong(NetworkIF::getPacketsRecv).sum();
        long currentSentSize = currentNetwork.stream().mapToLong(NetworkIF::getBytesSent).sum();
        long currentSentPacket = currentNetwork.stream().mapToLong(NetworkIF::getPacketsSent).sum();
        // 计算
        NetBandwidthBO net = new NetBandwidthBO();
        net.setSr(Dates.getSecondTime(prevTime));
        net.setEr(Dates.getSecondTime(currentTime));
        net.setSs((currentSentSize - beforeSentSize) / Const.BUFFER_KB_1);
        net.setRs((currentReceiveSize - beforeReceiveSize) / Const.BUFFER_KB_1);
        net.setSp(currentSentPacket - beforeSentPacket);
        net.setRp(currentReceivePacket - beforeReceivePacket);
        log.debug("网络带宽指标: {}", JSON.toJSONString(net));
        // 拼接到天级数据
        String path = PathBuilders.getNetDayDataPath(Utils.getRangeStartTime(net.getSr()));
        Utils.appendMetricsData(path, net);
        return net;
    }

}
