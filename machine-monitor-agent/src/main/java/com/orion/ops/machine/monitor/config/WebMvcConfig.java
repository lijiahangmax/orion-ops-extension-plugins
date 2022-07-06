package com.orion.ops.machine.monitor.config;

import com.orion.id.UUIds;
import com.orion.lang.wrapper.HttpWrapper;
import com.orion.ops.machine.monitor.interceptor.AccessTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * spring mvc 配置
 *
 * @author Jiahang Li
 * @version 1.0.0
 * @since 2022/6/29 17:00
 */
@Slf4j
@Configuration
@RestControllerAdvice
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private AccessTokenInterceptor accessTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 访问拦截器
        registry.addInterceptor(accessTokenInterceptor)
                .addPathPatterns("/**")
                .order(5);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @ExceptionHandler(value = Exception.class)
    public HttpWrapper<?> exceptionHandler(HttpServletRequest request, Exception ex) {
        String traceId = UUIds.random32();
        log.error("exceptionHandler TRACE-{} url: {}, 抛出异常: {}, message: {}", traceId, request.getRequestURI(), ex.getClass(), ex.getMessage(), ex);
        return HttpWrapper.error().data(traceId);
    }

}
