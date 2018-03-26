package com.jcble.jcparking.api.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.AdminReqDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.model.admin.Admin;
import com.jcble.jcparking.common.service.admin.AdminService;

/**
 * 管理员
 * 
 * @author Jingdong Wang
 * @date 2017年3月1日 下午2:41:17
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

	@Autowired
	private AdminService adminService;

	/**
	 * 管理员登录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto getParkings(@RequestBody AdminReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getAccount())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			Admin admin = adminService.login(reqDto);
			resp.setPayload(admin);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 管理员找回密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/password", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ResponseDto resetPassword(@RequestBody AdminReqDto reqDto, HttpServletRequest request) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getAccount())
					|| StringUtils.isBlank(reqDto.getPassword()) || StringUtils.isBlank(reqDto.getVcode())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			String code = (String) request.getSession().getAttribute(reqDto.getAccount());
			if (reqDto.getVcode().equals(code)) {
				request.getSession().removeAttribute(reqDto.getAccount());
			} else {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10006);
			}
			adminService.resetPassword(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 提交极光用户注册ID接口
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/jpush_id", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ResponseDto setJpsuhRegistrationID(@RequestBody AdminReqDto reqDto, HttpServletRequest request) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getAccount())
					|| StringUtils.isBlank(reqDto.getJpushId())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			adminService.setRegistrationID(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
