package com.jcble.jcparking.api.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.response.ChargeRuleRespDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.service.common.ChargeRuleService;

@Controller
@RequestMapping("/common")
public class ChargeRuleControll extends BaseController {
	
	@Autowired
	private ChargeRuleService ruleService;

	/**
	 * 获取收费规则
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/charge_rule", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getVerifyCode(@RequestParam(value = "parkinglotId", required = true) Integer parkinglotId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			ChargeRuleRespDto rule = ruleService.getRuleByParkinglot(parkinglotId);
			resp.setPayload(rule);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
