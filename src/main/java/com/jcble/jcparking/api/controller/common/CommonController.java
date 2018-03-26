package com.jcble.jcparking.api.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.VerifyCodeDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.service.common.CommonService;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {
	
	@Autowired
	private CommonService commonService;

	/**
	 * 获取验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/verify_code", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto getVerifyCode(@RequestBody VerifyCodeDto verifyCodeDto, HttpServletRequest request) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (verifyCodeDto == null || StringUtils.isBlank(verifyCodeDto.getMobile())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			VerifyCodeDto verifyCode = commonService.getVerifyCode(verifyCodeDto);
			request.getSession().setAttribute(verifyCodeDto.getMobile(),verifyCode.getVerifyCode());
			resp.setPayload(verifyCode);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
