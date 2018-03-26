package com.jcble.jcparking.api.controller.user;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.UserReqDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.model.user.User;
import com.jcble.jcparking.common.service.user.UserService;

/**
 * 用户模块
 * 
 * @author Jingdong Wang
 * @date 2017年3月13日 下午3:32:44
 *
 */
@Controller
public class UserController extends BaseController {
	
	@Autowired
	private UserService userService;

	/**
	 * 5.1  用户注册接口
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto register(@RequestBody UserReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getAccount()) || StringUtils.isBlank(reqDto.getPassword())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			userService.register(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.2 用户登录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/login", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto login(@RequestBody UserReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getAccount()) || StringUtils.isBlank(reqDto.getPassword())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			User user = userService.login(reqDto);
			resp.setPayload(user);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.3 用户获取个人信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getUserInfo(@PathVariable String userId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			User user = userService.getUserById(userId);
			resp.setPayload(user);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.4 用户修改密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/password", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ResponseDto updatePassword(@RequestBody UserReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getAccount()) || StringUtils.isBlank(reqDto.getPassword()) ||
					StringUtils.isBlank(reqDto.getOldPassword())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			userService.updatePassword(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.5 用户找回密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/password", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto findPassword(@RequestBody UserReqDto reqDto,HttpServletRequest request) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getAccount()) || StringUtils.isBlank(reqDto.getPassword()) ||
					StringUtils.isBlank(reqDto.getVcode())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			String code = (String) request.getSession().getAttribute(reqDto.getAccount());
			if (reqDto.getVcode().equals(code)) {
				request.getSession().removeAttribute(reqDto.getAccount());
			} else {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10006);
			}
			userService.findPassword(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.6 更换手机号
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ResponseDto updatePhoneNumber(@RequestBody UserReqDto reqDto,@PathVariable String userId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getPhoneNumber())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			reqDto.setUserId(userId);
			userService.updatePhoneNumber(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
}
