package com.orion.ops.machine.monitor.metrics.statistics;

import com.orion.lang.define.wrapper.TimestampValue;
import com.orion.ops.machine.monitor.constant.DataMetricsType;
import com.orion.ops.machine.monitor.entity.bo.NetBandwidthBO;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.MetricsStatisticsVO;
import com.orion.ops.machine.monitor.entity.vo.NetBandwidthMetricsStatisticVO;
import com.orion.ops.machine.monitor.utils.Utils;

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
     * 上行速率 mbp/s
     */
    private final MetricsStatisticsVO<Double> sentSpeed;

    /**
     * 下行速率 mbp/s
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
        long s = this.computeRowsSecond(rows);
        long totalSentSize = Utils.getLongStream(rows, NetBandwidthBO::getSs).sum();
        long totalRecvSize = Utils.getLongStream(rows, NetBandwidthBO::getRs).sum();
        long totalSentPacket = Utils.getLongStream(rows, NetBandwidthBO::getSp).sum();
        long totalRecvPacket = Utils.getLongStream(rows, NetBandwidthBO::getRp).sum();
        sentSpeed.getMetrics().add(new TimestampValue<>(start, Utils.computeMbpSecondSpeed(s, totalSentSize)));
        recvSpeed.getMetrics().add(new TimestampValue<>(start, Utils.computeMbpSecondSpeed(s, totalRecvSize)));
        sentPacket.getMetrics().add(new TimestampValue<>(start, totalSentPacket / s));
        recvPacket.getMetrics().add(new TimestampValue<>(start, totalRecvPacket / s));
    }

    @Override
    protected void computeMetricsMax() {
        double sentSpeedMax = super.calcDataReduce(sentSpeed.getMetrics(), DoubleStream::max);
        double recvSpeedMax = super.calcDataReduce(recvSpeed.getMetrics(), DoubleStream::max);
        long sentPacketMax = super.calcDataReduceLong(sentPacket.getMetrics(), LongStream::max);
        long recvPacketMax = super.calcDataReduceLong(recvPacket.getMetrics(), LongStream::max);
        sentSpeed.setMax(sentSpeedMax);
        recvSpeed.setMax(recvSpeedMax);
        sentPacket.setMax(sentPacketMax);
        recvPacket.setMax(recvPacketMax);
    }

    @Override
    protected void computeMetricsMin() {
        double sentSpeedMin = super.calcDataReduce(sentSpeed.getMetrics(), DoubleStream::min);
        double recvSpeedMin = super.calcDataReduce(recvSpeed.getMetrics(), DoubleStream::min);
        long sentPacketMin = super.calcDataReduceLong(sentPacket.getMetrics(), LongStream::min);
        long recvPacketMin = super.calcDataReduceLong(recvPacket.getMetrics(), LongStream::min);
        sentSpeed.setMin(sentSpeedMin);
        recvSpeed.setMin(recvSpeedMin);
        sentPacket.setMin(sentPacketMin);
        recvPacket.setMin(recvPacketMin);
    }

    @Override
    protected void computeMetricsAvg() {
        double sentSpeedAvg = super.calcDataAvg(sentSpeed.getMetrics());
        double recvSpeedAvg = super.calcDataAvg(recvSpeed.getMetrics());
        double sentPacketAvg = super.calcDataAvgLong(sentPacket.getMetrics());
        double recvPacketAvg = super.calcDataAvgLong(recvPacket.getMetrics());
        sentSpeed.setAvg(sentSpeedAvg);
        recvSpeed.setAvg(recvSpeedAvg);
        sentPacket.setAvg(sentPacketAvg);
        recvPacket.setAvg(recvPacketAvg);
    }

}
