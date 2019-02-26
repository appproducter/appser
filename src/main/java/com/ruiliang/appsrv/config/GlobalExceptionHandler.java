package com.ruiliang.appsrv.config;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	 
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseData  defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("", e);
        Map<String,String[]> map = req.getParameterMap();
        Set<Entry<String, String[]>> set =  map.entrySet();
        for (Entry<String, String[]> entry : set) {
			logger.info("[[["+entry.getKey()+"]]]"+"[[["+entry.getValue().toString()+"]]]");
		}
        ResponseData re = new ResponseData();
        re.setMessage(e.getMessage());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
             re.setCode(404);
        } else {
        	re.setCode(500);
        }
        return re;
    }

}
