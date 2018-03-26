package com.jcble.jcparking.api.controller.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.UserWechatReqDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.model.user.User;
import com.jcble.jcparking.common.service.user.UserWechatService;

/**
 * 用户微信账号
 * 
 * @author Jingdong Wang
 * @date 2017年3月13日 下午3:32:44
 *
 */
@Controller
public class UserWechatController extends BaseController {

	@Autowired
	private UserWechatService userWechatService;
	
	/**
	 * 5.7  用户绑定微信账号
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/{userId}/wechat", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto register(@PathVariable String userId,@RequestBody UserWechatReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(userId) || StringUtils.isBlank(reqDto.getWechatAcc())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			reqDto.setUserId(userId);
			userWechatService.linkWechat(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.8  解除微信账号绑定
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/{userId}/wechat/{wechatId}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseDto deleteWechatLink(@PathVariable Integer wechatId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			userWechatService.deleteWechatLink(wechatId);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.35 微信登录获取用户信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/wechat/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto wechatAuth(@RequestBody UserWechatReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getWechatAcc())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			User user = userWechatService.wechatAuth(reqDto);
			resp.setPayload(user);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
