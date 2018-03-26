package com.jcble.jcparking.api.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.CommonConstants;
import com.jcble.jcparking.common.dto.request.ReservationReqDto;
import com.jcble.jcparking.common.dto.response.ReserveRespDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.model.user.Reservation;
import com.jcble.jcparking.common.service.user.ReservationService;
import com.jcble.jcparking.common.utils.HttpHelper;

import baseproj.common.mybatis.page.PageParameter;

/**
 * 预约订单模块
 * 
 * @author Jingdong Wang
 * @date 2017年3月14日 上午10:22:28
 *
 */
@Controller
public class ReservationController extends BaseController {

	@Autowired
	private ReservationService service;

	/**
	 * 5.13 预约车位
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reservation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto reservation(HttpServletRequest request,@RequestBody ReservationReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getCarNumber()) || StringUtils.isBlank(reqDto.getUserId())
					|| reqDto.getParkinglotId() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			// 获取客户端ip
			String clientIpAddr = HttpHelper.getIpAddr(request);
			reqDto.setIpAddr(clientIpAddr);
			ReserveRespDto reservaInfo = service.reservationParking(reqDto);
			resp.setPayload(reservaInfo);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 5.14 取消预约
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reservation/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseDto cancelReservation(@PathVariable Integer id) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			service.cancelReservation(id);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 5.15 获取个人预约订单记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reservations/{userId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getReservations(@PathVariable String userId,
			@RequestParam(value = "page", required = false) Integer pageIndex,
			@RequestParam(value = "page_size", required = false) Integer pageSize) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			Reservation queryParams = new Reservation();
			if (pageIndex == null) {
				pageIndex = CommonConstants.PAGE_INDEX;
			}
			if (pageSize == null) {
				pageSize = CommonConstants.PAGE_SIZE;
			}
			queryParams.setPage(new PageParameter(pageIndex, pageSize));
			queryParams.setUserId(userId);
			List<Reservation> data = service.getUserReservations(queryParams);
			resp.setPayload(data);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 5.16 获取车辆进行中的预约记录信息
	 * @param carId
	 * @param coordinateX
	 * @param coordinateY
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/{carId}/reservation", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getReservationByCar(@PathVariable Integer carId,
			@RequestParam(value = "coordinateX") double coordinateX,@RequestParam(value = "coordinateY") double coordinateY) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			Reservation data = service.getReservationByCar(carId,coordinateX, coordinateY);
			resp.setPayload(data);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.31 取消预约后申请退款
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/reservation/{reservationId}/refund", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto refund(@PathVariable Integer reservationId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			//reservationId 预约订单唯一标识
			service.refund(reservationId);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
