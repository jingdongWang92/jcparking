package com.jcble.jcparking.api.controller.admin;

import java.util.List;

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
import com.jcble.jcparking.common.CommonEnum;
import com.jcble.jcparking.common.dto.request.OrderReqDto;
import com.jcble.jcparking.common.dto.response.ChargeStatisticsRespDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.model.admin.ParkingOrder;
import com.jcble.jcparking.common.service.admin.ParkingOrderService;

import baseproj.common.mybatis.page.PageParameter;

/**
 * 停车场订单相关控制器
 * 
 * @author Jingdong Wang
 *
 */
@Controller
public class ParkingOrderController extends BaseController {

	@Autowired
	private ParkingOrderService orderService;
	
	
	/**
	 * 4.4 收费记录/历史未收费订单
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/orders",method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ResponseDto getOrders(@RequestParam(value = "page", required = false) Integer pageIndex,@RequestParam(value = "page_size", required = false) Integer pageSize
			,@RequestParam(value = "operatorId") Integer operatorId,@RequestParam(value = "payWay", required = false) String payWay
			,@RequestParam(value = "startTime", required = false) String startTime,@RequestParam(value = "endTime", required = false) String endTime
			,@RequestParam(value = "payStatus", required = false) String payStatus,@RequestParam(value = "carNumber", required = false) String carNumber) throws Exception {
		ResponseDto resp = new ResponseDto();
		ParkingOrder queryParams = new ParkingOrder();
		try {
			if(pageIndex == null) {
				pageIndex = CommonConstants.PAGE_INDEX;
			}
			if(pageSize == null) {
				pageSize = CommonConstants.PAGE_SIZE;
			}
			queryParams.setPage(new PageParameter(pageIndex, pageSize));
			queryParams.setOperatorId(operatorId);
			queryParams.setInTime(startTime);
			queryParams.setOutTime(endTime);
			queryParams.setPayStatus(payStatus);
			queryParams.setPayWay(payWay);
			queryParams.setCarNumber(carNumber);
			queryParams.setOrderStatus(CommonEnum.OrderStatus.Ing.code);
			List<ParkingOrder> list = orderService.queryByPage(queryParams);
			resp.setPayload(list);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	
	/**
	 * 停车订单录入
	 * @throws Exception 
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseDto createOrder(@RequestBody OrderReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || reqDto.getParkingId() == null || reqDto.getOperatorId() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			orderService.createParkingOrder(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 4.6 获取订单详情(收费端)
	 * @throws Exception 
	 */
	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getOrderById(@PathVariable Integer orderId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (orderId == null ) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			ParkingOrder order = orderService.getOrderById(orderId);
			resp.setPayload(order);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 获取订单详情(用户端)
	 * @throws Exception 
	 */
	@RequestMapping(value = "/user/order/{orderId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getUserOrderById(@PathVariable Integer orderId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (orderId == null ) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			ParkingOrder order = orderService.getUserOrderById(orderId);
			resp.setPayload(order);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 修改订单信息
	 * @throws Exception 
	 */
	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ResponseDto getOrderById(@PathVariable Integer orderId,@RequestBody OrderReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || reqDto.getParkinglotCarId() == null || reqDto.getParkingId() == null
					|| reqDto.getOperatorId() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			reqDto.setOrderId(orderId);
			orderService.updateOrder(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	
	/**
	 * 停车收费
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/charge", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ResponseDto orderSettlement(@RequestBody OrderReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || reqDto.getOrderId() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			ParkingOrder order = orderService.orderSettlement(reqDto.getOrderId());
			resp.setPayload(order);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 确认收费
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/charge_confirm", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ResponseDto chargeConfirm(@RequestBody OrderReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || reqDto.getOperatorId() == null || reqDto.getOrderId() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			orderService.chargeConfirm(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 免单设置
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/free", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ResponseDto setOrderFree(@RequestBody OrderReqDto dto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (dto == null || dto.getOperatorId() == null || dto.getOrderId() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			orderService.setOrderFree(dto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 收费员账单统计
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/statistics", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto orderStatistics(@RequestParam(value = "operatorId") Integer operatorId
			,@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			ParkingOrder reqDto = new ParkingOrder();
			reqDto.setInTime(startTime);
			reqDto.setOutTime(endTime);
			reqDto.setOperatorId(operatorId);
			ChargeStatisticsRespDto data = orderService.orderStatistics(reqDto);
			resp.setPayload(data);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.13 获取个人停车订单记录
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/user/{userId}/orders",method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ResponseDto getUserOrders(@PathVariable String userId,@RequestParam(value = "page", required = false) Integer pageIndex,
			@RequestParam(value = "page_size", required = false) Integer pageSize) throws Exception {
		ResponseDto resp = new ResponseDto();
		ParkingOrder dto = new ParkingOrder();
		try {
			if(pageIndex == null) {
				pageIndex = CommonConstants.PAGE_INDEX;
			}
			if(pageSize == null) {
				pageSize = CommonConstants.PAGE_SIZE;
			}
			dto.setPage(new PageParameter(pageIndex, pageSize));
			dto.setOrderStatus(CommonEnum.OrderStatus.Ing.code);
			dto.setUserId(userId);
			List<ParkingOrder> list = orderService.queryUserOrdersByPage(dto);
			resp.setPayload(list);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 5.17 获取车辆进行中的停车订单信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/car/{carId}/order",method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ResponseDto getUserOrder(@PathVariable Integer carId) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			ParkingOrder order = orderService.getOrderByCar(carId);
			resp.setPayload(order);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
}
