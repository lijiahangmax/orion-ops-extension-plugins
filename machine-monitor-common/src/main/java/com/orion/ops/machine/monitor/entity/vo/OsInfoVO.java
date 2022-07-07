package com.orion.ops.machine.monitor.entity.vo;

import com.orion.lang.utils.convert.TypeStore;
import com.orion.lang.utils.io.Files1;
import com.orion.ops.machine.monitor.entity.dto.OsInfoDTO;
import com.orion.ops.machine.monitor.utils.Formats;
import lombok.Data;

/**
 * 系统信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 17:50
 */
@Data
public class OsInfoVO {

    /**
     * 进程id
     */
    private Integer pid;

    /**
     * 系统名称
     */
    private String osName;

    /**
     * 启动时间
     */
    private String uptime;

    /**
     * cpu 名称
     */
    private String cpuName;

    /**
     * cpu 物理核心数
     */
    private Integer cpuPhysicalCore;

    /**
     * cpu 逻辑核心数
     */
    private Integer cpuLogicalCore;

    /**
     * 总内存
     */
    private String totalMemory;

    /**
     * 主机名
     */
    private String hostname;

    /**
     * 用户名
     */
    private String username;

    static {
        TypeStore.STORE.register(OsInfoDTO.class, OsInfoVO.class, p -> {
            OsInfoVO vo = new OsInfoVO();
            vo.setPid(p.getPid());
            vo.setOsName(p.getOsName());
            vo.setUptime(Formats.formatElapsedSecs(p.getUptime()));
            vo.setCpuName(p.getCpuName());
            vo.setCpuPhysicalCore(p.getCpuPhysicalCore());
            vo.setCpuLogicalCore(p.getCpuLogicalCore());
            vo.setTotalMemory(Files1.getSize(p.getTotalMemory()));
            vo.setHostname(p.getHostname());
            vo.setUsername(p.getUsername());
            return vo;
        });
    }

}
