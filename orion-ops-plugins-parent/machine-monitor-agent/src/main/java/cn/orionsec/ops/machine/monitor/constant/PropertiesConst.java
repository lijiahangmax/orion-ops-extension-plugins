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
package cn.orionsec.ops.machine.monitor.constant;

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
    private void setMachineId(Long machineId) {
        PropertiesConst.MACHINE_ID = machineId;
    }

    @Value("${agent.version}")
    private void setAgentVersion(String agentVersion) {
        PropertiesConst.AGENT_VERSION = agentVersion;
    }

}
