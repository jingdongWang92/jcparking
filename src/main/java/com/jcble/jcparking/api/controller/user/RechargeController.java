package com.jcble.jcparking.api.controller.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.ReservationReqDto;
import com.jcble.jcparking.common.dto.response.RechargeRespDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.service.user.UserService;

/**
 * 余额充值
 * @author Jingdong Wang 
 * @date 2017年4月12日 下午5:56:30
 *
 */
@Controller
public class RechargeController extends BaseController {
	
	@Autowired
	private UserService userService;

	/**
	 *  5.35 余额充值
	 * @param reqDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/balance/recharge", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto balanceRecharge(@RequestBody ReservationReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getUserId()) || StringUtils.isBlank(reqDto.getPayWay()) || 
					reqDto.getPayFee() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			RechargeRespDto payInfo = userService.balanceRecharge(reqDto);
			resp.setPayload(payInfo);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
