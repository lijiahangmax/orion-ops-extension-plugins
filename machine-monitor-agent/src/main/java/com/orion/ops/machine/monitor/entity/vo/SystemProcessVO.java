package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.entity.dto.SystemProcessDTO;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.convert.TypeStore;
import com.orion.utils.io.Files1;
import lombok.Data;
import oshi.util.FormatUtil;

/**
 * 系统进程
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 17:44
 */
@Data
public class SystemProcessVO {

    /**
     * 进程id
     */
    private Integer pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 用户
     */
    private String user;

    /**
     * cpu 使用率
     */
    private Double cpuLoad;

    /**
     * 使用内存
     */
    private String memory;

    /**
     * 句柄数
     */
    private Long openFile;

    /**
     * 启用时长
     */
    private String uptime;

    /**
     * 命令行
     */
    private String commandLine;

    static {
        TypeStore.STORE.register(SystemProcessDTO.class, SystemProcessVO.class, p -> {
            SystemProcessVO vo = new SystemProcessVO();
            vo.setPid(p.getPid());
            vo.setName(p.getName());
            vo.setUser(p.getUser());
            vo.setCpuLoad(Utils.roundToDouble(p.getCpuLoad()));
            vo.setMemory(Files1.getSize(p.getMemory()));
            vo.setOpenFile(p.getOpenFile());
            vo.setUptime(FormatUtil.formatElapsedSecs(p.getUptime()));
            vo.setCommandLine(p.getCommandLine());
            return vo;
        });
    }

}
