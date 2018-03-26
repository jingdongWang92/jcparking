package com.jcble.jcparking.api.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.model.admin.ParkingArea;
import com.jcble.jcparking.common.service.admin.ParkingAreaService;

/**
 * 停车场区域相关控制器
 * @author Jingdong Wang
 *
 */
@Controller
public class ParkingAreaController {
	
	@Autowired
	private ParkingAreaService parkingAreaService;
	
	/**
	 * 获取停车场区域
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/parkinglot/{parkinglotId}/area",method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ResponseDto getParkings(HttpServletRequest request,@PathVariable Integer parkinglotId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(parkinglotId == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			List<ParkingArea> parkingLots = parkingAreaService.queryParkingAreas(parkinglotId);
			resp.setPayload(parkingLots);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
}
