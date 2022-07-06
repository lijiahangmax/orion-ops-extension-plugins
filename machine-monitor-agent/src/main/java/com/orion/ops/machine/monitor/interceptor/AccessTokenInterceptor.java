package com.orion.ops.machine.monitor.interceptor;

import com.orion.constant.StandardContentType;
import com.orion.lang.wrapper.HttpWrapper;
import com.orion.ops.machine.monitor.constant.Const;
import com.orion.ops.machine.monitor.constant.ResultCode;
import com.orion.servlet.web.Servlets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 访问拦截器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 17:02
 */
@Component
public class AccessTokenInterceptor implements HandlerInterceptor {

    @Value("${access.token}")
    private String accessToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String accessTokenHeader = request.getHeader(Const.ACCESS_TOKEN);
        if (accessToken.equals(accessTokenHeader)) {
            return true;
        }
        // 非法访问
        response.setContentType(StandardContentType.APPLICATION_JSON);
        Servlets.transfer(response, HttpWrapper.of(ResultCode.INVALID_ACCESS_TOKEN).toJsonString().getBytes());
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

}
