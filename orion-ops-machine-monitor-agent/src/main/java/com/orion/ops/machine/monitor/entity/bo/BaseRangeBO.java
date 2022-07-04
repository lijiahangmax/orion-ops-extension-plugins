package com.orion.ops.machine.monitor.entity.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.orion.utils.time.Dates;
import lombok.Data;

/**
 * 时间区间
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/1 11:23
 */
@Data
public class BaseRangeBO {

    /**
     * 开始时间 秒 startRange
     */
    @JSONField(ordinal = 1)
    private Long sr;

    /**
     * 结束时间 秒 endRange
     */
    @JSONField(ordinal = 2)
    private Long er;

    // TODO
    public void setSr(Long sr) {
        this.sr = sr / Dates.SECOND_STAMP;
    }

    // TODO
    public void setEr(Long er) {
        this.er = er / Dates.SECOND_STAMP;
    }

}
