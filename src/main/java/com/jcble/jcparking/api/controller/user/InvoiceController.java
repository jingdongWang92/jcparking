package com.jcble.jcparking.api.controller.user;

import java.util.List;

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
import com.jcble.jcparking.common.dto.request.InvoiceApplyReqDto;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;
import com.jcble.jcparking.common.model.user.Invoice;
import com.jcble.jcparking.common.service.user.InvoiceService;

import baseproj.common.mybatis.page.PageParameter;

/**
 * 用户发票
 * 
 * @author Jingdong Wang
 * @date 2017年3月14日 下午6:04:03
 *
 */
@Controller
public class InvoiceController extends BaseController {

	@Autowired
	private InvoiceService InvoiceService;

	/**
	 * 5.21 申请发票
	 * @param reqDto
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/invoice/application", method = RequestMethod.POST)
	@ResponseBody
	public ResponseDto applyInvoice(@RequestBody InvoiceApplyReqDto reqDto) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			if (reqDto == null || StringUtils.isBlank(reqDto.getUserId()) || StringUtils.isBlank(reqDto.getRecevier())
					|| StringUtils.isBlank(reqDto.getInvoiceHead()) || StringUtils.isBlank(reqDto.getLinkPhone())
					|| StringUtils.isBlank(reqDto.getAddress()) || reqDto.getOrder() == null){
				throw new ParkingServiceException(ParkingServiceException.ERROR_10001);
			}
			InvoiceService.applyInvoice(reqDto);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 5.22 用户获取个人发票记录
	 * @param pageIndex
	 * @param pageSize
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/invoices/{userId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getParkings(@RequestParam(value = "page", required = false) Integer pageIndex,
			@RequestParam(value = "page_size", required = false) Integer pageSize, @PathVariable String userId)
			throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			Invoice queryParams = new Invoice();
			if (pageIndex == null) {
				pageIndex = CommonConstants.PAGE_INDEX;
			}
			if (pageSize == null) {
				pageSize = CommonConstants.PAGE_SIZE;
			}
			queryParams.setPage(new PageParameter(pageIndex, pageSize));
			queryParams.setUserId(userId);
			List<Invoice> invoices = InvoiceService.queryUserInvoicesByPage(queryParams);
			resp.setPayload(invoices);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 5.23 发票详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	@RequestMapping(value = "/invoice/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getParkinglot(@PathVariable Integer id) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			Invoice invoice = InvoiceService.getInvoiceDetail(id);
			resp.setPayload(invoice);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
