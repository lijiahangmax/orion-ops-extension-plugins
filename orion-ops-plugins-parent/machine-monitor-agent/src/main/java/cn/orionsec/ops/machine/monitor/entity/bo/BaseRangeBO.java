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

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 时间区间
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 11:23
 */
@Data
@ApiModel(value = "时间区间")
public class BaseRangeBO {

    @JSONField(ordinal = 1)
    @ApiModelProperty(value = "开始时间 (秒) startRange")
    private Long sr;

    @JSONField(ordinal = 2)
    @ApiModelProperty(value = "结束时间 (秒) endRange")
    private Long er;

}
