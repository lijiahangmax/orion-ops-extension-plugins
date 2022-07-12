package com.orion.ops.monitor.common.entity.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 网络带宽流量信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/27 18:23
 */
@Data
@ApiModel(value = "网络带宽流量信息")
public class NetBandwidthDTO {

    @ApiModelProperty(value = "上行流量流量 byte")
    private Long up;

    @ApiModelProperty(value = "下行流量流量 byte")
    private Long down;

}
