package com.orion.ops.monitor.common.handler.http;

import com.orion.http.support.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * agent 端 http api
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/8 16:06
 */
@Getter
@AllArgsConstructor
public enum AgentHttpApi implements HttpApiDefined {

    /**
     * 端点 ping
     */
    ENDPOINT_PING("/orion/machine-monitor-agent/api/endpoint/ping", HttpMethod.GET),

    /**
     * 端点 version
     */
    ENDPOINT_VERSION("/orion/machine-monitor-agent/api/endpoint/version", HttpMethod.GET),

    /**
     * 端点 获取监控启动状态
     */
    ENDPOINT_STATUS("/orion/machine-monitor-agent/api/endpoint/status", HttpMethod.GET),

    /**
     * 端点 开启监控
     */
    ENDPOINT_START("/orion/machine-monitor-agent/api/endpoint/start", HttpMethod.GET),

    /**
     * 端点 停止监控
     */
    ENDPOINT_STOP("/orion/machine-monitor-agent/api/endpoint/stop", HttpMethod.GET),

    /**
     * 指标 获取机器信息
     */
    METRICS_INFO("/orion/machine-monitor-agent/api/metrics/info", HttpMethod.GET),

    /**
     * 指标 获取系统负载
     */
    METRICS_SYSTEM_LOAD("/orion/machine-monitor-agent/api/metrics/system-load", HttpMethod.GET),

    /**
     * 指标 获取cpu使用信息
     */
    METRICS_CPU_USAGE("/orion/machine-monitor-agent/api/metrics/cpu-usage", HttpMethod.GET),

    /**
     * 指标 获取内存使用信息
     */
    METRICS_MEMORY_USAGE("/orion/machine-monitor-agent/api/metrics/memory-usage", HttpMethod.GET),

    /**
     * 指标 获取网络带宽使用信息
     */
    METRICS_NET_BANDWIDTH("/orion/machine-monitor-agent/api/metrics/net-bandwidth", HttpMethod.GET),

    /**
     * 指标 获取硬盘空间使用信息
     */
    METRICS_DISK_STORE_USAGE("/orion/machine-monitor-agent/api/metrics/disk-store-usage", HttpMethod.GET),

    /**
     * 指标 合并获取硬盘空间使用信息
     */
    METRICS_DISK_STORE_USAGE_MERGE("/orion/machine-monitor-agent/api/metrics/disk-store-usage-merge", HttpMethod.GET),

    /**
     * 指标 获取硬盘名称
     */
    METRICS_DISK_NAME("/orion/machine-monitor-agent/api/metrics/disk-name", HttpMethod.GET),

    /**
     * 指标 获取硬盘io使用信息
     */
    METRICS_DISK_IO_USAGE("/orion/machine-monitor-agent/api/metrics/disk-io-usage", HttpMethod.GET),

    /**
     * 指标 合并获取硬盘io使用信息
     */
    METRICS_DISK_IO_USAGE_MERGE("/orion/machine-monitor-agent/api/metrics/disk-io-usage-merge", HttpMethod.GET),

    /**
     * 指标 获取Top进程
     */
    METRICS_TOP_PROCESSES("/orion/machine-monitor-agent/api/metrics/top-processes", HttpMethod.GET),

    /**
     * 监控 获取cpu数据
     */
    MONITOR_CPU("/orion/machine-monitor-agent/api/metrics/cpu", HttpMethod.POST),

    /**
     * 监控 获取内存数据
     */
    MONITOR_MEMORY("/orion/machine-monitor-agent/api/metrics/memory", HttpMethod.POST),

    /**
     * 监控 获取网络数据
     */
    MONITOR_NET("/orion/machine-monitor-agent/api/metrics/net", HttpMethod.POST),

    /**
     * 监控 获取磁盘数据
     */
    MONITOR_DISK("/orion/machine-monitor-agent/api/metrics/disk", HttpMethod.POST),

    ;

    /**
     * 请求路径
     */
    private final String path;

    /**
     * 请求方法
     */
    private final HttpMethod method;

}
