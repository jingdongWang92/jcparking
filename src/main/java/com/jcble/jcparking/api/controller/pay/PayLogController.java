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
import com.jcble.jcparking.common.service.pay.PayLogService;

/**
 * 支付日志接口
 * 
 * @author Jingdong Wang
 * @date 2017年3月21日 上午9:42:27
 *
 */
@Controller
public class PayLogController extends BaseController {

	@Autowired
	private PayLogService paylogService;

	/**
	 * 5.30 支付结果反馈接口
	 * 
	 * @param reqDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paylog", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto createOrder(@RequestBody PayRecordReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getType()) || reqDto.getAmount() == null
					|| StringUtils.isBlank(reqDto.getPayStatus())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			paylogService.payFeedback(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
