package com.jcble.jcparking.api.controller.pay;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.PayReqDto;
import com.jcble.jcparking.common.dto.response.AlipayOrderRespDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.service.admin.ParkingOrderService;
import com.jcble.jcparking.common.service.pay.AliPayService;
import com.jcble.jcparking.common.utils.Generator;


/**
 * 支付宝支付
 * 
 * @author Jingdong Wang
 * @date 2017年3月22日 下午3:05:33
 *
 */
@Controller
@RequestMapping("/alipay")
public class AliPayController extends BaseController {

	@Autowired
	protected AliPayService aliPayService;
	@Autowired
	protected ParkingOrderService orderService;

	/**
	 * 支付宝完成支付通知接口
	 */
	@RequestMapping(value = "/notify",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String handleFlightPayNotify(HttpServletRequest request) throws Exception {
		System.out.println("==========================支付宝通知开始==============================");
		String respStr = "";
		try {
			respStr = aliPayService.handleAliPayNotify(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("==========================支付宝通知结束==============================");
		return respStr;
	}

	/**
	 * 生成支付宝支付信息
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/orderinfo", method = RequestMethod.POST,produces = "application/json")
	@ResponseBody
	public ResponseDto topay(@RequestBody PayReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(reqDto == null || StringUtils.isBlank(reqDto.getOrderNo()) || StringUtils.isBlank(reqDto.getOrderType()) || reqDto.getPayFee() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			//生成交易订单
			String tradeNo = Generator.generatePayOrderNo(reqDto.getOrderNo());
			String orderinfoStr = aliPayService.getAlipayOrderInfo(tradeNo,reqDto.getPayFee().toString(),reqDto.getOrderType());
			AlipayOrderRespDto orderInfo = new AlipayOrderRespDto();
			orderInfo.setOrderInfo(orderinfoStr);
			resp.setPayload(orderInfo);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
}
