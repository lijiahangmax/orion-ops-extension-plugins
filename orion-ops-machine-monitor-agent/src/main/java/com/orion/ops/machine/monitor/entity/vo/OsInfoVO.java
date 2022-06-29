package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.entity.dto.OsInfoDTO;
import com.orion.utils.convert.TypeStore;
import com.orion.utils.io.Files1;
import lombok.Data;
import oshi.util.FormatUtil;

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
     * cpu 核心数
     */
    private Integer cpuCore;

    /**
     * 总内存
     */
    private String totalMemory;

    static {
        TypeStore.STORE.register(OsInfoDTO.class, OsInfoVO.class, p -> {
            OsInfoVO vo = new OsInfoVO();
            vo.setOsName(p.getOsName());
            vo.setUptime(FormatUtil.formatElapsedSecs(p.getUptime()));
            vo.setCpuName(p.getCpuName());
            vo.setCpuCore(p.getCpuCore());
            vo.setTotalMemory(Files1.getSize(p.getTotalMemory()));
            return vo;
        });
    }

}
