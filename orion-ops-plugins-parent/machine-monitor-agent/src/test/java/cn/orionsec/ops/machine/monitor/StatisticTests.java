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
package cn.orionsec.ops.machine.monitor;

import cn.orionsec.ops.machine.monitor.metrics.statistics.CpuMetricsStatisticResolver;
import cn.orionsec.ops.machine.monitor.metrics.statistics.DiskMetricsStatisticResolver;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import cn.orionsec.kit.lang.utils.time.Dates;
import cn.orionsec.ops.machine.monitor.constant.Currents;
import cn.orionsec.ops.machine.monitor.constant.GranularityType;
import cn.orionsec.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import cn.orionsec.ops.machine.monitor.metrics.statistics.MemoryMetricsStatisticResolver;
import cn.orionsec.ops.machine.monitor.metrics.statistics.NetMetricsStatisticResolver;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/6 11:11
 */
public class StatisticTests {

    private MetricsStatisticsRequest req;

    @Before
    public void setup() {
        this.req = new MetricsStatisticsRequest();
        long s = Dates.parse("202207061110").getTime() / 1000;
        long e = Dates.parse("202207061115").getTime() / 1000;
        req.setStartRange(s);
        req.setEndRange(e);
        req.setGranularity(GranularityType.MINUTE_1.getType());
        Currents.setDiskSeq("e43c18c4");
    }

    @Test
    public void cpu() {
        CpuMetricsStatisticResolver res = new CpuMetricsStatisticResolver(req);
        res.statistics();
        System.out.println(JSON.toJSONString(res.getMetrics()));
    }

    @Test
    public void mem() {
        MemoryMetricsStatisticResolver res = new MemoryMetricsStatisticResolver(req);
        res.statistics();
        System.out.println(JSON.toJSONString(res.getMetrics()));
    }

    @Test
    public void net() {
        NetMetricsStatisticResolver res = new NetMetricsStatisticResolver(req);
        res.statistics();
        System.out.println(JSON.toJSONString(res.getMetrics(), SerializerFeature.WriteBigDecimalAsPlain));
    }

    @Test
    public void disk() {
        DiskMetricsStatisticResolver res = new DiskMetricsStatisticResolver(req);
        res.statistics();
        System.out.println(JSON.toJSONString(res.getMetrics(), SerializerFeature.WriteBigDecimalAsPlain));
    }

}
