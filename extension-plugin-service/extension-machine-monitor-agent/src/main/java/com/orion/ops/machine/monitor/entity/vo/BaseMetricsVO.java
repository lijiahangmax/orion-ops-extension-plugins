package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.constant.PropertiesConst;
import com.orion.ops.machine.monitor.metrics.collect.MetricsCollectTask;
import com.orion.spring.SpringHolder;
import lombok.Data;

import java.util.List;

/**
 * 获取基本监控信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/8/1 15:19
 */
@Data
public class BaseMetricsVO {

    /**
     * 机器id
     */
    private Long machineId;

    /**
     * 版本
     */
    private String version;

    /**
     * 监控状态
     */
    private Boolean status;

    /**
     * 系统信息
     */
    private OsInfoVO os;

    /**
     * 系统负载
     */
    private SystemLoadVO load;

    /**
     * 磁盘信息
     */
    private List<DiskStoreUsageVO> disks;

    /**
     * 进程信息
     */
    private List<SystemProcessVO> processes;

    public BaseMetricsVO() {
        this.machineId = PropertiesConst.MACHINE_ID;
        this.version = PropertiesConst.AGENT_VERSION;
        this.status = SpringHolder.getBean(MetricsCollectTask.class).isRun();
    }

}
