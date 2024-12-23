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
package cn.orionsec.ops.machine.monitor.metrics.statistics;

import cn.orionsec.kit.lang.define.wrapper.TimestampValue;
import cn.orionsec.ops.machine.monitor.constant.Const;
import cn.orionsec.ops.machine.monitor.constant.DataMetricsType;
import cn.orionsec.ops.machine.monitor.entity.bo.CpuUsageBO;
import cn.orionsec.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import cn.orionsec.ops.machine.monitor.entity.vo.CpuMetricsStatisticsVO;
import cn.orionsec.ops.machine.monitor.entity.vo.MetricsStatisticsVO;
import cn.orionsec.ops.machine.monitor.utils.Formats;
import cn.orionsec.ops.machine.monitor.utils.Utils;

import java.util.List;
import java.util.stream.DoubleStream;

/**
 * 处理器数据指标统计
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:51
 */
public class CpuMetricsStatisticResolver extends BaseMetricsStatisticResolver<CpuUsageBO, CpuMetricsStatisticsVO> {

    /**
     * 使用率
     */
    private final MetricsStatisticsVO<Double> usage;

    public CpuMetricsStatisticResolver(MetricsStatisticsRequest request) {
        super(request, DataMetricsType.CPU, new CpuMetricsStatisticsVO());
        this.usage = new MetricsStatisticsVO<>();
        metrics.setUsage(usage);
    }

    @Override
    protected void computeMetricsData(List<CpuUsageBO> rows, Long start, Long end) {
        double avgUsage = Utils.getDoubleStream(rows, CpuUsageBO::getU)
                .average()
                .orElse(Const.D_0);
        usage.getMetrics().add(new TimestampValue<>(start, Formats.roundToDouble(avgUsage, 3)));
    }

    @Override
    protected void computeMetricsMax() {
        double max = super.calcDataReduce(usage.getMetrics(), DoubleStream::max);
        usage.setMax(max);
    }

    @Override
    protected void computeMetricsMin() {
        double min = super.calcDataReduce(usage.getMetrics(), DoubleStream::min);
        usage.setMin(min);
    }

    @Override
    protected void computeMetricsAvg() {
        double avg = super.calcDataAvg(usage.getMetrics());
        usage.setAvg(avg);
    }

}
