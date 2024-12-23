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

import cn.orionsec.kit.lang.utils.convert.TypeStore;
import cn.orionsec.kit.lang.utils.io.Files1;
import cn.orionsec.ops.machine.monitor.entity.dto.MemoryUsageDTO;
import cn.orionsec.ops.machine.monitor.utils.Formats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 内存使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 16:35
 */
@Data
@ApiModel(value = "内存使用信息")
public class MemoryUsageVO {

    @ApiModelProperty(value = "总内存")
    private String totalMemory;

    @ApiModelProperty(value = "使用内存")
    private String usageMemory;

    @ApiModelProperty(value = "空闲内存")
    private String freeMemory;

    @ApiModelProperty(value = "内存使用率")
    private Double usage;

    static {
        TypeStore.STORE.register(MemoryUsageDTO.class, MemoryUsageVO.class, p -> {
            MemoryUsageVO vo = new MemoryUsageVO();
            vo.setTotalMemory(Files1.getSize(p.getTotalMemory()));
            vo.setUsageMemory(Files1.getSize(p.getUsageMemory()));
            vo.setFreeMemory(Files1.getSize(p.getFreeMemory()));
            vo.setUsage(Formats.roundToDouble(p.getUsage()));
            return vo;
        });
    }

}
