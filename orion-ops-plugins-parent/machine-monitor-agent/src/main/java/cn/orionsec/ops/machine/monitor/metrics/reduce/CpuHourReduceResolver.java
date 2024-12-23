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
package cn.orionsec.ops.machine.monitor.metrics.reduce;

import cn.orionsec.ops.machine.monitor.entity.bo.CpuUsageBO;
import cn.orionsec.ops.machine.monitor.utils.PathBuilders;
import cn.orionsec.ops.machine.monitor.utils.Utils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 处理器时级数据规约器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/2 16:09
 */
@Slf4j
@Component
public class CpuHourReduceResolver extends BaseMetricsHourReduceResolver<CpuUsageBO> {

    public CpuHourReduceResolver() {
        super((prevHour) -> PathBuilders.getCpuMonthDataPath(Utils.getRangeStartMonth(prevHour)));
    }

    @Override
    protected CpuUsageBO computeHourReduceData(String currentHour, String prevHour) {
        // 设置规约数据
        CpuUsageBO reduce = new CpuUsageBO();
        reduce.setU(this.getAvgReduceData(CpuUsageBO::getU, 3));
        log.debug("处理器时级数据指标 {}", JSON.toJSONString(reduce));
        return reduce;
    }

}
