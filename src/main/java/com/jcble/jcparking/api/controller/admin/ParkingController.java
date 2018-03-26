package com.jcble.jcparking.api.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.CommonConstants;
import com.jcble.jcparking.common.CommonEnum;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.model.admin.Parking;
import com.jcble.jcparking.common.service.admin.ParkingService;

import baseproj.common.mybatis.page.PageParameter;

/**
 * 车位相关控制器
 * 
 * @author Jingdong Wang
 *
 */
@Controller
public class ParkingController extends BaseController {

	@Autowired
	private ParkingService parkingService;

	
	/**
	 * 获取车位信息 
	 * @param pageIndex 
	 * @param pageSize 
	 * @param parkinglotId
	 * @param lockStatus
	 * @param operatorId
	 * @param devBindStatus
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/parkings", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getParkings(@RequestParam(value = "page", required = false) Integer pageIndex,@RequestParam(value = "page_size", required = false) Integer pageSize,
			@RequestParam(value = "parkinglotId", required = false) Integer parkinglotId,@RequestParam(value = "lockStatus", required = false) String lockStatus,
			@RequestParam(value = "operatorId") Integer operatorId,@RequestParam(value = "devBindStatus", required = false) String devBindStatus,
			@RequestParam(value = "areaId", required = false) Integer areaId,@RequestParam(value = "parkingNo", required = false) String parkingNo) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			Parking dto = new Parking();
			if(pageIndex == null) {
				pageIndex = CommonConstants.PAGE_INDEX;
			}
			if(pageSize == null) {
				pageSize = CommonConstants.PAGE_SIZE;
			}
			dto.setPage(new PageParameter(pageIndex, pageSize));
			dto.setParkingNo(parkingNo);
			dto.setLockStatus(lockStatus);
			dto.setParkinglotId(parkinglotId);
			dto.setDevBindStatus(devBindStatus);
			dto.setOperatorId(operatorId);
			dto.setAreaId(areaId);
			List<Parking> parkings = parkingService.queryParkings(dto);
			resp.setPayload(parkings);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 获取停车场未使用车位
	 * 
	 * @param parkinglotId 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/parkings/free", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getParkings(@RequestParam(value = "parkinglotId") Integer parkinglotId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			Parking dto = new Parking();
			dto.setParkinglotId(parkinglotId);
			dto.setStatus(CommonEnum.ParkingStatus.Free.code);
			List<Parking> parkings = parkingService.queryUnusedParkings(dto);
			resp.setPayload(parkings);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
}
