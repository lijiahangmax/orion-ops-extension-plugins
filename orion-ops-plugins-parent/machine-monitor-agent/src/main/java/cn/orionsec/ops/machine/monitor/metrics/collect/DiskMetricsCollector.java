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

import cn.orionsec.kit.lang.utils.collect.Lists;
import cn.orionsec.kit.lang.utils.time.Dates;
import cn.orionsec.ops.machine.monitor.constant.Const;
import cn.orionsec.ops.machine.monitor.entity.bo.DiskIoUsageBO;
import cn.orionsec.ops.machine.monitor.metrics.MetricsProvider;
import cn.orionsec.ops.machine.monitor.utils.PathBuilders;
import cn.orionsec.ops.machine.monitor.utils.Utils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 硬盘带宽指标收集器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 13:52
 */
@Slf4j
@Order(530)
@Component
public class DiskMetricsCollector implements IMetricsCollector<DiskIoUsageBO> {

    @Resource
    private MetricsProvider metricsProvider;

    private HardwareAbstractionLayer hardware;

    /**
     * 上次采集硬盘信息
     */
    private List<HWDiskStore> prevDisks;

    /**
     * 上次采集硬盘信息时间
     */
    private long prevTime;

    @PostConstruct
    private void initCollector() {
        log.info("初始化硬盘指标收集器");
        this.hardware = metricsProvider.getHardware();
        this.prevDisks = this.hardware.getDiskStores();
        this.prevTime = System.currentTimeMillis();
    }

    @Override
    public DiskIoUsageBO collect() {
        return Lists.first(this.collectAsList());
    }

    @Override
    public List<DiskIoUsageBO> collectAsList() {
        List<HWDiskStore> prevDisks = this.prevDisks;
        long prevTime = this.prevTime;
        List<HWDiskStore> currentDisks = this.prevDisks = hardware.getDiskStores();
        long currentTime = this.prevTime = System.currentTimeMillis();
        // 计算
        List<DiskIoUsageBO> list = Lists.newList();
        for (int i = 0; i < currentDisks.size(); i++) {
            HWDiskStore currentDisk = currentDisks.get(i);
            HWDiskStore prevDisk;
            if (prevDisks.size() > i) {
                prevDisk = prevDisks.get(i);
            } else {
                prevDisk = currentDisk;
            }
            // 设置
            String seq = Utils.getDiskSeq(currentDisk.getModel());
            DiskIoUsageBO disk = new DiskIoUsageBO();
            disk.setSeq(seq);
            disk.setRs((currentDisk.getReadBytes() - prevDisk.getReadBytes()) / Const.BUFFER_KB_1);
            disk.setWs((currentDisk.getWriteBytes() - prevDisk.getWriteBytes()) / Const.BUFFER_KB_1);
            disk.setRc(currentDisk.getReads() - prevDisk.getReads());
            disk.setWc(currentDisk.getWrites() - prevDisk.getWrites());
            disk.setUt(currentDisk.getTransferTime() - prevDisk.getTransferTime());
            disk.setSr(Dates.getSecondTime(prevTime));
            disk.setEr(Dates.getSecondTime(currentTime));
            list.add(disk);
            log.debug("硬盘读写指标-{}: {}", seq, JSON.toJSONString(disk));
            // 拼接到天级数据
            String path = PathBuilders.getDiskDayDataPath(Utils.getRangeStartTime(disk.getSr()), disk.getSeq());
            Utils.appendMetricsData(path, disk);
        }
        return list;
    }

}
