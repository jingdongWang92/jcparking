package com.jcble.jcparking.api.controller.user;

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
import com.jcble.jcparking.common.model.user.Message;
import com.jcble.jcparking.common.service.user.MessageService;

import baseproj.common.mybatis.page.PageParameter;

/**
 * 用户获取后台推送消息
 * 
 * @author Jingdong Wang
 * @date 2017年3月21日 上午10:26:42
 *
 */
@Controller
public class MessageController extends BaseController {

	@Autowired
	private MessageService messageService;

	@RequestMapping(value = "/messages/{userId}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseDto getUserMessages(@PathVariable String userId,
			@RequestParam(value = "page", required = false) Integer pageIndex,
			@RequestParam(value = "page_size", required = false) Integer pageSize) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			Message queryParams = new Message();
			queryParams.setUserId(userId);
			if (pageIndex == null) {
				pageIndex = CommonConstants.PAGE_INDEX;
			}
			if (pageSize == null) {
				pageSize = CommonConstants.PAGE_SIZE;
			}
			queryParams.setPage(new PageParameter(pageIndex, pageSize));
			List<Message> messages = messageService.getUserMessages(queryParams);
			resp.setPayload(messages);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 修改用户消息
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/message/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseDto updateUserMessage(@PathVariable Integer id) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			messageService.updateUserMessage(id);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

	/**
	 * 用户消息详情
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseDto getUserMessages(@PathVariable Integer id) throws Exception {
		ResponseDto resp = new ResponseDto();
		try {
			Message message = messageService.getUserMessageDetail(id);
			resp.setPayload(message);
		} catch (Exception e) {
			throw e;
		}
		return resp;
	}

}
