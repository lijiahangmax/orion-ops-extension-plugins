package com.orion.ops.monitor.common.handler.http;

import com.alibaba.fastjson.TypeReference;
import com.orion.lang.define.wrapper.HttpWrapper;

/**
 * http api 请求器基类
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/8 18:26
 */
public abstract class BaseHttpApiRequester<API extends HttpApiDefined> {

    /**
     * 请求 agent api
     *
     * @param api       api
     * @param dataClass dataClass
     * @param <T>       T
     * @return HttpWrapper
     */
    public <T> HttpWrapper<T> request(API api, Class<T> dataClass) {
        return this.getRequest(api).getHttpWrapper(dataClass);
    }

    /**
     * 请求 agent api
     *
     * @param api  api
     * @param type type
     * @param <T>  T
     * @return T
     */
    public <T> T request(API api, TypeReference<T> type) {
        return this.getRequest(api).getJson(type);
    }

    /**
     * 请求 agent api
     *
     * @param api         api
     * @param requestBody requestBody
     * @param dataClass   dataClass
     * @param <T>         T
     * @return HttpWrapper
     */
    public <T> HttpWrapper<T> request(API api, Object requestBody, Class<T> dataClass) {
        return this.getRequest(api).jsonBody(requestBody).getHttpWrapper(dataClass);
    }

    /**
     * 请求 agent api
     *
     * @param api         api
     * @param requestBody requestBody
     * @param type        type
     * @param <T>         T
     * @return T
     */
    public <T> T request(API api, Object requestBody, TypeReference<T> type) {
        return this.getRequest(api).jsonBody(requestBody).getJson(type);
    }

    /**
     * 获取 api request
     *
     * @param host host
     * @param api  api
     * @return request
     */
    public abstract HttpApiRequest getRequest(API api);

}
