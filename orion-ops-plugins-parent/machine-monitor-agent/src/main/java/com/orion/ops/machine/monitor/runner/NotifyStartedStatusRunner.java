package com.orion.ops.machine.monitor.runner;

import com.orion.ops.machine.monitor.constant.PropertiesConst;
import com.orion.ops.plugin.common.handler.http.HttpApiRequest;
import com.orion.ops.plugin.common.handler.http.OrionOpsExposeHttpApi;
import com.orion.ops.plugin.common.handler.http.OrionOpsExposeHttpApiRequester;
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
