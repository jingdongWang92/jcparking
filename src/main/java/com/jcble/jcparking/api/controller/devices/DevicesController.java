package com.jcble.jcparking.api.controller.devices;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.request.BindDevicesReqDto;
import com.jcble.jcparking.common.dto.request.DevicesOpReqDto;
import com.jcble.jcparking.common.dto.response.DeviceDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.service.devices.DevicesService;

/**
 * 设备相关控制器
 * 
 * @author Jingdong Wang
 *
 */
@Controller
public class DevicesController extends BaseController {

	@Autowired
	private DevicesService devicesService;

	/**
	 * 绑定车位锁/地磁设备到车位
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/devices", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto bindDeviceToParking(HttpServletRequest request,@RequestBody BindDevicesReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(reqDto == null || reqDto.getOperatorId() == null || reqDto.getDevices() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			List<DeviceDto> deviceResp = devicesService.bindDeviceToParking(reqDto);
			resp.setPayload(deviceResp);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 升起车位锁
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/devices/lock/{serialNumber}/up", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseDto controlLockUp(@PathVariable String serialNumber,@RequestBody DevicesOpReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(reqDto == null || reqDto.getOperatorId() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			reqDto.setSerialNumber(serialNumber);
			devicesService.controlLockUp(reqDto);
			
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 升起车位锁
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/devices/lock/{serialNumber}/down", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseDto controlLockUpDown(@PathVariable String serialNumber,@RequestBody DevicesOpReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(reqDto == null || reqDto.getOperatorId() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			reqDto.setSerialNumber(serialNumber);
			devicesService.controlLockDown(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
















