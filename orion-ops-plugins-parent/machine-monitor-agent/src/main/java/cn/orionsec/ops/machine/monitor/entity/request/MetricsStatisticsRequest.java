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
package cn.orionsec.ops.machine.monitor.entity.request;

import cn.orionsec.ops.machine.monitor.constant.GranularityType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 监控请求
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 17:20
 */
@Data
@ApiModel(value = "监控统计请求")
public class MetricsStatisticsRequest {

    /**
     * @see GranularityType
     */
    @ApiModelProperty(value = "数据粒度")
    private Integer granularity;

    @ApiModelProperty(value = "开始区间 (秒)")
    private Long startRange;

    @ApiModelProperty(value = "结束区间 (秒)")
    private Long endRange;

    @ApiModelProperty(value = "磁盘序列")
    private String seq;

}
