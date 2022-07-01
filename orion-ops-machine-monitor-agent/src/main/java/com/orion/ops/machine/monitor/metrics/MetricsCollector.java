package com.orion.ops.machine.monitor.metrics;

import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.entity.bo.CpuUsingBO;
import com.orion.ops.machine.monitor.entity.bo.MemoryUsingBO;
import com.orion.ops.machine.monitor.entity.bo.NetBandwidthBO;
import com.orion.ops.machine.monitor.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

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
        metricsHolder.setPrevNetwork(hardware.getNetworkIFs());
        metricsHolder.setPrevNetworkTime(System.currentTimeMillis());
        metricsHolder.setPrevDisk(hardware.getDiskStores());
        metricsHolder.setPrevDiskTime(System.currentTimeMillis());
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
        List<NetworkIF> prevNetwork = metricsHolder.getPrevNetwork();
        long prevTime = metricsHolder.getPrevNetworkTime();
        List<NetworkIF> currentNetwork = hardware.getNetworkIFs();
        long currentTime = System.currentTimeMillis();
        metricsHolder.setPrevNetwork(currentNetwork);
        metricsHolder.setPrevNetworkTime(currentTime);
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

}
