package com.orion.ops.machine.monitor.collect;

import com.orion.ops.machine.monitor.entity.dto.*;
import com.orion.utils.Strings;
import com.orion.utils.Systems;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 机器指标采集器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/28 18:31
 */
@Slf4j
@Order(400)
@Component
public class MetricsCollector {

    @Resource
    private MetricsHolder metricsHolder;

    /**
     * 硬件
     */
    private HardwareAbstractionLayer hardware;

    /**
     * 系统
     */
    private OperatingSystem os;

    @PostConstruct
    public void initCollector() {
        log.info("初始化数据采集器");
        // 获取机器数据
        SystemInfo si = new SystemInfo();
        this.hardware = si.getHardware();
        this.os = si.getOperatingSystem();
        // 设置原始数据
        CentralProcessor processor = hardware.getProcessor();
        metricsHolder.setCurrentCpuLoadTicks(processor.getSystemCpuLoadTicks());
        metricsHolder.setCurrentProcTicks(processor.getProcessorCpuLoadTicks());
        metricsHolder.setCurrentNetwork(hardware.getNetworkIFs());
        metricsHolder.setCurrentDisk(hardware.getDiskStores());
    }

    /**
     * 获取系统信息
     *
     * @return metrics
     */
    public OsInfoDTO getOsInfo() {
        CentralProcessor processor = hardware.getProcessor();
        OsInfoDTO info = new OsInfoDTO();
        info.setOsName(os.toString());
        info.setUptime(os.getSystemUptime());
        info.setCpuName(processor.getProcessorIdentifier().getName());
        info.setCpuLogicalCore(processor.getLogicalProcessorCount());
        info.setCpuPhysicalCore(processor.getPhysicalProcessorCount());
        info.setTotalMemory(hardware.getMemory().getTotal());
        info.setPid(os.getProcessId());
        info.setUsername(Systems.USER_NAME);
        info.setHostname(os.getNetworkParams().getHostName());
        return info;
    }

    /**
     * 获取系统负载
     *
     * @return metrics
     */
    public SystemLoadDTO getSystemLoad() {
        CentralProcessor processor = hardware.getProcessor();
        double[] load = processor.getSystemLoadAverage(3);
        double oneMinuteLoad = load[0];
        double fiveMinuteLoad = load[1];
        double fifteenMinuteLoad = load[2];
        // 设置负载
        SystemLoadDTO systemLoad = new SystemLoadDTO();
        systemLoad.setOneMinuteLoad(oneMinuteLoad < 0 ? 0 : oneMinuteLoad);
        systemLoad.setFiveMinuteLoad(fiveMinuteLoad < 0 ? 0 : fiveMinuteLoad);
        systemLoad.setFifteenMinuteLoad(fifteenMinuteLoad < 0 ? 0 : fifteenMinuteLoad);
        return systemLoad;
    }

    /**
     * 获取内存信息
     *
     * @return metrics
     */
    public MemoryUsingDTO getMemoryUsing() {
        GlobalMemory memory = hardware.getMemory();
        long totalByte = memory.getTotal();
        long availableByte = memory.getAvailable();
        // 设置内存指标
        MemoryUsingDTO memoryUsing = new MemoryUsingDTO();
        memoryUsing.setTotalMemory(totalByte);
        memoryUsing.setUsingMemory(totalByte - availableByte);
        memoryUsing.setFreeMemory(availableByte);
        memoryUsing.setUsingRate((double) (totalByte - availableByte) / (double) totalByte);
        return memoryUsing;
    }

    /**
     * 获取磁盘空间信息
     *
     * @return metrics
     */
    public List<DiskStoreUsingDTO> getDiskStoreUsing() {
        return os.getFileSystem()
                .getFileStores()
                .stream()
                .map(s -> {
                    long totalSpace = s.getTotalSpace();
                    long freeSpace = s.getFreeSpace();
                    long usingSpace = totalSpace - freeSpace;
                    DiskStoreUsingDTO disk = new DiskStoreUsingDTO();
                    disk.setName(s.getName());
                    disk.setTotalSpace(totalSpace);
                    disk.setUsingSpace(usingSpace);
                    disk.setFreeSpace(freeSpace);
                    disk.setUsingRate((double) usingSpace / (double) totalSpace);
                    return disk;
                }).collect(Collectors.toList());
    }

    /**
     * 获取进程信息
     *
     * @param name  命令名称
     * @param limit 限制数
     * @return metrics
     */
    public List<SystemProcessDTO> getProcesses(String name, int limit) {
        Predicate<OSProcess> filter;
        if (Strings.isEmpty(name)) {
            filter = OperatingSystem.ProcessFiltering.ALL_PROCESSES;
        } else {
            filter = p -> p.getName().toLowerCase().contains(name.toLowerCase());
        }
        return os.getProcesses(filter, OperatingSystem.ProcessSorting.CPU_DESC, limit)
                .stream()
                .map(s -> {
                    SystemProcessDTO p = new SystemProcessDTO();
                    p.setPid(s.getProcessID());
                    p.setName(s.getName());
                    p.setUser(s.getUser());
                    p.setCpuLoad(s.getProcessCpuLoadBetweenTicks(s));
                    p.setMemory(s.getResidentSetSize());
                    p.setOpenFile(s.getOpenFiles());
                    p.setUptime(s.getUpTime());
                    p.setCommandLine(s.getCommandLine());
                    return p;
                }).collect(Collectors.toList());
    }

