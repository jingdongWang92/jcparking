package com.jcble.jcparking.api.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.jcble.jcparking.common.model.admin.ParkingLot;
import com.jcble.jcparking.common.service.admin.ParkingLotService;

import baseproj.common.mybatis.page.PageParameter;

/**
 * 停车场相关控制器
 * @author Jingdong Wang
 *
 */
@Controller
public class ParkingLotController extends BaseController{
	
	@Autowired
	private ParkingLotService parkingLotService;
	
	/**
	 * 获取停车场信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/parkinglots",method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ResponseDto getParkings(HttpServletRequest request,@RequestParam(value = "page", required = false) Integer pageIndex,
			@RequestParam(value = "page_size", required = false) Integer pageSize,@RequestParam(value = "parkinglotName", required = false) String parkinglotName) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(pageIndex == null) {
				pageIndex = CommonConstants.PAGE_INDEX;
			}
			if(pageSize == null) {
				pageSize = CommonConstants.PAGE_SIZE;
			}
			ParkingLot queryParams = new ParkingLot();
			queryParams.setPage(new PageParameter(pageIndex, pageSize));
			queryParams.setParkinglotName(parkinglotName);
			List<ParkingLot> parkingLots = parkingLotService.queryParkingLots(queryParams);
			resp.setPayload(parkingLots);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.20 获取预约基本信息(停车场信息)
	 * @param id 停车场id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/parkinglot/{id}",method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ResponseDto getParkinglot(@PathVariable Integer id) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			ParkingLot parkingLotDto = parkingLotService.getParkinglotById(id);
			resp.setPayload(parkingLotDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.19 获取附近停车场
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/parkinglots/neighborhood",method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ResponseDto getNeighborhoodParkinglot(@RequestParam(value = "range") double range,
			@RequestParam(value = "coordinateX") double coordinateX,@RequestParam(value = "coordinateY") double coordinateY) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			List<ParkingLot> parkingLots = parkingLotService.getParkinglotByRange(range,coordinateX,coordinateY);
			resp.setPayload(parkingLots);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
}
