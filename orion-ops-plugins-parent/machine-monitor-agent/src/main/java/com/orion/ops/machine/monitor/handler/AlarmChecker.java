package com.orion.ops.machine.monitor.handler;

import com.orion.lang.utils.collect.Maps;
import com.orion.ops.machine.monitor.constant.MachineAlarmType;
import com.orion.ops.plugin.common.handler.http.vo.MachineAlarmConfig;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 报警推送
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/8/31 19:06
 */
@Component
public class AlarmChecker {

    @Getter
    private final Map<MachineAlarmType, MachineAlarmConfig> config = Maps.newMap();

}
