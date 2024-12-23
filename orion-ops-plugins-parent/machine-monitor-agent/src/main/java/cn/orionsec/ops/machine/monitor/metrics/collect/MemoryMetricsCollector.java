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
import cn.orionsec.ops.machine.monitor.entity.bo.MemoryUsageBO;
import cn.orionsec.ops.machine.monitor.metrics.MetricsProvider;
import cn.orionsec.ops.machine.monitor.utils.Formats;
import cn.orionsec.ops.machine.monitor.utils.PathBuilders;
import cn.orionsec.ops.machine.monitor.utils.Utils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.hardware.GlobalMemory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 内存指标收集器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 12:34
 */
@Slf4j
@Order(510)
@Component
public class MemoryMetricsCollector implements IMetricsCollector<MemoryUsageBO> {

    @Resource
    private MetricsProvider metricsProvider;

    /**
     * 内存信息
     */
    private GlobalMemory memory;

    /**
     * 上次采集内存信息时间
     */
    private long prevTime;

    @PostConstruct
    private void initCollector() {
        log.info("初始化内存指标收集器");
        this.memory = metricsProvider.getHardware().getMemory();
        this.prevTime = System.currentTimeMillis();
    }

    @Override
    public MemoryUsageBO collect() {
        long prevTime = this.prevTime;
        long total = memory.getTotal();
        long usage = total - memory.getAvailable();
        long currentTime = this.prevTime = System.currentTimeMillis();
        // 计算
        MemoryUsageBO mem = new MemoryUsageBO();
        mem.setUr(Formats.roundToDouble((double) usage / (double) total * 100, 3));
        mem.setUs(usage / Const.BUFFER_KB_1 / Const.BUFFER_KB_1);
        mem.setSr(Dates.getSecondTime(prevTime));
        mem.setEr(Dates.getSecondTime(currentTime));
        log.debug("内存指标: {}", JSON.toJSONString(mem));
        // 拼接到天级数据
        String path = PathBuilders.getMemoryDayDataPath(Utils.getRangeStartTime(mem.getSr()));
        Utils.appendMetricsData(path, mem);
        return mem;
    }

}
