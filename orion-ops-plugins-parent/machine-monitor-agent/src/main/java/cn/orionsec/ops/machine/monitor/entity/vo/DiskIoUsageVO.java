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
import cn.orionsec.ops.machine.monitor.entity.dto.DiskIoUsageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 硬盘 IO 使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 18:46
 */
@Data
@ApiModel(value = "硬盘IO使用信息")
public class DiskIoUsageVO {

    @ApiModelProperty(value = "硬盘名称")
    private String model;

    @ApiModelProperty(value = "读取次数")
    private Long readCount;

    @ApiModelProperty(value = "读取大小")
    private String readSize;

    @ApiModelProperty(value = "写入次数")
    private Long writeCount;

    @ApiModelProperty(value = "写入大小")
    private String writeSize;

    static {
        TypeStore.STORE.register(DiskIoUsageDTO.class, DiskIoUsageVO.class, p -> {
            DiskIoUsageVO vo = new DiskIoUsageVO();
            vo.setModel(p.getModel());
            vo.setReadCount(p.getReadCount());
            vo.setReadSize(Files1.getSize(p.getReadBytes()));
            vo.setWriteCount(p.getWriteCount());
            vo.setWriteSize(Files1.getSize(p.getWriteBytes()));
            return vo;
        });
    }

}
