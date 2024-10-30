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

import com.orion.lang.annotation.Removed;

/**
 * 常量
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 15:06
 */
public class Const extends com.orion.lang.constant.Const {

    private Const() {
    }

    public static final String OPS_MONITOR_AGENT = "ops-monitor-agent";

    @Removed("remove by update orion-kit")
    public static final int MBP = 1024 * 128;

}
