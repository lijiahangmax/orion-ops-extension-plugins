package com.orion.ops.machine.monitor.utils;

import com.orion.ops.machine.monitor.handler.http.BaseHttpApiRequester;
import com.orion.ops.machine.monitor.handler.http.HttpApiRequest;
import com.orion.ops.machine.monitor.handler.http.OrionOpsHttpApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * orion-ops http api 请求器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/8 18:26
 */
@Component
public class OrionOpsHttpApiRequester extends BaseHttpApiRequester<OrionOpsHttpApi> {

    @Value("${orion.ops.access.host}")
    private String accessHost;

    @Value("${orion.ops.access.key}")
    private String accessKey;

    @Value("${orion.ops.access.secret}")
    private String accessSecret;

    @Override
    public HttpApiRequest getRequest(OrionOpsHttpApi api) {
        HttpApiRequest req = new HttpApiRequest(accessHost, api);
        req.header(accessKey, accessSecret);
        return req;
    }

}
