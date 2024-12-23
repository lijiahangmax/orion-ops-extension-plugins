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
package cn.orionsec.ops.machine.monitor.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 硬盘 IO 使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/28 10:25
 */
@Data
@ApiModel(value = "硬盘io使用信息")
public class DiskIoUsageDTO {

    @ApiModelProperty(value = "名称")
    private String model;

    @ApiModelProperty(value = "读取次数")
    private Long readCount;

    @ApiModelProperty(value = "读取字节数")
    private Long readBytes;

    @ApiModelProperty(value = "写入次数")
    private Long writeCount;

    @ApiModelProperty(value = "写入字节数")
    private Long writeBytes;

    @ApiModelProperty(value = "使用时间")
    private Long usageTime;

}
