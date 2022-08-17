package com.orion.ops.machine.monitor.constant;

import com.orion.lang.define.thread.NamedThreadFactory;
import com.orion.lang.utils.Systems;
import com.orion.lang.utils.Threads;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 调度线程池
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/8/1 14:38
 */
public class SchedulerPools {

    private SchedulerPools() {
    }

    /**
     * 数据采集调度器
     */
    public static final ScheduledExecutorService COLLECT_SCHEDULER = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("machine-collector-thread-"));

    static {
        Systems.addShutdownHook(() -> {
            Threads.shutdownPoolNow(COLLECT_SCHEDULER, Const.MS_S_3);
        });
    }

}
