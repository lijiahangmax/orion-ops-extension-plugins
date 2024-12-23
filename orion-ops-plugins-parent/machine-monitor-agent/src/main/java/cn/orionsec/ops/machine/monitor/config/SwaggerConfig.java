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
package cn.orionsec.ops.machine.monitor.config;

import cn.orionsec.kit.lang.utils.collect.Lists;
import cn.orionsec.ops.machine.monitor.constant.Const;
import cn.orionsec.ops.machine.monitor.constant.PropertiesConst;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * swagger 配置
 * <p>
 * http://localhost:9220/doc.html
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/8 10:13
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Profile({"dev"})
public class SwaggerConfig {

    @Value("${agent.access.header}")
    private String accessHeader;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.getApiInfo())
                .securitySchemes(this.getSecuritySchemes())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.orionsec.ops.machine.monitor"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 配置 api 信息
     *
     * @return api info
     */
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("machine-monitor-agent restful API")
                .contact(new Contact(Const.ORION_AUTHOR, Const.ORION_GITEE, Const.ORION_EMAIL))
                .version(PropertiesConst.AGENT_VERSION)
                .description("机器监控agent端api管理")
                .build();
    }

    /**
     * 认证配置
     *
     * @return security scheme
     */
    private List<SecurityScheme> getSecuritySchemes() {
        ApiKey loginToken = new ApiKey(accessHeader, accessHeader, "header");
        return Lists.of(loginToken);
    }

}
