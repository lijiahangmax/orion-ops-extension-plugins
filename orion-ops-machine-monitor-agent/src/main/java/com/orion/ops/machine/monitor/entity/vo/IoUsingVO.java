package com.orion.ops.machine.monitor.entity.vo;

import com.orion.ops.machine.monitor.entity.dto.IoUsingDTO;
import com.orion.utils.convert.TypeStore;
import com.orion.utils.io.Files1;
import lombok.Data;

/**
 * IO 使用信息
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/30 18:46
 */
@Data
public class IoUsingVO {

    /**
     * 名称
     */
    private String name;

    /**
     * 读取次数
     */
    private Long readCount;

    /**
     * 读取大小
     */
    private String readSize;

    /**
     * 写入次数
     */
    private Long writeCount;

    /**
     * 写入大小
     */
    private String writeSize;

    static {
        TypeStore.STORE.register(IoUsingDTO.class, IoUsingVO.class, p -> {
            IoUsingVO vo = new IoUsingVO();
            vo.setName(p.getName());
            vo.setReadCount(p.getReadCount());
            vo.setReadSize(Files1.getSize(p.getReadBytes()));
            vo.setWriteCount(p.getWriteCount());
            vo.setWriteSize(Files1.getSize(p.getWriteBytes()));
            return vo;
        });
    }

}
