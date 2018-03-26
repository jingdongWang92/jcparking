package com.jcble.jcparking.api.controller.pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.PayReqDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.service.pay.WechatPayService;
import com.jcble.jcparking.common.utils.Generator;
import com.jcble.jcparking.common.utils.HttpHelper;

import weixin.popular.bean.paymch.UnifiedorderResult;

/**
 * 支付回调函数
 * 
 * @author Jingdong Wang
 * @date 2017年3月22日 下午3:05:33
 *
 */
@Controller
@RequestMapping("/weixin/pay")
public class WxPayController extends BaseController {

	@Autowired
	protected WechatPayService wxPayService;

	/**
	 * 微信完成支付通知接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/notify")
	public void handleFlightPayNotify(HttpServletRequest request, HttpServletResponse response, String source)
			throws Exception {
		logger.info("======================微信支付结果通知开始=========================");
		wxPayService.handleWxPayNotify(request, response, source);
		logger.info("======================微信支付结果通知结束=========================");
	}

	/**
	 * 生成微信支付订单
	 * 
	 * @param orderId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/orderinfo", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto topay(@RequestBody PayReqDto reqDto,HttpServletRequest request) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(reqDto == null || StringUtils.isBlank(reqDto.getOrderNo()) || StringUtils.isBlank(reqDto.getOrderType()) || reqDto.getPayFee() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			// 获取客户端ip
			String clientIpAddr = HttpHelper.getIpAddr(request);
			String tradeNo = Generator.generatePayOrderNo(reqDto.getOrderNo());
			UnifiedorderResult orderInfo = wxPayService.getWxPayOrderInfo(clientIpAddr,tradeNo,reqDto.getPayFee().toString(),reqDto.getOrderType());
			resp.setPayload(orderInfo);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
