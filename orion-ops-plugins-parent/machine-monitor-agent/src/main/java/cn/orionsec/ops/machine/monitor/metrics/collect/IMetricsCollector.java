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
package cn.orionsec.ops.machine.monitor.metrics.collect;

import cn.orionsec.kit.lang.utils.collect.Lists;
import cn.orionsec.ops.machine.monitor.entity.bo.BaseRangeBO;

import java.util.List;

/**
 * 数据采集器接口
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 11:28
 */
public interface IMetricsCollector<T extends BaseRangeBO> {

    /**
     * 采集数据
     *
     * @return 数据
     */
    T collect();

    /**
     * 采集数据
     *
     * @return 数据集合
     */
    default List<T> collectAsList() {
        return Lists.singleton(this.collect());
    }

}
