package com.orion.ops.machine.monitor.utils;

import com.orion.ops.machine.monitor.constant.Const;
import com.orion.utils.Systems;

/**
 * 路径构建器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 17:22
 */
public class PathBuilders {

    private PathBuilders() {
    }

    /**
     * 获取处理器指标数据路径
     *
     * @param time 开始时间
     * @return path
     */
    public static String getCpuDataPath(String time) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + Const.CPU_DATA_PATH
                + "/" + time
                + "." + Const.SUFFIX_LOG;
    }

    /**
     * 获取内存指标数据路径
     *
     * @param time 开始时间
     * @return path
     */
    public static String getMemoryDataPath(String time) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + Const.MEMORY_DATA_PATH
                + "/" + time
                + "." + Const.SUFFIX_LOG;
    }

    /**
     * 获取网卡指标数据路径
     *
     * @param time 开始时间
     * @return path
     */
    public static String getNetDataPath(String time) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + Const.NET_DATA_PATH
                + "/" + time
                + "." + Const.SUFFIX_LOG;
    }

    /**
     * 获取磁盘指标数据路径
     *
     * @param time 开始时间
     * @return path
     */
    public static String getDiskDataPath(String time, String tag) {
        return Systems.HOME_DIR
                + "/" + Const.OPS_MONITOR_AGENT
                + Const.DISK_DATA_PATH
                + "/" + tag
                + "-" + time
                + "." + Const.SUFFIX_LOG;
    }

}
