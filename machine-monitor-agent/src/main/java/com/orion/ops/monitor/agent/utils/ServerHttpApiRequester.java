package com.orion.ops.monitor.agent.utils;

import com.orion.ops.monitor.common.handler.http.BaseHttpApiRequester;
import com.orion.ops.monitor.common.handler.http.HttpApiRequest;
import com.orion.ops.monitor.common.handler.http.ServerHttpApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * server http api 请求器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/8 18:26
 */
@Component
public class ServerHttpApiRequester extends BaseHttpApiRequester<ServerHttpApi> {

    @Value("${monitor.server.push.host}")
    private String accessHost;

    @Value("${monitor.server.push.key}")
    private String accessKey;

    @Value("${monitor.server.push.secret}")
    private String accessSecret;

    @Override
    public HttpApiRequest getRequest(ServerHttpApi api) {
        HttpApiRequest req = new HttpApiRequest(accessHost, api);
        req.header(accessKey, accessSecret);
        return req;
    }

}
