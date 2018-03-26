package com.jcble.jcparking.api.controller.parking;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.ParkingReqDto;
import com.jcble.jcparking.common.dto.response.CarPassRespDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.service.parking.CarPassService;

/**
 * 停车相关控制器
 * 
 * @author Jingdong Wang
 * @date 2017年4月10日 上午9:58:52
 *
 */
@Controller
public class CarPassController extends BaseController {
	
	@Autowired
	private CarPassService carPassService;

	/**
	 * 6.1 车辆未入场 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/in", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto carIn(@RequestBody ParkingReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(reqDto.getParkinglotId() == null || StringUtils.isBlank(reqDto.getCarNumber())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			CarPassRespDto command = carPassService.carIn(reqDto);
			resp.setPayload(command);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 6.2 处理过车接口
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/passed", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto carPassed(HttpServletRequest request, @RequestBody ParkingReqDto reqDto)
			throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(reqDto.getParkinglotId() == null || StringUtils.isBlank(reqDto.getCarNumber()) || StringUtils.isBlank(reqDto.getInOrOut())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			carPassService.carPass(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 6.3 车辆未出场 (道闸请求接口)
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/out", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto carOut(@RequestBody ParkingReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(reqDto.getParkinglotId() == null || StringUtils.isBlank(reqDto.getCarNumber())) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			CarPassRespDto command = carPassService.carOut(reqDto);
			resp.setPayload(command);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
}
