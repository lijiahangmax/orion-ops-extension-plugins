package com.orion.ops.machine.monitor.metrics.statistics;

import com.orion.lang.define.wrapper.TimestampValue;
import com.orion.ops.machine.monitor.constant.DataMetricsType;
import com.orion.ops.machine.monitor.entity.bo.DiskIoUsageBO;
import com.orion.ops.machine.monitor.entity.request.MetricsStatisticsRequest;
import com.orion.ops.machine.monitor.entity.vo.DiskMetricsStatisticVO;
import com.orion.ops.machine.monitor.entity.vo.MetricsStatisticsVO;
import com.orion.ops.machine.monitor.utils.Formats;
import com.orion.ops.machine.monitor.utils.Utils;

import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

/**
 * 硬盘数据指标统计
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 18:24
 */
public class DiskMetricsStatisticResolver extends BaseMetricsStatisticResolver<DiskIoUsageBO, DiskMetricsStatisticVO> {

    /**
     * 读取速度
     */
    private final MetricsStatisticsVO<Double> readSpeed;

    /**
     * 写入速度
     */
    private final MetricsStatisticsVO<Double> writeSpeed;

    /**
     * 读取次数
     */
    private final MetricsStatisticsVO<Long> readCount;

    /**
     * 写入次数
     */
    private final MetricsStatisticsVO<Long> writeCount;

    /**
     * 使用时间
     */
    private final MetricsStatisticsVO<Long> usageTime;

    public DiskMetricsStatisticResolver(MetricsStatisticsRequest request) {
        super(request, DataMetricsType.DISK, new DiskMetricsStatisticVO());
        this.readSpeed = new MetricsStatisticsVO<>();
        this.writeSpeed = new MetricsStatisticsVO<>();
        this.readCount = new MetricsStatisticsVO<>();
        this.writeCount = new MetricsStatisticsVO<>();
        this.usageTime = new MetricsStatisticsVO<>();
        metrics.setReadSpeed(readSpeed);
        metrics.setWriteSpeed(writeSpeed);
        metrics.setReadCount(readCount);
        metrics.setWriteCount(writeCount);
        metrics.setUsageTime(usageTime);
    }

    @Override
    protected void computeMetricsData(List<DiskIoUsageBO> rows, Long start, Long end) {
        long s = this.computeRowsSecond(rows);
        long totalReadSize = Utils.getLongStream(rows, DiskIoUsageBO::getRs).sum();
        long totalWriteSize = Utils.getLongStream(rows, DiskIoUsageBO::getWs).sum();
        long totalReadCount = Utils.getLongStream(rows, DiskIoUsageBO::getRc).sum();
        long totalWriteCount = Utils.getLongStream(rows, DiskIoUsageBO::getWc).sum();
        long totalUsageTime = Utils.getLongStream(rows, DiskIoUsageBO::getUt).sum();
        readSpeed.getMetrics().add(new TimestampValue<>(start, Formats.roundToDouble((double) totalReadSize / s, 3)));
        writeSpeed.getMetrics().add(new TimestampValue<>(start, Formats.roundToDouble((double) totalWriteSize / s, 3)));
        readCount.getMetrics().add(new TimestampValue<>(start, totalReadCount / s));
        writeCount.getMetrics().add(new TimestampValue<>(start, totalWriteCount / s));
        usageTime.getMetrics().add(new TimestampValue<>(start, totalUsageTime));
    }

    @Override
    protected void computeMetricsMax() {
        double readSpeedMax = super.calcDataReduce(readSpeed.getMetrics(), DoubleStream::max);
        double writeSpeedMax = super.calcDataReduce(writeSpeed.getMetrics(), DoubleStream::max);
        long readCountMax = super.calcDataReduceLong(readCount.getMetrics(), LongStream::max);
        long writeCountMax = super.calcDataReduceLong(writeCount.getMetrics(), LongStream::max);
        long usageTimeMax = super.calcDataReduceLong(usageTime.getMetrics(), LongStream::max);
        readSpeed.setMax(readSpeedMax);
        writeSpeed.setMax(writeSpeedMax);
        readCount.setMax(readCountMax);
        writeCount.setMax(writeCountMax);
        usageTime.setMax(usageTimeMax);
    }

    @Override
    protected void computeMetricsMin() {
        double readSpeedMin = super.calcDataReduce(readSpeed.getMetrics(), DoubleStream::min);
        double writeSpeedMin = super.calcDataReduce(writeSpeed.getMetrics(), DoubleStream::min);
        long readCountMin = super.calcDataReduceLong(readCount.getMetrics(), LongStream::min);
        long writeCountMin = super.calcDataReduceLong(writeCount.getMetrics(), LongStream::min);
        long usageTimeMin = super.calcDataReduceLong(usageTime.getMetrics(), LongStream::min);
        readSpeed.setMin(readSpeedMin);
        writeSpeed.setMin(writeSpeedMin);
        readCount.setMin(readCountMin);
        writeCount.setMin(writeCountMin);
        usageTime.setMin(usageTimeMin);
    }

    @Override
    protected void computeMetricsAvg() {
        double readSpeedAvg = super.calcDataAvg(readSpeed.getMetrics());
        double writeSpeedAvg = super.calcDataAvg(writeSpeed.getMetrics());
        double readCountAvg = super.calcDataAvgLong(readCount.getMetrics());
        double writeCountAvg = super.calcDataAvgLong(writeCount.getMetrics());
        double usageTimeAvg = super.calcDataAvgLong(usageTime.getMetrics());
        readSpeed.setAvg(readSpeedAvg);
        writeSpeed.setAvg(writeSpeedAvg);
        readCount.setAvg(readCountAvg);
        writeCount.setAvg(writeCountAvg);
        usageTime.setAvg(usageTimeAvg);
    }

}
