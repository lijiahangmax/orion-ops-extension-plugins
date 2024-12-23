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
package cn.orionsec.ops.plugin.common.handler.http;

import cn.orionsec.kit.http.support.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * orion-ops 暴露服务api
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/8/31 19:09
 */
@Getter
@AllArgsConstructor
public enum OrionOpsExposeHttpApi implements HttpApiDefined {

    /**
     * 获取机器报警配置
     */
    GET_MACHINE_ALARM_CONFIG("/orion/expose-api/machine-alarm/get-config", HttpMethod.GET),

    /**
     * 触发机器报警
     */
    TRIGGER_MACHINE_ALARM("/orion/expose-api/machine-alarm/trigger-alarm", HttpMethod.POST),

    /**
     * 获取机器报警配置
     */
    MACHINE_MONITOR_STARTED("/orion/expose-api/machine-monitor/started", HttpMethod.GET),

    ;

    private final String path;

    private final HttpMethod method;

}
