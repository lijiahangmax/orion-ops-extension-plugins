package com.orion.ops.machine.monitor.entity.vo;

import com.orion.lang.utils.Systems;
import com.orion.ops.machine.monitor.constant.PropertiesConst;
import com.orion.ops.machine.monitor.metrics.collect.MetricsCollectTask;
import com.orion.spring.SpringHolder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "获取基本监控信息")
public class BaseMetricsVO {

    @ApiModelProperty(value = "机器id")
    private Long machineId;

    @ApiModelProperty(value = "版本")
    private String version;

    @ApiModelProperty(value = "监控状态")
    private Boolean status;

    @ApiModelProperty(value = "是否为windows系统")
    private Boolean isWindows;

    @ApiModelProperty(value = "系统信息")
    private OsInfoVO os;

    @ApiModelProperty(value = "系统负载")
    private SystemLoadVO load;

    @ApiModelProperty(value = "硬盘名称")
    private List<DiskStoreUsageVO> disks;

    @ApiModelProperty(value = "top进程信息")
    private List<SystemProcessVO> processes;

    public BaseMetricsVO() {
        this.machineId = PropertiesConst.MACHINE_ID;
        this.version = PropertiesConst.AGENT_VERSION;
        this.status = SpringHolder.getBean(MetricsCollectTask.class).isRun();
        this.isWindows = Systems.BE_WINDOWS;
    }

}
