package com.jcble.jcparking.api.controller.system;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.dto.response.WechatRespDto;
import com.jcble.jcparking.common.service.system.SystemConfigService;

/**
 * 获取系统参数
 * @author Jingdong Wang 
 * @date 2017年4月7日 下午3:51:21
 *
 */
@Controller
public class SystemConfigController extends BaseController {
	
	@Autowired
	private SystemConfigService service;

	
	/**
	 * 获取微信appid
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wechat/appid", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getWechatAppId() throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			String appId = service.getSysCfgParamValueByKey("weixin_pay_appid");
			WechatRespDto wechatApp = new WechatRespDto();
			wechatApp.setAppId(appId);
			resp.setPayload(wechatApp);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	


}
