package com.orion.ops.machine.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Jiahang Li
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:config/spring-*.xml"})
public class AgentApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        // FIXME typeStoreRegisterRunner 升级kit删除
        Class.forName("com.orion.ops.machine.monitor.entity.vo.CpuUsageVO");
        Class.forName("com.orion.ops.machine.monitor.entity.vo.DiskIoUsageVO");
        Class.forName("com.orion.ops.machine.monitor.entity.vo.DiskNameVO");
        Class.forName("com.orion.ops.machine.monitor.entity.vo.DiskStoreUsageVO");
        Class.forName("com.orion.ops.machine.monitor.entity.vo.MemoryUsageVO");
        Class.forName("com.orion.ops.machine.monitor.entity.vo.NetBandwidthVO");
        Class.forName("com.orion.ops.machine.monitor.entity.vo.OsInfoVO");
        Class.forName("com.orion.ops.machine.monitor.entity.vo.SystemLoadVO");
        Class.forName("com.orion.ops.machine.monitor.entity.vo.SystemProcessVO");
        SpringApplication.run(AgentApplication.class, args);
    }

}
