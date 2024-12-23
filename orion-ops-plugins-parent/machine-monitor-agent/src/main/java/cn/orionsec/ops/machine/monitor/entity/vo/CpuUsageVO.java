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
import cn.orionsec.ops.machine.monitor.entity.dto.CpuUsageDTO;
import cn.orionsec.ops.machine.monitor.utils.Formats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * cpu 使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 19:04
 */
@Data
@ApiModel(value = "cpu使用信息")
public class CpuUsageVO {

    @ApiModelProperty(value = "使用率")
    private Double usage;

    @ApiModelProperty(value = "核心使用率")
    private List<Double> coreUsage;

    static {
        TypeStore.STORE.register(CpuUsageDTO.class, CpuUsageVO.class, p -> {
            CpuUsageVO vo = new CpuUsageVO();
            vo.setUsage(Formats.roundToDouble(p.getUsage()));
            vo.setCoreUsage(p.getCoreUsage().stream()
                    .map(Formats::roundToDouble)
                    .collect(Collectors.toList()));
            return vo;
        });
    }

}
