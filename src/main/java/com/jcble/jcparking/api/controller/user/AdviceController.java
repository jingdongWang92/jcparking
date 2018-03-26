package com.jcble.jcparking.api.controller.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.AdviceReqDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.service.user.AdviceService;

/**
 * 建议反馈
 * 
 * @author Jingdong Wang
 * @date 2017年3月14日 下午5:08:44
 *
 */
@Controller
public class AdviceController extends BaseController {
	
	@Autowired
	private AdviceService adviceService;

	/**
	 * 5.24 建议反馈
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/advice", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto register(@RequestBody AdviceReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getUserId()) || StringUtils.isBlank(reqDto.getType())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			adviceService.saveAdvice(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
