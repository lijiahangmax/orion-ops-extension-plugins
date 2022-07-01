package com.orion.ops.machine.monitor.metrics;

import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.entity.bo.DiskIoUsingBO;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsingBO;
import com.orion.ops.machine.monitor.entity.bo.NetBandwidthBO;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.collect.Maps;
import com.orion.utils.crypto.Signatures;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.hardware.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 机器指标采集器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 18:31
 */
@Slf4j
@Order(410)
@Component
public class MetricsCollector {

    @Resource
    private MetricsProvider metricsProvider;

    @Resource
    private MetricsHolder metricsHolder;

    /**
     * 硬件
     */
    private HardwareAbstractionLayer hardware;

    /**
     * 处理器
     */
    private CentralProcessor processor;

    /**
     * 内存信息
     */
    private GlobalMemory memory;

    @PostConstruct
    private void initCollector() {
        log.info("初始化数据采集器");
        // 设置数据集
        this.hardware = metricsProvider.getHardware();
        this.processor = hardware.getProcessor();
        this.memory = hardware.getMemory();
        // 设置原始数据
        metricsHolder.setPrevCpu(processor.getSystemCpuLoadTicks());
        metricsHolder.setPrevCpuTime(System.currentTimeMillis());
        metricsHolder.setPrevMemoryTime(System.currentTimeMillis());
        metricsHolder.setPrevNetworks(hardware.getNetworkIFs());
        metricsHolder.setPrevNetworksTime(System.currentTimeMillis());
        metricsHolder.setPrevDisks(hardware.getDiskStores());
        metricsHolder.setPrevDisksTime(System.currentTimeMillis());
    }

    /**
     * 收集 cpu 使用信息
     *
     * @return cpu 使用信息
     */
    public CpuUsingBO collectCpu() {
        long[] prevCpu = metricsHolder.getPrevCpu();
        long prevTime = metricsHolder.getPrevCpuTime();
        long[] currentCpu = processor.getSystemCpuLoadTicks();
        long currentTime = System.currentTimeMillis();
        metricsHolder.setPrevCpu(currentCpu);
        metricsHolder.setPrevCpuTime(currentTime);
        // 计算
        CpuUsingBO cpu = new CpuUsingBO();
        cpu.setU(Utils.computeCpuLoad(prevCpu, currentCpu));
        cpu.setSr(prevTime);
        cpu.setEr(currentTime);
        return cpu;
    }

    /**
     * 收集内存使用信息
     *
     * @return 内存使用信息
     */
    public MemoryUsingBO collectMemory() {
        long prevTime = metricsHolder.getPrevMemoryTime();
        long total = memory.getTotal();
        long using = total - memory.getAvailable();
        long currentTime = System.currentTimeMillis();
        metricsHolder.setPrevMemoryTime(currentTime);
        // 计算
        MemoryUsingBO mem = new MemoryUsingBO();
        mem.setUr(Utils.roundToDouble((double) using / (double) total, 3));
        mem.setUs(using / Const.BUFFER_KB_1 / Const.BUFFER_KB_1);
        mem.setSr(prevTime);
        mem.setEr(currentTime);
        return mem;
    }

    /**
     * 收集网络带宽使用信息
     *
     * @return 网络带宽使用信息
     */
    public NetBandwidthBO collectNetBandwidth() {
        List<NetworkIF> prevNetwork = metricsHolder.getPrevNetworks();
        long prevTime = metricsHolder.getPrevNetworksTime();
        List<NetworkIF> currentNetwork = hardware.getNetworkIFs();
        long currentTime = System.currentTimeMillis();
        metricsHolder.setPrevNetworks(currentNetwork);
        metricsHolder.setPrevNetworksTime(currentTime);
        // 统计流量信息
        long beforeReceiveSize = prevNetwork.stream().mapToLong(NetworkIF::getBytesRecv).sum();
        long beforeReceivePacket = prevNetwork.stream().mapToLong(NetworkIF::getPacketsRecv).sum();
        long beforeSentSize = prevNetwork.stream().mapToLong(NetworkIF::getBytesSent).sum();
        long beforeSentPacket = prevNetwork.stream().mapToLong(NetworkIF::getPacketsSent).sum();
        long currentReceiveSize = currentNetwork.stream().mapToLong(NetworkIF::getBytesRecv).sum();
        long currentReceivePacket = currentNetwork.stream().mapToLong(NetworkIF::getPacketsRecv).sum();
        long currentSentSize = currentNetwork.stream().mapToLong(NetworkIF::getBytesSent).sum();
        long currentSentPacket = currentNetwork.stream().mapToLong(NetworkIF::getPacketsSent).sum();
        // 计算
        NetBandwidthBO net = new NetBandwidthBO();
        net.setSs(currentSentSize - beforeSentSize);
        net.setSp(currentSentPacket - beforeSentPacket);
        net.setRs(currentReceiveSize - beforeReceiveSize);
        net.setRp(currentReceivePacket - beforeReceivePacket);
        net.setSr(prevTime);
        net.setEr(currentTime);
        return net;
    }

    /**
     * 收集磁盘 IO 使用信息
     *
     * @return 磁盘 IO 使用信息
     * key: md5.8
     * value: metrics
     */
    public Map<String, DiskIoUsingBO> collectDistIo() {
        List<HWDiskStore> prevDisks = metricsHolder.getPrevDisks();
        long prevTime = metricsHolder.getPrevDisksTime();
        List<HWDiskStore> currentDisks = hardware.getDiskStores();
        long currentTime = System.currentTimeMillis();
        metricsHolder.setPrevDisks(currentDisks);
        metricsHolder.setPrevDisksTime(currentTime);
        // 计算
        Map<String, DiskIoUsingBO> map = Maps.newLinkedMap();
        for (int i = 0; i < currentDisks.size(); i++) {
            HWDiskStore currentDisk = currentDisks.get(i);
            HWDiskStore prevDisk = prevDisks.get(i);
            // 设置
            DiskIoUsingBO disk = new DiskIoUsingBO();
            disk.setRs(currentDisk.getReadBytes() - prevDisk.getReadBytes());
            disk.setWs(currentDisk.getReadBytes() - prevDisk.getReadBytes());
            disk.setRc(currentDisk.getReads() - prevDisk.getReads());
            disk.setWc(currentDisk.getWrites() - prevDisk.getWrites());
            disk.setUt(currentDisk.getTransferTime() - prevDisk.getTransferTime());
            disk.setSr(prevTime);
            disk.setEr(currentTime);
            map.put(Signatures.md5(currentDisk.getModel()).substring(0, 8), disk);
        }
        return map;
    }

}
