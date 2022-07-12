package com.orion.ops.monitor.common.runner;

import com.orion.lang.support.Attempt;
import com.orion.lang.utils.reflect.PackageScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * TypeStore 类型转换加载器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 18:01
 */
@Component
@Order(1100)
@Slf4j
public class TypeStoreRegisterRunner implements CommandLineRunner {

    @Value("#{'${type.store.scan.packages}'.split(',')}")
    private String[] scanPackages;

    @Override
    public void run(String... args) throws Exception {
        log.info("注册对象转换器-开始");
        new PackageScanner(scanPackages)
                .with(TypeStoreRegisterRunner.class)
                .scan()
                .getClasses()
                .forEach(Attempt.rethrows(s -> {
                    log.info("register type store class: {}", s);
                    Class.forName(s.getName());
                }));
        log.info("注册对象转换器-结束");
    }

}
