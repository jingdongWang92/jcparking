package com.jcble.jcparking.api.controller.devices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.CommonConstants;
import com.jcble.jcparking.common.CommonEnum;
import com.jcble.jcparking.common.dto.request.AlarmMsgReqDto;
import com.jcble.jcparking.common.dto.response.CountDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.model.devices.AlarmMsg;
import com.jcble.jcparking.common.service.devices.AlarmMsgService;

import baseproj.common.mybatis.page.PageParameter;

/**
 * 设备告警消息相关控制器
 * 
 * @author Jingdong Wang
 *
 */
@Controller
public class AlarmMsgController extends BaseController {

	@Autowired
	private AlarmMsgService alarmMsgService;

	/**
	 * 获取告警消息
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/alarm_messages", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getAlarmMessages(@RequestParam(value = "page", required = false) Integer pageIndex,@RequestParam(value = "page_size", required = false) Integer pageSize,
			@RequestParam(value = "parkinglotId", required = false) Integer parkinglotId,@RequestParam(value = "deviceType", required = false) String deviceType,
			@RequestParam(value = "snagType", required = false) String snagType,@RequestParam(value = "operatorId") Integer operatorId) throws Exception {
		ResponseDto resp = new ResponseDto();
		AlarmMsg queryParams = new AlarmMsg();
		try {
			if(pageIndex == null) {
				pageIndex = CommonConstants.PAGE_INDEX;
			}
			if(pageSize == null) {
				pageSize = CommonConstants.PAGE_SIZE;
			}
			queryParams.setPage(new PageParameter(pageIndex, pageSize));
			queryParams.setOperatorId(operatorId);
			queryParams.setParkinglotId(parkinglotId);
			queryParams.setDeviceType(deviceType);
			queryParams.setSnagType(snagType);
			List<AlarmMsg> data = alarmMsgService.getAlarmMessages(queryParams);
			resp.setPayload(data);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 处理告警消息
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/alarm_messages", method = RequestMethod.PUT, produces = "application/json")
	@ResponseBody
	public ResponseDto handAlarmMessages(@RequestBody AlarmMsgReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(reqDto == null || reqDto.getOperatorId() == null || reqDto.getAlarmMessage() == null) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			alarmMsgService.handAlarmMessages(reqDto);
			
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	
	/**
	 * 根据管理员获取未处理的告警消息总的记录条数
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/alarm_messages/count", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getCountByOperator(@RequestParam(value = "operatorId") Integer operatorId) throws Exception {
		ResponseDto resp = new ResponseDto();
		AlarmMsg reqDto = new AlarmMsg();
		try {
			reqDto.setOperatorId(operatorId);
			reqDto.setStatus(CommonEnum.AlarmHandleStatus.UnProcessed.code);
			CountDto count = alarmMsgService.getCountByOperator(reqDto);
			resp.setPayload(count);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}
	

}
















