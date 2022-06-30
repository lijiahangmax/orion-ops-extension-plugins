package com.orion.ops.machine.monitor.controller;

import com.orion.ops.machine.monitor.annotation.RestWrapper;
import com.orion.ops.machine.monitor.collect.MetricsCollector;
import com.orion.ops.machine.monitor.entity.dto.DiskStoreUsingDTO;
import com.orion.ops.machine.monitor.entity.vo.*;
import com.orion.utils.convert.Converts;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 机器监控指标 api
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 16:23
 */
@RestWrapper
@RestController
@RequestMapping("/orion/machine-monitor-agent/api/metrics")
public class MonitorMetricsController {

    @Resource
    private MetricsCollector metricsCollector;

    /**
     * 获取机器信息
     */
    @GetMapping("/info")
    public OsInfoVO getOsInfo() {
        return Converts.to(metricsCollector.getOsInfo(), OsInfoVO.class);
    }

    /**
     * 获取 cpu 使用信息
     */
    @GetMapping("/cpu-using")
    public CpuUsingVO getCpuUsing() {
        return Converts.to(metricsCollector.getCpuUsing(), CpuUsingVO.class);
    }

    /**
     * 获取系统负载
     */
    @GetMapping("/system-load")
    public SystemLoadVO getSystemLoad() {
        return Converts.to(metricsCollector.getSystemLoad(), SystemLoadVO.class);
    }

    /**
     * 获取内存使用信息
     */
    @GetMapping("/memory-using")
    public MemoryUsingVO getMemoryUsing() {
        return Converts.to(metricsCollector.getMemoryUsing(), MemoryUsingVO.class);
    }

    /**
     * 获取磁盘使用信息
     */
    @GetMapping("/disk-using")
    public List<DiskStoreUsingVO> getDiskStoreUsing() {
        return Converts.toList(metricsCollector.getDiskStoreUsing(), DiskStoreUsingVO.class);
    }

    /**
     * 合并获取磁盘使用信息
     */
    @GetMapping("/disk-merge-using")
    public DiskStoreUsingVO getDiskStoreMergeUsing() {
        List<DiskStoreUsingDTO> store = metricsCollector.getDiskStoreUsing();
        if (store.size() == 1) {
            return Converts.to(store.get(0), DiskStoreUsingVO.class);
        }
        // 合并
        long totalSpace = store.stream().mapToLong(DiskStoreUsingDTO::getTotalSpace).sum();
        long freeSpace = store.stream().mapToLong(DiskStoreUsingDTO::getFreeSpace).sum();
        long usingSpace = totalSpace - freeSpace;
        DiskStoreUsingDTO merge = new DiskStoreUsingDTO();
        merge.setTotalSpace(totalSpace);
        merge.setUsingSpace(usingSpace);
        merge.setFreeSpace(freeSpace);
        merge.setUsingRate((double) usingSpace / (double) totalSpace);
        return Converts.to(merge, DiskStoreUsingVO.class);
    }

    /**
     * 获取进程
     */
    @GetMapping("/top-processes")
    public List<SystemProcessVO> getTopProgress(@RequestParam(value = "name", required = false) String name,
                                                @RequestParam("limit") Integer limit) {
        return Converts.toList(metricsCollector.getProcesses(name, limit), SystemProcessVO.class);
    }

}
