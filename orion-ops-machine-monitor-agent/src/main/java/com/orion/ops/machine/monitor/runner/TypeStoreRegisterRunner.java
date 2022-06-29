package com.orion.ops.machine.monitor.runner;

import com.orion.ops.machine.monitor.OrionOpsMachineMonitorAgentApplication;
import com.orion.support.Attempt;
import com.orion.utils.reflect.PackageScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * TypeStore加载器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 18:01
 */
@Component
@Order(1100)
@Slf4j
public class TypeStoreRegisterRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("注册vo转换器-开始");
        new PackageScanner("com.orion.ops.machine.monitor.entity.vo.*", "com.orion.ops.machine.monitor.dto.*")
                .with(OrionOpsMachineMonitorAgentApplication.class)
                .scan()
                .getClasses()
                .forEach(Attempt.rethrows(s -> {
                    log.info("register type store class: {}", s);
                    Class.forName(s.getName());
                }));
        log.info("注册vo转换器-结束");
    }

}
