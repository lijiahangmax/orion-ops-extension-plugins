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

import cn.orionsec.kit.spring.SpringHolder;
import cn.orionsec.ops.machine.monitor.entity.bo.BaseRangeBO;
import lombok.AllArgsConstructor;

/**
 * 数据指标采集类型
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 11:28
 */
@AllArgsConstructor
public enum MetricsCollectorType {

    /**
     * 处理器
     */
    CPU(CpuMetricsCollector.class),

    /**
     * 内存
     */
    MEMORY(MemoryMetricsCollector.class),

    /**
     * 网络带宽
     */
    NET(NetBandwidthCollector.class),

    /**
     * 硬盘
     */
    DISK(DiskMetricsCollector.class),

    ;

    private final Class<? extends IMetricsCollector<?>> beanClass;

    /**
     * 获取采集器 bean
     */
    @SuppressWarnings("unchecked")
    public <T extends IMetricsCollector<? extends BaseRangeBO>> T getCollectBean() {
        return (T) SpringHolder.getBean(beanClass);
    }

}
