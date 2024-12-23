/*
 * Copyright (c) 2021 - present Jiahang Li, All rights reserved.
 *
 *   https://om.orionsec.cn
 *
 * Members:
 *   Jiahang Li - ljh1553488six@139.com - author
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.orionsec.ops.machine.monitor.entity.bo;

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
