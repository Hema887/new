package com.community.api.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonFilter implements Filter {
	Logger LOGGER = LoggerFactory.getLogger(CommonFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		LOGGER.info("[CommonFilter] - Inside doFilterMethod");
		LOGGER.info("Local Port :" + request.getLocalPort());
		LOGGER.info("Server Name : " + request.getServerName());
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		LOGGER.info("Method Name :" + httpServletRequest.getMethod());
		LOGGER.info("Request URI : " + httpServletRequest.getRequestURI());
		LOGGER.info("Servlet Path : " + httpServletRequest.getServletPath());
		if (httpServletRequest.getRequestURI().contentEquals("/api/v1/jtcustomer9090")) {
			LOGGER.info("URL Verified");
			req = new HttpServletRequestWrapper((HttpServletRequest) request) {
				@Override
				public String getRequestURI() {
					return "/api/v1/access/response";
				}
			};
			req.setAttribute("statusCode", "403");
			req.setAttribute("message", "You are not allowed to access");
		}

		chain.doFilter(req, response);
	}
}
