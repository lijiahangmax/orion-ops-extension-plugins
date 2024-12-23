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

import cn.orionsec.ops.machine.monitor.constant.SchedulerPools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 机器指标采集调度器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 14:40
 */
@Slf4j
@Order(600)
@Component
public class MetricsCollectScheduler {

    @Value("${collect.period.second}")
    private Integer collectPeriodSecond;

    @Resource
    private MetricsCollectTask metricsCollectTask;

    @PostConstruct
    private void initScheduler() {
        log.info("初始化数据采集调度器 采集周期: {}s", collectPeriodSecond);
        SchedulerPools.COLLECT_SCHEDULER.scheduleAtFixedRate(metricsCollectTask, collectPeriodSecond, collectPeriodSecond, TimeUnit.SECONDS);
    }

}
