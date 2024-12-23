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
import cn.orionsec.kit.lang.utils.time.Dates;
import cn.orionsec.ops.machine.monitor.entity.dto.SystemProcessDTO;
import cn.orionsec.ops.machine.monitor.utils.Formats;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统进程
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 17:44
 */
@Data
@ApiModel(value = "系统进程")
public class SystemProcessVO {

    @ApiModelProperty(value = "进程id")
    private Integer pid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "用户")
    private String user;

    @ApiModelProperty(value = "cpu使用率")
    private Double cpuUsage;

    @ApiModelProperty(value = "使用内存")
    private String memoryUsage;

    @ApiModelProperty(value = "句柄数")
    private Long openFile;

    @ApiModelProperty(value = "启用时长")
    private String uptime;

    @ApiModelProperty(value = "命令行")
    private String commandLine;

    static {
        TypeStore.STORE.register(SystemProcessDTO.class, SystemProcessVO.class, p -> {
            SystemProcessVO vo = new SystemProcessVO();
            vo.setPid(p.getPid());
            vo.setName(p.getName());
            vo.setUser(p.getUser());
            vo.setCpuUsage(Formats.roundToDouble(p.getCpuUsage()));
            vo.setMemoryUsage(Files1.getSize(p.getMemoryUsage()));
            vo.setOpenFile(p.getOpenFile());
            vo.setUptime(Formats.formatElapsedSecs(p.getUptime() / Dates.SECOND_STAMP));
            vo.setCommandLine(p.getCommandLine());
            return vo;
        });
    }

}
