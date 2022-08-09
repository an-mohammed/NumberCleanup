package com.ooredoo.nc.gui.managedbean;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class Error implements ErrorController {
	
	private static final String ERROR_PATH = "/error";
	
	@Autowired
    private ErrorAttributes errorAttributes;
	private static final Logger LOGGER = LoggerFactory.getLogger(Error.class);

	@RequestMapping(ERROR_PATH)
    public String handleError(HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
		LOGGER.error("Error Attributes :" + getErrorAttributes(request, true, webRequest));
		request.getSession().invalidate();
        return "/error.xhtml";
    }
	
	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace, WebRequest webRequest) {
        return errorAttributes.getErrorAttributes(webRequest, includeStackTrace);
    }
}
