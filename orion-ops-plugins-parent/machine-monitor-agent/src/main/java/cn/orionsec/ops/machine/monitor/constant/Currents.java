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
package cn.orionsec.ops.machine.monitor.constant;

import cn.orionsec.kit.lang.utils.Valid;

/**
 * 当前上下文
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/4 23:18
 */
public class Currents {

    private static final ThreadLocal<String> DISK_SEQ = new ThreadLocal<>();

    private Currents() {
    }

    /**
     * 设置硬盘序列
     *
     * @param seq seq
     */
    public static void setDiskSeq(String seq) {
        DISK_SEQ.set(Valid.notNull(seq));
    }

    /**
     * 获取硬盘序列
     *
     * @return seq
     */
    public static String getDiskSeq() {
        return DISK_SEQ.get();
    }

    /**
     * 删除硬盘序列
     */
    public static void removeDiskSeq() {
        DISK_SEQ.remove();
    }

}
