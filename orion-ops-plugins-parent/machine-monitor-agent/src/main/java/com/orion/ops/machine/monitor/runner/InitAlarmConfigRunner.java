package com.orion.ops.machine.monitor.runner;

import com.alibaba.fastjson.TypeReference;
import com.orion.lang.define.wrapper.HttpWrapper;
import com.orion.lang.utils.collect.Lists;
import com.orion.ops.machine.monitor.constant.MachineAlarmType;
import com.orion.ops.machine.monitor.constant.PropertiesConst;
import com.orion.ops.machine.monitor.handler.AlarmChecker;
import com.orion.ops.plugin.common.handler.http.HttpApiRequest;
import com.orion.ops.plugin.common.handler.http.OrionOpsExposeHttpApi;
import com.orion.ops.plugin.common.handler.http.OrionOpsExposeHttpApiRequester;
import com.orion.ops.plugin.common.handler.http.vo.MachineAlarmConfig;
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
            data.forEach(s -> alarmChecker.getConfigMap().put(MachineAlarmType.of(s.getType()), s));
        } catch (Exception e) {
            log.error("初始化报警配置-失败", e);
        }
    }

}
