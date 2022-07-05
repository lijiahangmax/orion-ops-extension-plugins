package com.orion.ops.machine.monitor.entity.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 硬盘 IO 使用指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 15:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DiskIoUsageBO extends BaseRangeBO {

    /**
     * 硬盘序列
     */
    @JSONField(serialize = false)
    private String seq;

    /**
     * 读取次数 readCount
     */
    private Long rc;

    /**
     * 读取流量数 kb readSize
     */
    private Long rs;

    /**
     * 写入次数 writeCount
     */
    private Long wc;

    /**
     * 写入流量数 kb writeSize
     */
    private Long ws;

    /**
     * 使用时间 ms usageTime
     */
    private Long ut;

}
