package com.orion.ops.machine.monitor.metrics.statistics;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.constant.DataMetricsType;
import com.orion.ops.machine.monitor.constant.GranularityType;
import com.orion.ops.machine.monitor.entity.bo.NetBandwidthBO;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.MetricsStatisticsVO;
import com.orion.ops.machine.monitor.entity.vo.NetBandwidthMetricsStatisticVO;
import com.orion.ops.machine.monitor.utils.TimestampValue;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.time.Dates;

import java.util.List;
import java.util.stream.DoubleStream;

/**
 * 网络带宽数据指标统计
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 15:42
 */
public class NetMetricsStatisticResolver extends BaseMetricsStatisticResolver<NetBandwidthBO, NetBandwidthMetricsStatisticVO> {

    /**
     * 上行速率 mpb/s
     */
    private final MetricsStatisticsVO<Double> sentSpeed;

    /**
     * 下行速率 mpb/s
     */
    private final MetricsStatisticsVO<Double> recvSpeed;

    /**
     * 上行包数
     */
    private final MetricsStatisticsVO<Double> sentPacket;

    /**
     * 下行包数
     */
    private final MetricsStatisticsVO<Double> recvPacket;

    public NetMetricsStatisticResolver(MetricsStatisticsRequest request) {
        super(request, DataMetricsType.NET, new NetBandwidthMetricsStatisticVO());
        this.sentSpeed = new MetricsStatisticsVO<>();
        this.recvSpeed = new MetricsStatisticsVO<>();
        this.sentPacket = new MetricsStatisticsVO<>();
        this.recvPacket = new MetricsStatisticsVO<>();
        metrics.setSentSpeed(sentSpeed);
        metrics.setRecvSpeed(recvSpeed);
        metrics.setSentPacket(sentPacket);
        metrics.setRecvPacket(recvPacket);
    }

    @Override
    protected void computeMetricsData(List<NetBandwidthBO> rows, Long start, Long end) {
        long s = end - start;
        long totalSentSize = rows.stream().mapToLong(NetBandwidthBO::getSk).sum();
        long totalRecvSize = rows.stream().mapToLong(NetBandwidthBO::getRk).sum();
        long totalSentPacket = rows.stream().mapToLong(NetBandwidthBO::getSp).sum();
        long totalRecvPacket = rows.stream().mapToLong(NetBandwidthBO::getRp).sum();
        sentSpeed.getMetrics().add(new TimestampValue<>(start, Utils.computeMpbSecondSpeed(s, totalSentSize)));
        recvSpeed.getMetrics().add(new TimestampValue<>(start, Utils.computeMpbSecondSpeed(s, totalRecvSize)));
        sentPacket.getMetrics().add(new TimestampValue<>(start, Utils.roundToDouble((double) totalSentPacket / s, 3)));
        recvPacket.getMetrics().add(new TimestampValue<>(start, Utils.roundToDouble((double) totalRecvPacket / s, 3)));
    }

    @Override
    protected void computeMetricsMax() {
        double upSpeedMax = super.calcDataAgg(sentSpeed.getMetrics(), DoubleStream::max, 5);
        double downSpeedMax = super.calcDataAgg(recvSpeed.getMetrics(), DoubleStream::max, 5);
        double upPacketMax = super.calcDataAgg(sentPacket.getMetrics(), DoubleStream::max, 3);
        double downPacketMax = super.calcDataAgg(recvPacket.getMetrics(), DoubleStream::max, 3);
        sentSpeed.setMax(upSpeedMax);
        recvSpeed.setMax(downSpeedMax);
        sentPacket.setMax(upPacketMax);
        recvPacket.setMax(downPacketMax);
    }

    @Override
    protected void computeMetricsMin() {
        double upSpeedMin = super.calcDataAgg(sentSpeed.getMetrics(), DoubleStream::min, 5);
        double downSpeedMin = super.calcDataAgg(recvSpeed.getMetrics(), DoubleStream::min, 5);
        double upPacketMin = super.calcDataAgg(sentPacket.getMetrics(), DoubleStream::min, 3);
        double downPacketMin = super.calcDataAgg(recvPacket.getMetrics(), DoubleStream::min, 3);
        sentSpeed.setMin(upSpeedMin);
        recvSpeed.setMin(downSpeedMin);
        sentPacket.setMin(upPacketMin);
        recvPacket.setMin(downPacketMin);
    }

    @Override
    protected void computeMetricsAvg() {
        double upSpeedAvg = super.calcDataAgg(sentSpeed.getMetrics(), DoubleStream::average, 5);
        double downSpeedAvg = super.calcDataAgg(recvSpeed.getMetrics(), DoubleStream::average, 5);
        double upPacketAvg = super.calcDataAgg(sentPacket.getMetrics(), DoubleStream::average, 3);
        double downPacketAvg = super.calcDataAgg(recvPacket.getMetrics(), DoubleStream::average, 3);
        sentSpeed.setAvg(upSpeedAvg);
        recvSpeed.setAvg(downSpeedAvg);
        sentPacket.setAvg(upPacketAvg);
        recvPacket.setAvg(downPacketAvg);
    }

    public static void main(String[] args) {
        long s = Dates.parse("202207051601").getTime();
        long e = Dates.parse("202207051603").getTime();
        MetricsStatisticsRequest r = new MetricsStatisticsRequest();
        r.setStartRange(s);
        r.setEndRange(e);
        r.setGranularity(GranularityType.MINUTE_1.getType());
        NetMetricsStatisticResolver res = new NetMetricsStatisticResolver(r);
        res.statistics();
        System.out.println();
        System.out.println(JSON.toJSONString(res.getMetrics()));
        System.out.println();
    }

}
