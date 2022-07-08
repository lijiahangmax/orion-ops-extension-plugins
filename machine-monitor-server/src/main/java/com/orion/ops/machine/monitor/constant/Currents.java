package com.orion.ops.machine.monitor.constant;

import com.orion.lang.utils.Valid;

/**
 * 当前上下文
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/8 18:47
 */
public class Currents {

    private static final ThreadLocal<String> AGENT_HOST = new ThreadLocal<>();

    private Currents() {
    }

    /**
     * 设置 agent 端请求主机
     *
     * @param seq seq
     */
    public static void setAgentHost(String seq) {
        AGENT_HOST.set(Valid.notNull(seq));
    }

    /**
     * 获取 agent 端请求主机
     *
     * @return seq
     */
    public static String getAgentHost() {
        return AGENT_HOST.get();
    }

    /**
     * 删除 agent 端请求主机
     */
    public static void removeDiskSeq() {
        AGENT_HOST.remove();
    }

}
