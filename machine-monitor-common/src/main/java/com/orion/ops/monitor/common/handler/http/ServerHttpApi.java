package com.orion.ops.monitor.common.handler.http;

import com.orion.http.support.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * server 端 http api
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/7/8 18:39
 */
@Getter
@AllArgsConstructor
public enum ServerHttpApi implements HttpApiDefined {

    ;

    /**
     * 请求路径
     */
    private final String path;

    /**
     * 请求方法
     */
    private final HttpMethod method;

}
