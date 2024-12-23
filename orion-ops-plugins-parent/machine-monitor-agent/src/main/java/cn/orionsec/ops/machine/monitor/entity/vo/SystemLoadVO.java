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
import cn.orionsec.ops.machine.monitor.entity.dto.SystemLoadDTO;
import cn.orionsec.ops.machine.monitor.utils.Formats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统负载
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 18:16
 */
@Data
@ApiModel(value = "系统负载")
public class SystemLoadVO {

    @ApiModelProperty(value = "系统1分种负载")
    private Double oneMinuteLoad;

    @ApiModelProperty(value = "系统5分钟负载")
    private Double fiveMinuteLoad;

    @ApiModelProperty(value = "系统15分钟负载")
    private Double fifteenMinuteLoad;

    static {
        TypeStore.STORE.register(SystemLoadDTO.class, SystemLoadVO.class, p -> {
            SystemLoadVO vo = new SystemLoadVO();
            vo.setOneMinuteLoad(Formats.roundToDouble(p.getOneMinuteLoad()));
            vo.setFiveMinuteLoad(Formats.roundToDouble(p.getFiveMinuteLoad()));
            vo.setFifteenMinuteLoad(Formats.roundToDouble(p.getFifteenMinuteLoad()));
            return vo;
        });
    }

}
