package com.jcble.jcparking.api.interceptor;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.Maps;

/**
 *  控制器相关处理拦截器
 * @author Jingdong Wang 
 * @date 2017年2月24日 上午9:01:28
 *
 */
public class LogPrintControllerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LogPrintControllerInterceptor.class);

    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

    /**
     * 方法调用之前
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Enumeration<String> parameterNames = request.getParameterNames();
        Map<String, String> paramNameMap = Maps.newHashMap();
        String parameterName = null;
        while (parameterNames.hasMoreElements()) {
            parameterName = parameterNames.nextElement();
            if (StringUtils.isBlank(parameterName)) {
                continue;
            }
            paramNameMap.put(parameterName, request.getParameter(parameterName));
        }
        logger.info(request.getRequestURI() + " request==>" + "[" + paramNameMap.toString() + "]");

        long beginTime = System.currentTimeMillis();// 1、开始时间
        startTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）
        return true;
    }

    /**
     * 方法调用全部完成后
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        long endTime = System.currentTimeMillis();// 2、结束时间
        long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
        long consumeTime = endTime - beginTime;// 3、消耗的时间

        logger.info(String.format("parking-api==>%s consume %d millis", request.getRequestURI(), consumeTime,""));
    }
}