    /**
     * 获取 cpu 使用率
     */
    public CpuUsingDTO getCpuUsing() {
        CentralProcessor processor = hardware.getProcessor();
        long[] beforeTicks = metricsHolder.getCurrentCpuLoadTicks();
        long[][] beforeProcTicks = metricsHolder.getCurrentProcTicks();
        long[] currentTicks = processor.getSystemCpuLoadTicks();
        long[][] currentProcTicks = processor.getProcessorCpuLoadTicks();
        long user = currentTicks[CentralProcessor.TickType.USER.getIndex()] - beforeTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = currentTicks[CentralProcessor.TickType.NICE.getIndex()] - beforeTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = currentTicks[CentralProcessor.TickType.SYSTEM.getIndex()] - beforeTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = currentTicks[CentralProcessor.TickType.IDLE.getIndex()] - beforeTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long ioWait = currentTicks[CentralProcessor.TickType.IOWAIT.getIndex()] - beforeTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = currentTicks[CentralProcessor.TickType.IRQ.getIndex()] - beforeTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long soft = currentTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - beforeTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = currentTicks[CentralProcessor.TickType.STEAL.getIndex()] - beforeTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + ioWait + irq + soft + steal;
        // 设置指标
        CpuUsingDTO cpuUsing = new CpuUsingDTO();
        cpuUsing.setUserUsing(100d * user / totalCpu);
        cpuUsing.setSystemUsing(100d * sys / totalCpu);
        cpuUsing.setTotalUsing(processor.getSystemCpuLoadBetweenTicks(beforeTicks) * 100);
        // 核心使用率
        List<Double> coreUsing = Arrays.stream(processor.getProcessorCpuLoadBetweenTicks(beforeProcTicks))
                .map(s -> s * 100)
                .boxed()
                .collect(Collectors.toList());
        cpuUsing.setCoreUsing(coreUsing);
        metricsHolder.setCurrentCpuLoadTicks(currentTicks);
        metricsHolder.setCurrentProcTicks(currentProcTicks);
        return cpuUsing;
    }

    /**
     * 获取网络带宽信息
     */
    public NetBandwidthDTO getNetBandwidth() {
        List<NetworkIF> beforeNetwork = metricsHolder.getCurrentNetwork();
        List<NetworkIF> currentNetwork = hardware.getNetworkIFs();
        // 统计流量信息
        long prevReceiveTotal = beforeNetwork.stream().mapToLong(NetworkIF::getBytesRecv).sum();
        long prevSentTotal = beforeNetwork.stream().mapToLong(NetworkIF::getBytesSent).sum();
        long currentReceiveTotal = currentNetwork.stream().mapToLong(NetworkIF::getBytesRecv).sum();
        long currentSentTotal = currentNetwork.stream().mapToLong(NetworkIF::getBytesSent).sum();
        // 返回
        NetBandwidthDTO net = new NetBandwidthDTO();
        net.setUpstreamFlowRate(currentSentTotal - prevSentTotal);
        net.setDownstreamFlowRate(currentReceiveTotal - prevReceiveTotal);
        metricsHolder.setCurrentNetwork(currentNetwork);
        return net;
    }

    /**
     * 获取 IO 使用信息
     *
     * @return metrics
     */
    public List<IoUsingDTO> getIoUsing() {
        List<HWDiskStore> beforeDisks = metricsHolder.getCurrentDisk();
        List<HWDiskStore> currentDisks = hardware.getDiskStores();
        List<IoUsingDTO> list = new ArrayList<>();
        for (int i = 0; i < currentDisks.size(); i++) {
            HWDiskStore afterDisk = currentDisks.get(i);
            HWDiskStore prevDisk = beforeDisks.get(i);
            IoUsingDTO using = new IoUsingDTO();
            using.setName(afterDisk.getName());
            using.setReadCount(afterDisk.getReads() - prevDisk.getReads());
            using.setReadBytes(afterDisk.getReadBytes() - prevDisk.getReadBytes());
            using.setWriteCount(afterDisk.getWrites() - prevDisk.getWrites());
            using.setWriteBytes(afterDisk.getReadBytes() - prevDisk.getReadBytes());
            using.setUsingTime(afterDisk.getTransferTime() - prevDisk.getTransferTime());
            list.add(using);
        }
        metricsHolder.setCurrentDisk(currentDisks);
        return list;
    }

}
