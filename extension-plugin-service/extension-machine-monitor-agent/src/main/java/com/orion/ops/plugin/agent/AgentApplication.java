package com.orion.ops.plugin.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Jiahang Li
 */
@SpringBootApplication
@ComponentScan("com.orion.ops.plugin.*")
@ImportResource(locations = {"classpath:config/spring-common.xml"})
public class AgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentApplication.class, args);
    }

}
