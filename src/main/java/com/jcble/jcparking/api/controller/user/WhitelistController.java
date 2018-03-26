package com.jcble.jcparking.api.controller.user;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.CommonEnum;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.model.user.Whitelist;
import com.jcble.jcparking.common.service.user.WhitelistService;

/**
 * 固定车功能功能模块
 * 
 * @author Jingdong Wang
 * @date 2017年3月17日 上午11:09:20
 *
 */
@Controller
public class WhitelistController extends BaseController {

	@Autowired
	private WhitelistService whitelistService;

	/**
	 * 5.18 获取车辆包月信息
	 * 
	 * @param carNumber 车牌号码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/car/{carNumber}/whitelists", method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public ResponseDto getwhitelistByCar(@PathVariable String carNumber)
			throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if(StringUtils.isBlank(carNumber)) {
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			Whitelist dto = new Whitelist();
			dto.setCarNumber(carNumber);
			dto.setStatus(CommonEnum.WhitelistStatus.UnExpired.code);
			List<Whitelist> whitelists = whitelistService.getWhitelistsByCar(dto);
			resp.setPayload(whitelists);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
