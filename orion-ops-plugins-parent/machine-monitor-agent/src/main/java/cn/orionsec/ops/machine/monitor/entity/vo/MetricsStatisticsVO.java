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

import cn.orionsec.kit.lang.define.wrapper.TimestampValue;
import cn.orionsec.kit.lang.utils.collect.Lists;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 指标统计
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/5 15:07
 */
@Data
@ApiModel(value = "指标统计")
public class MetricsStatisticsVO<T> {

    @JSONField(ordinal = 0)
    @ApiModelProperty(value = "最大值")
    private T max;

    @JSONField(ordinal = 1)
    @ApiModelProperty(value = "最小值")
    private T min;

    @JSONField(ordinal = 2)
    @ApiModelProperty(value = "平均值")
    private Double avg;

    @JSONField(ordinal = 3)
    @ApiModelProperty(value = "指标")
    private List<TimestampValue<T>> metrics;

    public MetricsStatisticsVO() {
        this.metrics = Lists.newList();
    }

}
