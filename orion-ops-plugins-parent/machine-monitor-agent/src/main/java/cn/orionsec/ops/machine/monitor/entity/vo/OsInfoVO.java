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
import cn.orionsec.ops.machine.monitor.entity.dto.OsInfoDTO;
import cn.orionsec.ops.machine.monitor.utils.Formats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 17:50
 */
@Data
@ApiModel(value = "系统信息")
public class OsInfoVO {

    @ApiModelProperty(value = "进程id")
    private Integer pid;

    @ApiModelProperty(value = "系统名称")
    private String osName;

    @ApiModelProperty(value = "启动时间")
    private String uptime;

    @ApiModelProperty(value = "cpu名称")
    private String cpuName;

    @ApiModelProperty(value = "cpu物理核心数")
    private Integer cpuPhysicalCore;

    @ApiModelProperty(value = "cpu逻辑核心数")
    private Integer cpuLogicalCore;

    @ApiModelProperty(value = "总内存")
    private String totalMemory;

    @ApiModelProperty(value = "主机名")
    private String hostname;

    @ApiModelProperty(value = "用户名")
    private String username;

    static {
        TypeStore.STORE.register(OsInfoDTO.class, OsInfoVO.class, p -> {
            OsInfoVO vo = new OsInfoVO();
            vo.setPid(p.getPid());
            vo.setOsName(p.getOsName());
            vo.setUptime(Formats.formatElapsedSecs(p.getUptime()));
            vo.setCpuName(p.getCpuName());
            vo.setCpuPhysicalCore(p.getCpuPhysicalCore());
            vo.setCpuLogicalCore(p.getCpuLogicalCore());
            vo.setTotalMemory(Files1.getSize(p.getTotalMemory()));
            vo.setHostname(p.getHostname());
            vo.setUsername(p.getUsername());
            return vo;
        });
    }

}
