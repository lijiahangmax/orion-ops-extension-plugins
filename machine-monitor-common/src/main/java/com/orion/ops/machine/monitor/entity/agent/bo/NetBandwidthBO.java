package com.orion.ops.machine.monitor.entity.agent.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网络带宽指标
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 14:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "网络带宽指标")
public class NetBandwidthBO extends BaseRangeBO {

    @ApiModelProperty(value = "上行流量 kb sentSize")
    private Long ss;

    @ApiModelProperty(value = "下行流量 kb receivedSize")
    private Long rs;

    @ApiModelProperty(value = "上行包 sentPacket")
    private Long sp;

    @ApiModelProperty(value = "下行包 receivedPacket")
    private Long rp;

}
