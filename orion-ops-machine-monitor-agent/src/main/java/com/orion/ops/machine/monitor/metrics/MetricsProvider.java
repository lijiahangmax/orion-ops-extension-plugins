package com.orion.ops.machine.monitor.metrics;

import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.entity.dto.*;
import com.orion.utils.Strings;
import com.orion.utils.Systems;
import com.orion.utils.Threads;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 机器指标提供者
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/28 18:31
 */
@Slf4j
@Order(400)
@Component
public class MetricsProvider {

    @Getter
    private HardwareAbstractionLayer hardware;

    @Getter
    private OperatingSystem os;

    @PostConstruct
    private void initCollector() {
        log.info("初始化数据提供者");
        // 获取机器数据
        SystemInfo si = new SystemInfo();
        this.hardware = si.getHardware();
        this.os = si.getOperatingSystem();
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
     * 获取 cpu 使用率
     */
    public CpuUsingDTO getCpuUsing() {
        CentralProcessor processor = hardware.getProcessor();
        long[] beforeTicks = processor.getSystemCpuLoadTicks();
        long[][] beforeProcTicks = processor.getProcessorCpuLoadTicks();
        // 获取当前指标 休眠1秒 获取后一秒的指标比对
        Threads.sleep(Const.MS_S_1);
        CpuUsingDTO cpuUsing = new CpuUsingDTO();
        cpuUsing.setUsing(processor.getSystemCpuLoadBetweenTicks(beforeTicks) * 100);
        // 核心使用率
        List<Double> coreUsing = Arrays.stream(processor.getProcessorCpuLoadBetweenTicks(beforeProcTicks))
                .map(s -> s * 100)
                .boxed()
                .collect(Collectors.toList());
        cpuUsing.setCoreUsing(coreUsing);
        return cpuUsing;
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
     * 获取网络带宽信息
     */
    public NetBandwidthDTO getNetBandwidth() {
        List<NetworkIF> beforeNetwork = hardware.getNetworkIFs();
        // 获取当前指标 休眠1秒 获取后一秒的指标比对
        Threads.sleep(Const.MS_S_1);
        List<NetworkIF> currentNetwork = hardware.getNetworkIFs();
        // 统计流量信息
        long beforeReceiveTotal = beforeNetwork.stream().mapToLong(NetworkIF::getBytesRecv).sum();
        long beforeSentTotal = beforeNetwork.stream().mapToLong(NetworkIF::getBytesSent).sum();
        long currentReceiveTotal = currentNetwork.stream().mapToLong(NetworkIF::getBytesRecv).sum();
        long currentSentTotal = currentNetwork.stream().mapToLong(NetworkIF::getBytesSent).sum();
        // 返回
        NetBandwidthDTO net = new NetBandwidthDTO();
        net.setUp(currentSentTotal - beforeSentTotal);
        net.setDown(currentReceiveTotal - beforeReceiveTotal);
        return net;
    }

    /**
     * 获取磁盘空间使用信息
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
     * 获取磁盘 IO 使用信息
     *
     * @return metrics
     */
    public List<DiskIoUsingDTO> getDiskIoUsing() {
        List<HWDiskStore> beforeDisks = hardware.getDiskStores();
        // 获取当前指标 休眠1秒 获取后一秒的指标比对
        Threads.sleep(Const.MS_S_1);
        List<HWDiskStore> currentDisks = hardware.getDiskStores();
        List<DiskIoUsingDTO> list = new ArrayList<>();
        for (int i = 0; i < currentDisks.size(); i++) {
            HWDiskStore afterDisk = currentDisks.get(i);
            HWDiskStore beforeDisk = beforeDisks.get(i);
            DiskIoUsingDTO using = new DiskIoUsingDTO();
            using.setModel(afterDisk.getModel());
            using.setReadCount(afterDisk.getReads() - beforeDisk.getReads());
            using.setReadBytes(afterDisk.getReadBytes() - beforeDisk.getReadBytes());
            using.setWriteCount(afterDisk.getWrites() - beforeDisk.getWrites());
            using.setWriteBytes(afterDisk.getReadBytes() - beforeDisk.getReadBytes());
            using.setUsingTime(afterDisk.getTransferTime() - beforeDisk.getTransferTime());
            list.add(using);
        }
        return list;
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

}
