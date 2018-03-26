package com.jcble.jcparking.api.interceptor;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.collect.Maps;
import com.jcble.jcparking.common.dto.response.ResponseDto;

/**
 * 打印请求日志
 * 
 * @author Jingdong Wang
 * @date 2017年3月24日 上午9:02:47
 *
 */
@Component
@Aspect
public class LogPrintControllerAspect {
	private static final Logger logger = LoggerFactory.getLogger(LogPrintControllerAspect.class);

	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void cutController() {
	}

	@Around("cutController()")
	public Object recordSysLog(ProceedingJoinPoint point) throws Throwable {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

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

		long beginTime = System.currentTimeMillis();

		Object result = point.proceed();
		// System.out.println(JsonFormatUtil.formatJson(JsonUtil.object2json(result)));
		long endTime = System.currentTimeMillis();
		long consumeTime = endTime - beginTime;

		if (result != null && result instanceof ResponseDto) {
			logger.info(String.format("parking-api==>%s consume %d millis, resp ==> %s", request.getRequestURI(),
					consumeTime, result.toString()));
		}
		return result;
	}
}
