package com.orion.ops.machine.monitor.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Objects;

/**
 * 硬盘空间使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/27 18:37
 */
@Data
@ApiModel(value = "硬盘空间使用信息")
public class DiskStoreUsageDTO {

    @ApiModelProperty(value = "硬盘名称")
    private String name;

    @ApiModelProperty(value = "硬盘总空间")
    private Long totalSpace;

    @ApiModelProperty(value = "使用空间")
    private Long usageSpace;

    @ApiModelProperty(value = "空闲空间")
    private Long freeSpace;

    @ApiModelProperty(value = "硬盘使用率")
    private Double usage;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DiskStoreUsageDTO that = (DiskStoreUsageDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
