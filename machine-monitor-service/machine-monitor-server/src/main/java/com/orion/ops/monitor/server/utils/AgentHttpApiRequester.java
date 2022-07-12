package com.orion.ops.monitor.server.utils;

import com.orion.ops.monitor.common.handler.http.AgentHttpApi;
import com.orion.ops.monitor.common.handler.http.BaseHttpApiRequester;
import com.orion.ops.monitor.common.handler.http.HttpApiRequest;
import com.orion.ops.monitor.server.constant.Currents;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * agent http api 请求器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/8 18:26
 */
@Component
public class AgentHttpApiRequester extends BaseHttpApiRequester<AgentHttpApi> {

    @Value("${monitor.agent.access.key}")
    private String accessKey;

    @Value("${monitor.agent.access.secret}")
    private String accessSecret;

    @Override
    public HttpApiRequest getRequest(AgentHttpApi api) {
        HttpApiRequest req = new HttpApiRequest(Currents.getAgentHost(), api);
        req.header(accessKey, accessSecret);
        return req;
    }

}
