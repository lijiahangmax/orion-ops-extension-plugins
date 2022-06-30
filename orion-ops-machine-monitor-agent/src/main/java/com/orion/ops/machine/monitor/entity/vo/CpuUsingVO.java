package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.entity.dto.CpuUsingDTO;
import com.orion.ops.machine.monitor.utils.Utils;
import com.orion.utils.convert.TypeStore;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * cpu 使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 19:04
 */
@Data
public class CpuUsingVO {

    /**
     * 用户使用率
     */
    private Double userUsing;

    /**
     * 系统使用率
     */
    private Double systemUsing;

    /**
     * 总使用率
     */
    private Double totalUsing;

    /**
     * 核心使用率
     */
    private List<Double> coreUsing;

    static {
        TypeStore.STORE.register(CpuUsingDTO.class, CpuUsingVO.class, p -> {
            CpuUsingVO vo = new CpuUsingVO();
            vo.setUserUsing(Utils.roundToDouble(p.getUserUsing()));
            vo.setSystemUsing(Utils.roundToDouble(p.getSystemUsing()));
            vo.setTotalUsing(Utils.roundToDouble(p.getUserUsing()));
            vo.setCoreUsing(p.getCoreUsing().stream()
                    .map(Utils::roundToDouble)
                    .collect(Collectors.toList()));
            return vo;
        });
    }

}
