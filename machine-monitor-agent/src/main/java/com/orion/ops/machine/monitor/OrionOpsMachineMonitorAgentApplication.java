package com.orion.ops.machine.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Jiahang Li
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:config/spring-*.xml"})
public class OrionOpsMachineMonitorAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrionOpsMachineMonitorAgentApplication.class, args);
    }

}
