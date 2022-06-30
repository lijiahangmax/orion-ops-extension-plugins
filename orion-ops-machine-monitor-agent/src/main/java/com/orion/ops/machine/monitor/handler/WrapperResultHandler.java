package com.orion.ops.machine.monitor.handler;

import com.orion.constant.StandardContentType;
import com.orion.lang.wrapper.HttpWrapper;
import com.orion.lang.wrapper.RpcWrapper;
import com.orion.ops.machine.monitor.annotation.IgnoreWrapper;
import com.orion.ops.machine.monitor.annotation.RestWrapper;
import com.orion.servlet.web.Servlets;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;

/**
 * 包装结果处理器
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2021/4/2 16:52
 */
@Component
public class WrapperResultHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        // 统一返回值
        if (!methodParameter.getContainingClass().isAnnotationPresent(RestWrapper.class)) {
            return false;
        }
        return !methodParameter.hasMethodAnnotation(IgnoreWrapper.class);
        // && methodParameter.getExecutable().getAnnotatedReturnType().getType() != Void.TYPE;
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        if (response == null) {
            return;
        }
        HttpWrapper<?> wrapper;
        if (o instanceof HttpWrapper) {
            wrapper = (HttpWrapper<?>) o;
        } else if (o instanceof RpcWrapper) {
            wrapper = ((RpcWrapper<?>) o).toHttpWrapper();
        } else {
            wrapper = new HttpWrapper<>().data(o);
        }
        modelAndViewContainer.setRequestHandled(true);
        response.setContentType(StandardContentType.APPLICATION_JSON);
        Servlets.transfer(response, wrapper.toJsonString().getBytes());
    }

}
