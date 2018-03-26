package com.jcble.jcparking.api.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.CommonConstants;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.model.admin.ParkingLotCar;
import com.jcble.jcparking.common.service.admin.ParkingLotCarService;
import com.jcble.jcparking.common.service.admin.ParkingOrderService;

import baseproj.common.mybatis.page.PageParameter;

/**
 * 停车场车辆相关控制器
 * 
 * @author Jingdong Wang
 * @date   2017-02-24
 *
 */
@Controller
public class ParkingLotCarController extends BaseController {

	@Autowired
	private ParkingLotCarService parkingLotCarService;
	
	@Autowired
	private ParkingOrderService orderService;

	
	/**
	 * 获取场内车辆信息 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/parkinglot/cars", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getParkings(@RequestParam(value = "page", required = false) Integer pageIndex,@RequestParam(value = "page_size", required = false) Integer pageSize,
			@RequestParam(value = "operatorId") Integer operatorId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			ParkingLotCar queryParams = new ParkingLotCar();
			if(pageIndex == null) {
				pageIndex = CommonConstants.PAGE_INDEX;
			}
			if(pageSize == null) {
				pageSize = CommonConstants.PAGE_SIZE;
			}
			queryParams.setPage(new PageParameter(pageIndex, pageSize));
			queryParams.setOperatorId(operatorId);
			List<ParkingLotCar> car = parkingLotCarService.queryParkingLotCar(queryParams);
			resp.setPayload(car);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 车辆离场
	 * @throws Exception 
	 */
	@RequestMapping(value = "/parkinglot/car/{parkingId}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public ResponseDto carOut(@PathVariable Integer parkingId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			orderService.carOut(parkingId);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
}
