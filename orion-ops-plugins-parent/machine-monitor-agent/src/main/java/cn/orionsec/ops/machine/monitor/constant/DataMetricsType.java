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
package cn.orionsec.ops.machine.monitor.constant;

import cn.orionsec.ops.machine.monitor.entity.bo.*;
import cn.orionsec.ops.machine.monitor.utils.PathBuilders;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * 数据指标类型
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 22:51
 */
@Getter
@AllArgsConstructor
public enum DataMetricsType {

    /**
     * 处理器
     */
    CPU(CpuUsageBO.class,
            PathBuilders::getCpuDayDataPath,
            PathBuilders::getCpuMonthDataPath),

    /**
     * 内存
     */
    MEMORY(MemoryUsageBO.class,
            PathBuilders::getMemoryDayDataPath,
            PathBuilders::getMemoryMonthDataPath),

    /**
     * 网络带宽
     */
    NET(NetBandwidthBO.class,
            PathBuilders::getNetDayDataPath,
            PathBuilders::getNetMonthDataPath),

    /**
     * 磁盘
     */
    DISK(DiskIoUsageBO.class,
            d -> PathBuilders.getDiskDayDataPath(d, Currents.getDiskSeq()),
            m -> PathBuilders.getDiskMonthDataPath(m, Currents.getDiskSeq())),

    ;

    private final Class<? extends BaseRangeBO> boClass;

    private final Function<String, String> dayPathGetter;

    private final Function<String, String> monthPathGetter;

}
