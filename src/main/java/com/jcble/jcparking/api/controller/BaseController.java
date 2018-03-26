package com.jcble.jcparking.api.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jcble.jcparking.common.dto.response.ResponseDto;
import com.jcble.jcparking.common.exception.ParkingServiceException;

/**
 * 所有控制器的基类：存放所有公共方法
 * 
 * @author Jingdong Wang
 * 
 */
public abstract class BaseController {
    /** logger */
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 记录响应日志
     * 
     * @param request
     * @param resp
     */
    protected void noteRespResult(HttpServletRequest request, ResponseDto resp) {
        logger.info(request.getRequestURI() + " resp====>" + resp.toString());
    }

    /**
     * 全局异常处理
     * 
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public ResponseDto handleException(HttpServletRequest request, Exception ex) {
    	// TODO 打印异常信息
        ex.printStackTrace();
    	ResponseDto resp = new ResponseDto();
        ParkingServiceException parkingServiceException = null;
        if (ex instanceof ParkingServiceException) {
            parkingServiceException = (ParkingServiceException) ex;
        } else if(ex instanceof MissingServletRequestParameterException || ex instanceof TypeMismatchException ||ex instanceof HttpMessageNotReadableException) {
            parkingServiceException = new ParkingServiceException(ParkingServiceException.ERROR_10001);
        } else {
        	 parkingServiceException = new ParkingServiceException(ParkingServiceException.ERROR_00001);
        }
        resp.setMessage(parkingServiceException.getErroDesc());
        resp.setError(true);

        JSONObject json = new JSONObject();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String key = enu.nextElement();
            Object value = request.getParameter(key);
            json.put(key, value);
        }
        logger.error("HandleException===>" + request.getRequestURI() + " request param:" + json.toJSONString());
        logger.error("HandleException===>" + request.getRequestURI() + " response error:" + resp.toString());
        return resp;
    }
}
