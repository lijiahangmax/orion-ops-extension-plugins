package com.orion.ops.machine.monitor.constant;

import com.orion.lang.annotation.Removed;

/**
 * 常量
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 15:06
 */
public class Const extends com.orion.lang.constant.Const {

    private Const() {
    }

    public static final String OPS_MONITOR_AGENT = "ops-monitor-agent";

    @Removed("remove by update orion-kit")
    public static final int MBP = 1024 * 128;

}
