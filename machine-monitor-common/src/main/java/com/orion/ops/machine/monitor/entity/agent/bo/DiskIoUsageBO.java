package com.orion.ops.machine.monitor.entity.agent.bo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "硬盘io使用指标")
public class DiskIoUsageBO extends BaseRangeBO {

    @JSONField(serialize = false)
    @ApiModelProperty(value = "硬盘序列")
    private String seq;

    @ApiModelProperty(value = "读取次数 readCount")
    private Long rc;

    @ApiModelProperty(value = "读取流量数 kb readSize")
    private Long rs;

    @ApiModelProperty(value = "写入次数 writeCount")
    private Long wc;

    @ApiModelProperty(value = "写入流量数 kb writeSize")
    private Long ws;

    @ApiModelProperty(value = "使用时间 ms usageTime")
    private Long ut;

}
