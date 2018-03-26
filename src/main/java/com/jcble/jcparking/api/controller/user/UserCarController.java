package com.jcble.jcparking.api.controller.user;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.UserCarReqDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.model.user.UserCar;
import com.jcble.jcparking.common.service.user.UserCarService;

/**
 * 用户车辆模块
 * 
 * @author Jingdong Wang
 * @date 2017年3月13日 下午4:49:26
 *
 */
@Controller
public class UserCarController extends BaseController {
	
	@Autowired
	private UserCarService userCarService;

	/**
	 * 5.9 用户获取个人车辆信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cars/{userId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getCars(@PathVariable String userId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			List<UserCar> cars = userCarService.getCarsByUserId(userId);
			resp.setPayload(cars);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 5.10 添加用户车辆信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto createUserCar(@RequestBody UserCarReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getCarNumber()) || reqDto.getUserId() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			userCarService.createUserCar(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 5.11 删除用户绑定车辆信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/{carId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public ResponseDto deleteCar(@PathVariable Integer carId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			userCarService.deleteCar(carId);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 修改车辆信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/{carId}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ResponseDto updateCar(@PathVariable Integer carId, @RequestBody UserCarReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getCarNumber())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			reqDto.setCarId(carId);
			userCarService.updateCar(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
