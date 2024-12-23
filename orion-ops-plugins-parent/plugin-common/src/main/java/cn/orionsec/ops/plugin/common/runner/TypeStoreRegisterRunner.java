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
package cn.orionsec.ops.plugin.common.runner;

import cn.orionsec.kit.lang.support.Attempt;
import cn.orionsec.kit.lang.utils.reflect.PackageScanner;
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
@Order(1000)
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
