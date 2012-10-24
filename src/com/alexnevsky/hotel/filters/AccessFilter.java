package com.alexnevsky.hotel.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.manager.ConfigurationManager;

/**
 * Access filter blocks illegal access to specified resources. Secured area is
 * specified by regex in filter config in web deployment descriptor.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class AccessFilter implements Filter {

	private static final String SECURED_AREA = "securedArea";
	private static final String DEFAULT_PATTERN = "(.)*/jsp/(.)*";
	private static Logger logger;

	static {
		logger = Logger.getLogger(AccessFilter.class);
	}
	private String pattern;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.pattern = fConfig.getInitParameter(SECURED_AREA);
		if (this.pattern == null) {
			this.pattern = DEFAULT_PATTERN;
		}
	}

	/**
	 * Filters illegal access to resources. If the user tries to access secured
	 * page, his session becomes invalid and he is redirected to the login page,
	 * where provided to re-login in the resources.
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
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession();

		String URI = httpServletRequest.getRequestURI();
		if (URI.matches(this.pattern)) {
			logger.warn("Access in secured area denied for remore addr '" + request.getRemoteAddr() + "'");
			if (session != null) {
				session.invalidate();
			}
			httpServletResponse.sendRedirect(httpServletResponse.encodeRedirectURL(httpServletRequest.getContextPath()
					+ ConfigurationManager.getInstance().getProperty(ConfigurationManager.CONTROLLER)));
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
	}
}
