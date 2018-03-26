package com.jcble.jcparking.api.scheduler;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jcble.jcparking.common.CommonEnum;
import com.jcble.jcparking.common.dao.admin.ParkingDao;
import com.jcble.jcparking.common.dao.admin.ParkingOrderDao;
import com.jcble.jcparking.common.dao.user.ReservationDao;
import com.jcble.jcparking.common.dao.user.UserCarDao;
import com.jcble.jcparking.common.model.admin.ParkingOrder;
import com.jcble.jcparking.common.model.user.Reservation;
import com.jcble.jcparking.common.model.user.UserCar;
import com.jcble.jcparking.common.service.admin.ParkingOrderService;
import com.jcble.jcparking.common.utils.DateUtils;
import com.jcble.jcparking.common.utils.MathUtil;

/**
 * 检查预约超时、离场超时定时器
 * 
 * @author Jingdong Wang
 * @date 2017年3月8日 下午4:32:42
 *
 */
@Component
public class CheckParkingScheduler {
	private static final Logger logger = LoggerFactory.getLogger(CheckParkingScheduler.class);

	@Autowired
	private ParkingOrderDao orderDao;
	@Autowired
	private ParkingDao parkingDao;
	@Autowired
	private ReservationDao reservationDao;
	@Autowired
	private ParkingOrderService orderServic;
	@Autowired
	private UserCarDao carDao;

	public CheckParkingScheduler() {

	}

	public void execute() {
		logger.info("<<<==============================check parking scheduler start.");
		try {
			this.checkParkingTimeout();
			this.checkAppointTimeout();
			this.checkAppointUnPay();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info(">>>==============================check parking scheduler end.");

	}

	/**
	 * 检查预约超时
	 * @throws Exception 
	 */
	private void checkAppointTimeout() throws Exception {
		//获取所有状态为预约成功的订单
		Reservation dto = new Reservation();
		dto.setStatus(CommonEnum.RevatationStatus.Success.code);
		List<Reservation> list = reservationDao.getReservations(dto);
		if(list != null && list.size() > 0) {
			for (Reservation reservation : list) {
				String expireTime = reservation.getExpireTime();
				Date expireDate = DateUtils.strToDate(expireTime);
				long timeSurplus = DateUtils.dateMinDiff(new Date(),expireDate);
				//已过期  设置预约订单为"已过期",释放车位、修改用户车辆状态
				if(timeSurplus < 0) {
					reservation.setStatus(CommonEnum.RevatationStatus.OverTime.code);
					reservationDao.update(reservation);
					parkingDao.updateParkingFree(reservation.getParkingId());
					
					UserCar carDto = new UserCar();
					carDto.setUserId(reservation.getUserId());
					carDto.setCarNumber(reservation.getCarNumber());
					UserCar car = carDao.getCarByUserIdAndCarNumber(carDto);
					if(car != null) {
						car.setStatus(CommonEnum.CarStatus.Out.code);
						carDao.update(car);
					}
				}
			}
		}
	}
	
	/**
	 * 检查未支付预约订单
	 * @throws Exception 
	 */
	private void checkAppointUnPay() throws Exception {
		//获取所有状态为已预约的订单
		Reservation dto = new Reservation();
		dto.setStatus(CommonEnum.RevatationStatus.Revatationed.code);
		dto.setPayStatus(CommonEnum.PayStatus.UnPay.code);
		List<Reservation> list = reservationDao.getReservations(dto);
		if(list != null && list.size() > 0) {
			for (Reservation reservation : list) {
				String startTime = reservation.getStartTime();
				Date startDate = DateUtils.strToDate(startTime);
				long timeSurplus = DateUtils.dateMinDiff(startDate,new Date());
				//已过期  设置预约订单为"已过期"
				if(timeSurplus >= 5) {
					reservation.setStatus(CommonEnum.RevatationStatus.OverTime.code);
					reservationDao.update(reservation);
					parkingDao.updateParkingFree(reservation.getParkingId());
					//修改用户车牌状态
					UserCar carDto = new UserCar();
					carDto.setUserId(reservation.getUserId());
					carDto.setCarNumber(reservation.getCarNumber());
					UserCar car = carDao.getCarByUserIdAndCarNumber(carDto);
					if(car != null) {
						car.setStatus(CommonEnum.CarStatus.Out.code);
						carDao.update(car);
					}
				}
			}
		}
	}

	/**
	 * 检查离场超时
	 * 
	 * @throws Exception
	 */
	private void checkParkingTimeout() throws Exception {
		try {
			// 获取所有未结束且已支付的订单信息,免费订单除外
			ParkingOrder dto = new ParkingOrder();
			dto.setPayStatus(CommonEnum.PayStatus.Payed.code);
			dto.setOrderStatus(CommonEnum.OrderStatus.Ing.code);
			dto.setIsFree(CommonEnum.IsFreeOrder.Yes.code);
			List<ParkingOrder> payedOrders = orderDao.queryPayedOrders(dto);
			if (payedOrders == null || payedOrders.size() < 1) {
				return;
			}
			for (ParkingOrder order : payedOrders) {
				// 超时 更新订单支付状态
				if (orderServic.isOverTime(order)) {
					BigDecimal totalFee = orderServic.parkingOrderSettlement(order);
					//总费用 = 停车总费用 + 预约费用
					Reservation reserveInfo = reservationDao.getReservationByOrderNo(order.getOrderNo());
					if(reserveInfo != null) {
						totalFee = MathUtil.add(totalFee, reserveInfo.getPayedFee());
					}
					order.setPayStatus(CommonEnum.PayStatus.UnPay.code);
					order.setTotalFee(totalFee);
					orderDao.update(order);
					//修改用户车牌状态
					UserCar carDto = new UserCar();
					carDto.setUserId(order.getUserId());
					carDto.setCarNumber(order.getCarNumber());
					UserCar car = carDao.getCarByUserIdAndCarNumber(carDto);
					if(car != null) {
						car.setStatus(CommonEnum.CarStatus.In.code);
						carDao.update(car);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
