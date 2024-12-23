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
 * 监控指标统计数据
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 17:53
 */
@Data
@ApiModel(value = "监控指标统计数据")
public class DiskMetricsStatisticVO implements BaseMetricsStatisticsEntity {

    @ApiModelProperty(value = "硬盘写速度 kb/s")
    private MetricsStatisticsVO<Double> readSpeed;

    @ApiModelProperty(value = "硬盘读速度 kb/s")
    private MetricsStatisticsVO<Double> writeSpeed;

    @ApiModelProperty(value = "硬盘读次数 count/s")
    private MetricsStatisticsVO<Long> readCount;

    @ApiModelProperty(value = "硬盘写次数 count/s")
    private MetricsStatisticsVO<Long> writeCount;

    @ApiModelProperty(value = "使用时间 ms")
    private MetricsStatisticsVO<Long> usageTime;

}
