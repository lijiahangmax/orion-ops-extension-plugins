package com.orion.ops.monitor.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Jiahang Li
 */
@SpringBootApplication
@ComponentScan("com.orion.ops.monitor.*")
@ImportResource(locations = {"classpath:config/spring-common.xml"})
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
