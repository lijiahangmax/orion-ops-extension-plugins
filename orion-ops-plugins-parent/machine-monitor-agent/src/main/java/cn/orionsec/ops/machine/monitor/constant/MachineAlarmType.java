/*
 * Copyright (c) 2021 - present Jiahang Li (om.orionsec.cn ljh1553488six@139.com).
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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 机器报警类型
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/8/31 18:54
 */
@Getter
@AllArgsConstructor
public enum MachineAlarmType {

    /**
     * cpu 使用率
     */
    CPU_USAGE(10),

    /**
     * 内存使用率
     */
    MEMORY_USAGE(20),

    ;

    private final Integer type;

    public static MachineAlarmType of(Integer type) {
        if (type == null) {
            return null;
        }
        for (MachineAlarmType value : values()) {
            if (value.type.equals(type)) {
                return value;
            }
        }
        return null;
    }

}
