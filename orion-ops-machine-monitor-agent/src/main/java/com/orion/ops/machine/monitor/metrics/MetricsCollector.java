package com.orion.ops.machine.monitor.metrics;

import com.alibaba.fastjson.JSON;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.entity.bo.DiskIoUsingBO;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsingBO;
import com.orion.ops.machine.monitor.entity.bo.NetBandwidthBO;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.hardware.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

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
    private MetricsCollectHolder metricsCollectHolder;

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
        long collectTime = System.currentTimeMillis();
        metricsCollectHolder.setPrevCpu(processor.getSystemCpuLoadTicks());
        metricsCollectHolder.setPrevCpuTime(collectTime);
        metricsCollectHolder.setPrevMemoryTime(collectTime);
        metricsCollectHolder.setPrevNetworks(hardware.getNetworkIFs());
        metricsCollectHolder.setPrevNetworksTime(collectTime);
        metricsCollectHolder.setPrevDisks(hardware.getDiskStores());
        metricsCollectHolder.setPrevDisksTime(collectTime);
    }

    /**
     * 收集 cpu 使用信息
     *
     * @return cpu 使用信息
     */
    public CpuUsingBO collectCpu() {
        long[] prevCpu = metricsCollectHolder.getPrevCpu();
        long prevTime = metricsCollectHolder.getPrevCpuTime();
        long[] currentCpu = processor.getSystemCpuLoadTicks();
        long currentTime = System.currentTimeMillis();
        metricsCollectHolder.setPrevCpu(currentCpu);
        metricsCollectHolder.setPrevCpuTime(currentTime);
        // 计算
        CpuUsingBO cpu = new CpuUsingBO();
        cpu.setU(Utils.computeCpuLoad(prevCpu, currentCpu));
        cpu.setSr(prevTime);
        cpu.setEr(currentTime);
        log.info("处理器指标: {}", JSON.toJSONString(cpu));
        return cpu;
    }

    /**
     * 收集内存使用信息
     *
     * @return 内存使用信息
     */
    public MemoryUsingBO collectMemory() {
        long prevTime = metricsCollectHolder.getPrevMemoryTime();
        long total = memory.getTotal();
        long using = total - memory.getAvailable();
        long currentTime = System.currentTimeMillis();
        metricsCollectHolder.setPrevMemoryTime(currentTime);
        // 计算
        MemoryUsingBO mem = new MemoryUsingBO();
        mem.setUr(Utils.roundToDouble((double) using / (double) total, 3));
        mem.setUs(using / Const.BUFFER_KB_1 / Const.BUFFER_KB_1);
        mem.setSr(prevTime);
        mem.setEr(currentTime);
        log.info("内存指标: {}", JSON.toJSONString(mem));
        return mem;
    }

    /**
     * 收集网络带宽使用信息
     *
     * @return 网络带宽使用信息
     */
    public NetBandwidthBO collectNetBandwidth() {
        List<NetworkIF> prevNetwork = metricsCollectHolder.getPrevNetworks();
        long prevTime = metricsCollectHolder.getPrevNetworksTime();
        List<NetworkIF> currentNetwork = hardware.getNetworkIFs();
        long currentTime = System.currentTimeMillis();
        metricsCollectHolder.setPrevNetworks(currentNetwork);
        metricsCollectHolder.setPrevNetworksTime(currentTime);
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
        net.setSr(prevTime);
        net.setEr(currentTime);
        net.setSmr(Utils.computeMpbSecondRate(net, currentSentSize - beforeSentSize));
        net.setRmr(Utils.computeMpbSecondRate(net, currentReceiveSize - beforeReceiveSize));
        net.setSp(currentSentPacket - beforeSentPacket);
        net.setRp(currentReceivePacket - beforeReceivePacket);
        log.info("网络带宽指标: {}", JSON.toJSONString(net));
        return net;
    }

    /**
     * 收集磁盘 IO 使用信息
     *
     * @return 磁盘 IO 使用信息
     */
    public List<DiskIoUsingBO> collectDiskIo() {
        List<HWDiskStore> prevDisks = metricsCollectHolder.getPrevDisks();
        long prevTime = metricsCollectHolder.getPrevDisksTime();
        List<HWDiskStore> currentDisks = hardware.getDiskStores();
        long currentTime = System.currentTimeMillis();
        metricsCollectHolder.setPrevDisks(currentDisks);
        metricsCollectHolder.setPrevDisksTime(currentTime);
        // 计算
        List<DiskIoUsingBO> list = Lists.newList();
        for (int i = 0; i < currentDisks.size(); i++) {
            HWDiskStore currentDisk = currentDisks.get(i);
            HWDiskStore prevDisk = prevDisks.get(i);
            // 设置
            String seq = Utils.getDiskSeq(currentDisk.getModel());
            DiskIoUsingBO disk = new DiskIoUsingBO();
            disk.setSeq(seq);
            disk.setRs(currentDisk.getReadBytes() - prevDisk.getReadBytes());
            disk.setWs(currentDisk.getReadBytes() - prevDisk.getReadBytes());
            disk.setRc(currentDisk.getReads() - prevDisk.getReads());
            disk.setWc(currentDisk.getWrites() - prevDisk.getWrites());
            disk.setUt(currentDisk.getTransferTime() - prevDisk.getTransferTime());
            disk.setSr(prevTime);
            disk.setEr(currentTime);
            list.add(disk);
            log.info("磁盘读写指标-{}: {}", seq, JSON.toJSONString(disk));
        }
        return list;
    }

}
