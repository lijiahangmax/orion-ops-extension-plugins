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

import cn.orionsec.ops.machine.monitor.constant.PropertiesConst;
import cn.orionsec.ops.plugin.common.handler.http.HttpApiRequest;
import cn.orionsec.ops.plugin.common.handler.http.OrionOpsExposeHttpApi;
import cn.orionsec.ops.plugin.common.handler.http.OrionOpsExposeHttpApiRequester;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 通知启动状态 runner
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/9/1 17:57
 */
@Slf4j
@Component
@Order(9000)
public class NotifyStartedStatusRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("通知启动状态-开始");
        try {
            HttpApiRequest request = OrionOpsExposeHttpApiRequester.create(OrionOpsExposeHttpApi.MACHINE_MONITOR_STARTED);
            request.queryParam("machineId", String.valueOf(PropertiesConst.MACHINE_ID));
            request.queryParam("version", String.valueOf(PropertiesConst.AGENT_VERSION));
            request.await();
            log.info("通知启动状态-完成");
        } catch (Exception e) {
            log.error("通知启动状态-失败", e);
        }
    }

}
