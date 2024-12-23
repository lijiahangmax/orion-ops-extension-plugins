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
package cn.orionsec.ops.plugin.common.handler.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * orion-ops expose-api 请求器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/9/1 10:31
 */
@Component
public class OrionOpsExposeHttpApiRequester implements HttpApiRequester<OrionOpsExposeHttpApi> {

    private static final String TAG = "orion-ops-api";

    private static String ORION_OPS_ACCESS_HOST;

    private static String ORION_OPS_ACCESS_HEADER;

    private static String ORION_OPS_ACCESS_SECRET;

    private OrionOpsExposeHttpApi api;

    private OrionOpsExposeHttpApiRequester() {
    }

    public OrionOpsExposeHttpApiRequester(OrionOpsExposeHttpApi api) {
        this.api = api;
    }

    /**
     * 获取请求
     *
     * @param api api
     * @return request
     */
    public static HttpApiRequest create(OrionOpsExposeHttpApi api) {
        return new OrionOpsExposeHttpApiRequester(api).getRequest();
    }

    @Override
    public HttpApiRequest getRequest() {
        HttpApiRequest request = new HttpApiRequest(ORION_OPS_ACCESS_HOST, api);
        request.tag(TAG);
        request.header(ORION_OPS_ACCESS_HEADER, ORION_OPS_ACCESS_SECRET);
        return request;
    }

    @Value("${orion.ops.access.host:}")
    private void setOrionOpsAccessHost(String orionOpsAccessHost) {
        ORION_OPS_ACCESS_HOST = orionOpsAccessHost;
    }

    @Value("${orion.ops.access.header:}")
    private void setOrionOpsAccessHeader(String orionOpsAccessHeader) {
        ORION_OPS_ACCESS_HEADER = orionOpsAccessHeader;
    }

    @Value("${orion.ops.access.secret:}")
    private void setOrionOpsAccessSecret(String orionOpsAccessSecret) {
        ORION_OPS_ACCESS_SECRET = orionOpsAccessSecret;
    }

}
