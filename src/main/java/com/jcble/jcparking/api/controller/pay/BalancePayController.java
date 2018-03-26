package com.jcble.jcparking.api.controller.pay;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.PayRecordReqDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.service.user.UserService;

/**
 * 余额支付接口
 * 
 * @author Jingdong Wang
 * @date 2017年4月6日 下午2:42:27
 *
 */
@Controller
@RequestMapping("/balance")
public class BalancePayController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 余额支付接口
	 * @param reqDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto createOrder(@RequestBody PayRecordReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || reqDto.getAmount() == null || StringUtils.isBlank(reqDto.getUserId())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			userService.balancePay(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
