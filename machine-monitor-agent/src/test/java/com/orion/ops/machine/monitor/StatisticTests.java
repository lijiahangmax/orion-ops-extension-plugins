package com.orion.ops.machine.monitor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.orion.lang.utils.time.Dates;
import com.orion.ops.machine.monitor.constant.Currents;
import com.orion.ops.machine.monitor.constant.GranularityType;
import com.orion.ops.machine.monitor.entity.agent.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.metrics.statistics.CpuMetricsStatisticResolver;
import com.orion.ops.machine.monitor.metrics.statistics.DiskMetricsStatisticResolver;
import com.orion.ops.machine.monitor.metrics.statistics.MemoryMetricsStatisticResolver;
import com.orion.ops.machine.monitor.metrics.statistics.NetMetricsStatisticResolver;
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
