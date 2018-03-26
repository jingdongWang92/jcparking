package com.jcble.jcparking.api.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcble.jcparking.api.controller.BaseController;
import com.jcble.jcparking.common.dto.response.DropDownDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.service.common.CommonService;

@Controller
public class DropdownController extends BaseController{
	
	@Autowired
	private CommonService commonService;

	
	/**
	 * 获取单个停车场信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/common/dropdown/{dropdownName}",method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public ResponseDto getParkinglot(@PathVariable String dropdownName) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			List<DropDownDto> dropdownList = commonService.getDropdownByName(dropdownName);
			resp.setPayload(dropdownList);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
