/*
 * Copyright (c) 2021 - present Jiahang Li, All rights reserved.
 *
 *   https://om.orionsec.cn
 *
 * Members:
 *   Jiahang Li - ljh1553488six@139.com - author
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.orionsec.ops.machine.monitor.metrics;

import cn.orionsec.kit.lang.function.Functions;
import cn.orionsec.kit.lang.utils.Strings;
import cn.orionsec.kit.lang.utils.Systems;
import cn.orionsec.kit.lang.utils.Threads;
import cn.orionsec.ops.machine.monitor.constant.Const;
import cn.orionsec.ops.machine.monitor.entity.dto.*;
import cn.orionsec.ops.machine.monitor.entity.vo.DiskNameVO;
import cn.orionsec.ops.machine.monitor.utils.Utils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.*;
import java.util.function.Function;
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
    private final HardwareAbstractionLayer hardware;

    @Getter
    private final OperatingSystem os;

    public MetricsProvider() {
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
    public CpuUsageDTO getCpuUsage() {
        CentralProcessor processor = hardware.getProcessor();
        long[] beforeTicks = processor.getSystemCpuLoadTicks();
        long[][] beforeProcTicks = processor.getProcessorCpuLoadTicks();
        // 获取当前指标 休眠1秒 获取后一秒的指标比对
        Threads.sleep(Const.MS_S_1);
        CpuUsageDTO cpu = new CpuUsageDTO();
        cpu.setUsage(processor.getSystemCpuLoadBetweenTicks(beforeTicks) * 100);
        // 核心使用率
        List<Double> core = Arrays.stream(processor.getProcessorCpuLoadBetweenTicks(beforeProcTicks))
                .map(s -> s * 100)
                .boxed()
                .collect(Collectors.toList());
        cpu.setCoreUsage(core);
        return cpu;
    }

    /**
     * 获取内存信息
     *
     * @return metrics
     */
    public MemoryUsageDTO getMemoryUsage() {
        GlobalMemory memory = hardware.getMemory();
        long totalByte = memory.getTotal();
        long availableByte = memory.getAvailable();
        // 设置内存指标
        MemoryUsageDTO memoryUsage = new MemoryUsageDTO();
        memoryUsage.setTotalMemory(totalByte);
        memoryUsage.setUsageMemory(totalByte - availableByte);
        memoryUsage.setFreeMemory(availableByte);
        memoryUsage.setUsage((double) (totalByte - availableByte) / (double) totalByte);
        return memoryUsage;
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
     * 获取硬盘空间使用信息
     *
     * @return metrics
     */
    public List<DiskStoreUsageDTO> getDiskStoreUsage() {
        return os.getFileSystem()
                .getFileStores()
                .stream()
                .map(s -> {
                    long totalSpace = s.getTotalSpace();
                    long freeSpace = s.getFreeSpace();
                    long usageSpace = totalSpace - freeSpace;
                    DiskStoreUsageDTO disk = new DiskStoreUsageDTO();
                    disk.setName(s.getName());
                    disk.setTotalSpace(totalSpace);
                    disk.setUsageSpace(usageSpace);
                    disk.setFreeSpace(freeSpace);
                    disk.setUsage((double) usageSpace / (double) totalSpace);
                    return disk;
                }).distinct().collect(Collectors.toList());
    }

    /**
     * 获取硬盘名称
     *
     * @return 硬盘名称
     */
    public List<DiskNameVO> getDiskName() {
        return hardware.getDiskStores().stream()
                .map(d -> {
                    DiskNameVO disk = new DiskNameVO();
                    String model = d.getModel();
                    disk.setName(model);
                    disk.setSeq(Utils.getDiskSeq(model));
                    return disk;
                }).collect(Collectors.toList());
    }

    /**
     * 获取硬盘 IO 使用信息
     *
     * @return 硬盘 IO 使用信息
     */
    public List<DiskIoUsageDTO> getDiskIoUsage() {
        List<HWDiskStore> beforeDisks = hardware.getDiskStores();
        // 获取当前指标 休眠1秒 获取后一秒的指标比对
        Threads.sleep(Const.MS_S_1);
        List<HWDiskStore> currentDisks = hardware.getDiskStores();
        List<DiskIoUsageDTO> list = new ArrayList<>();
        for (int i = 0; i < currentDisks.size(); i++) {
            HWDiskStore afterDisk = currentDisks.get(i);
            HWDiskStore beforeDisk = beforeDisks.get(i);
            // 对比数据
            DiskIoUsageDTO disk = new DiskIoUsageDTO();
            disk.setModel(afterDisk.getModel());
            disk.setReadCount(afterDisk.getReads() - beforeDisk.getReads());
            disk.setReadBytes(afterDisk.getReadBytes() - beforeDisk.getReadBytes());
            disk.setWriteCount(afterDisk.getWrites() - beforeDisk.getWrites());
            disk.setWriteBytes(afterDisk.getReadBytes() - beforeDisk.getReadBytes());
            disk.setUsageTime(afterDisk.getTransferTime() - beforeDisk.getTransferTime());
            list.add(disk);
        }
        return list;
    }

    /**
     * 获取进程信息
     *
     * @param name  命令名称
     * @param limit 限制数
     * @return 进程
     */
    public List<SystemProcessDTO> getProcesses(String name, int limit) {
        Predicate<OSProcess> filter;
        if (Strings.isBlank(name)) {
            filter = null;
        } else {
            filter = p -> p.getName().toLowerCase().contains(name.toLowerCase())
                    || (p.getCommandLine() != null && p.getCommandLine().toLowerCase().contains(name.toLowerCase()));
        }
        // 获取全部进程
        List<OSProcess> beforeProcesses = os.getProcesses(filter, OperatingSystem.ProcessSorting.CPU_DESC, limit);
        // 获取当前指标 休眠1秒 比对指标
        Threads.sleep(Const.MS_S_1);
        // 获取进程最新信息
        Map<Integer, OSProcess> afterProcessMap = beforeProcesses.stream()
                .map(OSProcess::getProcessID)
                .map(os::getProcess)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(OSProcess::getProcessID, Function.identity(), Functions.right()));
        // cpu 核心数
        int cpuCount = hardware.getProcessor().getLogicalProcessorCount();
        // 响应
        return beforeProcesses.stream()
                .map(s -> {
                    SystemProcessDTO p = new SystemProcessDTO();
                    p.setPid(s.getProcessID());
                    p.setName(s.getName());
                    p.setUser(s.getUser());
                    // 平均核心使用率
                    OSProcess afterProcess = afterProcessMap.get(s.getProcessID());
                    if (afterProcess != null) {
                        p.setCpuUsage(afterProcess.getProcessCpuLoadBetweenTicks(s) * 100 / cpuCount);
                    } else {
                        p.setCpuUsage(s.getProcessCpuLoadCumulative() * 100 / cpuCount);
                    }
                    p.setMemoryUsage(s.getResidentSetSize());
                    p.setOpenFile(s.getOpenFiles());
                    p.setUptime(s.getUpTime());
                    p.setCommandLine(s.getCommandLine());
                    return p;
                })
                .sorted(Comparator.comparing(SystemProcessDTO::getCpuUsage).reversed())
                .collect(Collectors.toList());
    }

}
