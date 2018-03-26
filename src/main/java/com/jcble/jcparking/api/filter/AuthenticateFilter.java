package com.jcble.jcparking.api.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.jcble.jcparking.common.CommonConstants;
import com.jcble.jcparking.common.dto.response.BaseRespDto;
import com.jcble.jcparking.common.utils.BodyReaderHttpServletRequestWrapper;
import com.jcble.jcparking.common.utils.HttpHelper;
import com.jcble.jcparking.common.utils.JacksonMapper;
import com.jcble.jcparking.common.utils.SignUtil;

/**
 * 请求签名认证过滤器
 * 
 * @author Administrator
 * 
 */
public class AuthenticateFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticateFilter.class);

	private String[] unchecked;

	public void init(FilterConfig arg0) throws ServletException {
		String tempParam = arg0.getInitParameter("unchecked");
		if (StringUtils.isNotBlank(tempParam)) {
			unchecked = tempParam.split(",");
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		response.setContentType("application/json; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		BaseRespDto resp = new BaseRespDto();
		boolean flag = true;
		if (this.isUnChecked(req)) {
			chain.doFilter(req, res);
			return;
		}
		ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(req);
		try {
			String requestMethod = req.getMethod();
			Map<String, String> paramNameMap = Maps.newHashMap();
			String reqSign = "";
			if (CommonConstants.HTTPMETHOD_GET.equals(requestMethod) || CommonConstants.HTTPMETHOD_DELETE.equals(requestMethod)) {
				reqSign = req.getParameter(CommonConstants.PARAMTER_SIGN);
				Enumeration<String> parameterNames = req.getParameterNames();
				String parameterName = null;
				while (parameterNames.hasMoreElements()) {
					parameterName = parameterNames.nextElement();
					if (StringUtils.isBlank(parameterName)) {
						continue;
					}
					paramNameMap.put(parameterName,req.getParameter(parameterName));
				}
			} else {
				String str = HttpHelper.getBodyString(requestWrapper);
				JSONObject jsonObj = JSONObject.parseObject(str);
				reqSign = jsonObj.getString(CommonConstants.PARAMTER_SIGN);
		        Set<String> iterator = jsonObj.keySet();
		        for (String key : iterator) {
					String value = jsonObj.getString(key);
					paramNameMap.put(key, value);
				}
			}
			String sign = SignUtil.signature(paramNameMap, CommonConstants.SECRET_CODE, true, false,
					CommonConstants.PARAMTER_SIGN);
			logger.info("request sign:"+reqSign);
			logger.info("api     sign:"+sign);
			if (!(StringUtils.isNotBlank(reqSign) && reqSign.equals(sign))) {
				flag = false;
			}
		} catch (Exception e) {
			logger.error("签名错误");
			flag = false;
		}

		if (flag) {
			chain.doFilter(requestWrapper, res);
			return;
		} else {
			// 认证失败，返回提示信息
			resp.setError(true);
			resp.setMessage("签名错误");
			String respstr = JacksonMapper.bean2Json(resp);
			res.getWriter().print(respstr);
		}
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 判断是否需要检查
	 * 
	 * @param request
	 *            the request
	 * @return 当不需要检查时返回true,否则返回false
	 */
	private boolean isUnChecked(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();

		if (uri.length() - 1 <= contextPath.length()) {
			return true;
		}
		String temp = null;
		if (unchecked != null && unchecked.length > 0) {
			for (int i = 0; i < unchecked.length; i++) {
				temp = unchecked[i].trim();
				if (uri.startsWith(contextPath + temp)) {
					return true;
				}
			}
		}
		return false;
	}
	

}
