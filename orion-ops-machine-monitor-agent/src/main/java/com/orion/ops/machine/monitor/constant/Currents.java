package com.orion.ops.machine.monitor.constant;

/**
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 23:18
 */
public class Currents {

    private static final ThreadLocal<String> DISK_SEQ = new ThreadLocal<>();

    private Currents() {
    }

    /**
     * 设置硬盘序列
     *
     * @param seq seq
     */
    public static void setDiskSeq(String seq) {
        DISK_SEQ.set(seq);
    }

    /**
     * 获取硬盘序列
     *
     * @return seq
     */
    public static String getDiskSeq() {
        return DISK_SEQ.get();
    }

    /**
     * 删除硬盘序列
     */
    public static void removeDiskSeq() {
        DISK_SEQ.remove();
    }

}
