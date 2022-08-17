package com.orion.ops.machine.monitor.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置常量
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/8/1 14:48
 */
@Component
public class PropertiesConst {

    /**
     * 机器id
     */
    public static Long MACHINE_ID;

    /**
     * 版本
     */
    public static String AGENT_VERSION;

    @Value("${machineId:}")
    public void setMachineId(Long machineId) {
        PropertiesConst.MACHINE_ID = machineId;
    }

    @Value("${agent.version}")
    public void setAgentVersion(String agentVersion) {
        PropertiesConst.AGENT_VERSION = agentVersion;
    }

}
