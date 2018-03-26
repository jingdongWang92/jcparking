package com.jcble.jcparking.api.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcble.jcparking.common.CommonConstants;
import com.jcble.jcparking.common.service.devices.DevicesService;
import com.jcble.jcparking.common.utils.SpringBeanUtils;

import baseproj.common.util.LoggerUtil;

/**
 * 启动初始化系统参数
 * 
 * @author Jingdong Wang
 * @date 2017年3月1日 下午3:43:22
 *
 */
public class SystemInitServlet extends HttpServlet {
	private static final long serialVersionUID = 8116219822239884792L;

	protected static final Logger logger = LoggerFactory.getLogger(SystemInitServlet.class);

	/**
	 * 系统初始化
	 * 
	 * @return @throws
	 */
	@Override
	public void init() throws ServletException {
		String version = CommonConstants.DEFAULT_VERSION;
		String result = "failed";
		try {
			result = "success";
			// 获取地磁上报数据
			createRabbitLink();
			logger.info("\n********* parking-api initialize all config successful! *********");
		} catch (Exception ex) {
			logger.error("\n********* parking-api initialize fail! *********", ex);
			throw new ServletException(ex);
		} finally {
			// 将启动的日志记录到tomcat/logs/version.txt中
			LoggerUtil.writeStartLog("parking-api start " + result + "! Version: " + version + "\n");
		}
	}

	/**
	 * Destroy.
	 * 
	 * @version
	 */
	@Override
	public void destroy() {
		
	}
	
	public void createRabbitLink() {
		DevicesService service = (DevicesService) SpringBeanUtils.getSpringBean("devicesServiceImpl");
		try {
			service.createRabbitLink();
		} catch (Exception e) {
			logger.info("\n********* create rabbitmq link failed! *********");
		}
		logger.info("\n********* create rabbitmq link successful! *********");

	}
}
