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
package cn.orionsec.ops.machine.monitor.runner;

import cn.orionsec.kit.lang.define.wrapper.HttpWrapper;
import cn.orionsec.kit.lang.utils.collect.Lists;
import cn.orionsec.ops.machine.monitor.constant.MachineAlarmType;
import cn.orionsec.ops.machine.monitor.constant.PropertiesConst;
import cn.orionsec.ops.machine.monitor.handler.AlarmChecker;
import cn.orionsec.ops.plugin.common.handler.http.HttpApiRequest;
import cn.orionsec.ops.plugin.common.handler.http.OrionOpsExposeHttpApi;
import cn.orionsec.ops.plugin.common.handler.http.OrionOpsExposeHttpApiRequester;
import cn.orionsec.ops.plugin.common.handler.http.vo.MachineAlarmConfig;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 加载报警配置 runner
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/8/31 18:49
 */
@Slf4j
@Component
@Order(2000)
public class InitAlarmConfigRunner implements CommandLineRunner {

    @Resource
    private AlarmChecker alarmChecker;

    @Override
    public void run(String... args) throws Exception {
        Long machineId = PropertiesConst.MACHINE_ID;
        log.info("初始化报警配置-开始 machineId: {}", machineId);
        if (machineId == null) {
            return;
        }
        try {
            HttpApiRequest request = OrionOpsExposeHttpApiRequester.create(OrionOpsExposeHttpApi.GET_MACHINE_ALARM_CONFIG);
            request.queryParam("machineId", String.valueOf(machineId));
            HttpWrapper<List<MachineAlarmConfig>> wrapper = request.getJson(new TypeReference<HttpWrapper<List<MachineAlarmConfig>>>() {
            });
            log.info("初始化报警配置-请求 {}", wrapper.toJsonString());
            if (!wrapper.isOk()) {
                return;
            }
            List<MachineAlarmConfig> data = wrapper.getData();
            if (Lists.isEmpty(data)) {
                return;
            }
            data.forEach(s -> alarmChecker.getAlarmContext().put(MachineAlarmType.of(s.getType()), s));
        } catch (Exception e) {
            log.error("初始化报警配置-失败", e);
        }
    }

}
