package com.orion.ops.plugin.agent.metrics.statistics;

import com.orion.lang.define.wrapper.TimestampValue;
import com.orion.ops.plugin.agent.constant.DataMetricsType;
import com.orion.ops.plugin.agent.utils.Utils;
import com.orion.ops.plugin.common.entity.agent.bo.NetBandwidthBO;
import com.orion.ops.plugin.common.entity.agent.request.MetricsStatisticsRequest;
import com.orion.ops.plugin.common.entity.agent.vo.MetricsStatisticsVO;
import com.orion.ops.plugin.common.entity.agent.vo.NetBandwidthMetricsStatisticVO;

import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

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
    private final MetricsStatisticsVO<Long> sentPacket;

    /**
     * 下行包数
     */
    private final MetricsStatisticsVO<Long> recvPacket;

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
        long totalSentSize = Utils.getLongStream(rows, NetBandwidthBO::getSs).sum();
        long totalRecvSize = Utils.getLongStream(rows, NetBandwidthBO::getRs).sum();
        long totalSentPacket = Utils.getLongStream(rows, NetBandwidthBO::getSp).sum();
        long totalRecvPacket = Utils.getLongStream(rows, NetBandwidthBO::getRp).sum();
        sentSpeed.getMetrics().add(new TimestampValue<>(start, Utils.computeMpbSecondSpeed(s, totalSentSize)));
        recvSpeed.getMetrics().add(new TimestampValue<>(start, Utils.computeMpbSecondSpeed(s, totalRecvSize)));
        sentPacket.getMetrics().add(new TimestampValue<>(start, totalSentPacket));
        recvPacket.getMetrics().add(new TimestampValue<>(start, totalRecvPacket));
    }

    @Override
    protected void computeMetricsMax() {
        double sentSpeedMax = super.calcDataAgg(sentSpeed.getMetrics(), DoubleStream::max, 5);
        double recvSpeedMax = super.calcDataAgg(recvSpeed.getMetrics(), DoubleStream::max, 5);
        long sentPacketMax = super.calcDataAggLong(sentPacket.getMetrics(), LongStream::max);
        long recvPacketMax = super.calcDataAggLong(recvPacket.getMetrics(), LongStream::max);
        sentSpeed.setMax(sentSpeedMax);
        recvSpeed.setMax(recvSpeedMax);
        sentPacket.setMax(sentPacketMax);
        recvPacket.setMax(recvPacketMax);
    }

    @Override
    protected void computeMetricsMin() {
        double sentSpeedMin = super.calcDataAgg(sentSpeed.getMetrics(), DoubleStream::min, 5);
        double recvSpeedMin = super.calcDataAgg(recvSpeed.getMetrics(), DoubleStream::min, 5);
        long sentPacketMin = super.calcDataAggLong(sentPacket.getMetrics(), LongStream::min);
        long recvPacketMin = super.calcDataAggLong(recvPacket.getMetrics(), LongStream::min);
        sentSpeed.setMin(sentSpeedMin);
        recvSpeed.setMin(recvSpeedMin);
        sentPacket.setMin(sentPacketMin);
        recvPacket.setMin(recvPacketMin);
    }

    @Override
    protected void computeMetricsAvg() {
        double sentSpeedAvg = super.calcDataAgg(sentSpeed.getMetrics(), DoubleStream::average, 5);
        double recvSpeedAvg = super.calcDataAgg(recvSpeed.getMetrics(), DoubleStream::average, 5);
        double sentPacketAvg = super.calcDataAvgLong(sentPacket.getMetrics());
        double recvPacketAvg = super.calcDataAvgLong(recvPacket.getMetrics());
        sentSpeed.setAvg(sentSpeedAvg);
        recvSpeed.setAvg(recvSpeedAvg);
        sentPacket.setAvg(sentPacketAvg);
        recvPacket.setAvg(recvPacketAvg);
    }

}
