package com.orion.ops.machine.monitor.constant;

/**
 * 常量
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 15:06
 */
public class Const extends com.orion.constant.Const {

    private Const() {
    }

    public static final String VERSION = "1.0.0";

    public static final String OPS_MONITOR_AGENT = "ops-monitor-agent";

    public static final String ACCESS_TOKEN = "accessToken";

    public static final String PUSH_TOKEN = "pushToken";

    public static final String INVALID_ACCESS_TOKEN = "非法访问";

    public static final String CPU_DATA_PATH = "/cpu";

    public static final String MEMORY_DATA_PATH = "/memory";

    public static final String NET_DATA_PATH = "/net";

    public static final String DISK_DATA_PATH = "/disk";

}
