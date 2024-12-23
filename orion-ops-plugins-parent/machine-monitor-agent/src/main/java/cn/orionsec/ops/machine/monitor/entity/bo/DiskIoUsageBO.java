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
