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
package cn.orionsec.ops.machine.monitor.metrics.reduce;

import cn.orionsec.kit.lang.utils.collect.Lists;
import cn.orionsec.kit.lang.utils.collect.Maps;
import cn.orionsec.ops.machine.monitor.constant.Const;
import cn.orionsec.ops.machine.monitor.entity.bo.DiskIoUsageBO;
import cn.orionsec.ops.machine.monitor.utils.PathBuilders;
import cn.orionsec.ops.machine.monitor.utils.Utils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 硬盘时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 10:51
 */
@Slf4j
@Component
public class DiskHourReduceResolver implements IMetricsHourReduceResolver<DiskIoUsageBO> {

    /**
     * 当前采集信息粒度时
     */
    private final Map<String, String> currentHours;

    /**
     * 当前采集信息
     */
    private final Map<String, List<DiskIoUsageBO>> currentMetrics;

    public DiskHourReduceResolver() {
        this.currentHours = Maps.newMap();
        this.currentMetrics = Maps.newMap();
    }

    @Override
    public void reduce(DiskIoUsageBO data) {
        String seq = data.getSeq();
        String currentHour = Utils.getRangeStartHour(data);
        String prevHour = currentHours.computeIfAbsent(seq, k -> currentHour);
        List<DiskIoUsageBO> list = currentMetrics.computeIfAbsent(seq, k -> Lists.newList());
        // 同一时间
        if (currentHour.equals(prevHour)) {
            list.add(data);
            return;
        }
        // 不同时间则规约
        currentHours.put(seq, currentHour);
        // 计算数据
        DiskIoUsageBO reduceData = new DiskIoUsageBO();
        reduceData.setRc(list.stream().mapToLong(DiskIoUsageBO::getRc).sum());
        reduceData.setRs(list.stream().mapToLong(DiskIoUsageBO::getRs).map(s -> s / Const.BUFFER_KB_1).sum());
        reduceData.setWc(list.stream().mapToLong(DiskIoUsageBO::getWc).sum());
        reduceData.setWs(list.stream().mapToLong(DiskIoUsageBO::getWs).map(s -> s / Const.BUFFER_KB_1).sum());
        reduceData.setUt(list.stream().mapToLong(DiskIoUsageBO::getUt).sum());
        Utils.setReduceHourRange(reduceData, prevHour, currentHour);
        log.debug("硬盘时级数据指标-seq: {} {}", seq, JSON.toJSONString(reduceData));
        list.clear();
        list.add(data);
        // 拼接到月级数据
        String path = PathBuilders.getDiskMonthDataPath(Utils.getRangeStartMonth(prevHour), seq);
        Utils.appendMetricsData(path, reduceData);
    }

}
