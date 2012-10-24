package com.alexnevsky.hotel.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Encoding filter sets right encoding for the request and response.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class EncodingFilter implements Filter {

	private String acceptEncoding;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.acceptEncoding = filterConfig.getInitParameter("acceptEncoding");
		if (this.acceptEncoding == null) {
			this.acceptEncoding = "UTF-8";
		}
	}

	/**
	 * Changes encodings of request and response.
	 * 
	 * @param request
	 *            ServletRequest
	 * @param response
	 *            ServletResponse
	 * @param chain
	 *            FilterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		request.setCharacterEncoding(this.acceptEncoding);
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
