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
import cn.orionsec.ops.machine.monitor.entity.dto.DiskStoreUsageDTO;
import cn.orionsec.ops.machine.monitor.utils.Formats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 硬盘空间使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 17:30
 */
@Data
@ApiModel(value = "硬盘空间使用信息")
public class DiskStoreUsageVO {

    @ApiModelProperty(value = "硬盘名称")
    private String name;

    @ApiModelProperty(value = "硬盘总空间")
    private String totalSpace;

    @ApiModelProperty(value = "使用空间")
    private String usageSpace;

    @ApiModelProperty(value = "空闲空间")
    private String freeSpace;

    @ApiModelProperty(value = "硬盘使用率")
    private Double usage;

    static {
        TypeStore.STORE.register(DiskStoreUsageDTO.class, DiskStoreUsageVO.class, p -> {
            DiskStoreUsageVO vo = new DiskStoreUsageVO();
            vo.setName(p.getName());
            vo.setTotalSpace(Files1.getSize(p.getTotalSpace()));
            vo.setUsageSpace(Files1.getSize(p.getUsageSpace()));
            vo.setFreeSpace(Files1.getSize(p.getFreeSpace()));
            vo.setUsage(Formats.roundToDouble(p.getUsage() * 100));
            return vo;
        });
    }

}
