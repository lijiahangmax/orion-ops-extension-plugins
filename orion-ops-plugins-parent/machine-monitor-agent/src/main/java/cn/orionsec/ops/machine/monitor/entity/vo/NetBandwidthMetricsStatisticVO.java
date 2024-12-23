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
package cn.orionsec.ops.machine.monitor.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 网络指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 15:35
 */
@Data
@ApiModel(value = "网络指标统计数据")
public class NetBandwidthMetricsStatisticVO implements BaseMetricsStatisticsEntity {

    @ApiModelProperty(value = "上行速率 mbp/s")
    private MetricsStatisticsVO<Double> sentSpeed;

    @ApiModelProperty(value = "下行速率 mbp/s")
    private MetricsStatisticsVO<Double> recvSpeed;

    @ApiModelProperty(value = "上行包数 个/s")
    private MetricsStatisticsVO<Long> sentPacket;

    @ApiModelProperty(value = "下行包数 个/s")
    private MetricsStatisticsVO<Long> recvPacket;

}
