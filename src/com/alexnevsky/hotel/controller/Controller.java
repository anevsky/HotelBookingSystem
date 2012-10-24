package com.alexnevsky.hotel.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.dao.AbstractDAOFactory;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.webapp.Config;

/**
 * Controller class.
 * The Controller process the user requests. Implements Service to Worker
 * (Front Controller + View Helper) pattern with Servlet Front,
 * Command and Controller, Dispatcher in Controller, JSP View and JavaBean
 * Helper strategies. Also Controller part of MVC model.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String LOG4J_CONFIG = "log4jConfigLocation";
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static Logger logger;
	/**
	 * Data Access Object Factory to use by all classes of the system.
	 */
	private static AbstractDAOFactory daoFactory;

	public Controller() {
		super();
	}

	@Override
	public void init() throws ServletException {
		super.init();
		try {
			String pathPrefix = this.getServletContext().getRealPath("/");
			String log4jConfigFile = this.getServletContext().getInitParameter(LOG4J_CONFIG);
			DOMConfigurator.configure(pathPrefix + log4jConfigFile);
			logger = Logger.getLogger(this.getClass());
			daoFactory = Config.getInstance(this.getServletContext()).getDAOFactory();
			logger.info("Servlet successfully initialized");
		} catch (Exception e) {
			// Critical error encountered. One of the resources can not be
			// loaded. So we can not guarantee correct working.
			if (logger != null) {
				logger.fatal(e, e);
			}
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(CONTENT_TYPE);
		this.processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType(CONTENT_TYPE);
		this.processRequest(request, response);
	}

	/**
	 * Processes user request by calling necessary actions
	 * and dispatching user to the view.
	 * 
	 * @param request
	 *            User request.
	 * @param response
	 *            Response for the user.
	 */
	private void processRequest(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		try {
			ICommand command = RequestHelper.getInstance().getCommand(request);
			page = command.execute(request, response);
		} finally {
			logger.info("User '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. "
					+ "Dispatching to view: " + page + ". RemoteAddr: " + request.getRemoteAddr());

			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher(page);
			try {
				dispatcher.forward(request, response);
			} catch (ServletException ex) {
				logger.error(ex, ex);
			} catch (IOException ex) {
				logger.error(ex, ex);
			}
		}
	}

	/**
	 * Returns the DAO Factory associated with this Controller.
	 * 
	 * @return The DAO Factory associated with this Controller
	 */
	public static AbstractDAOFactory getDAOFactory() {
		return daoFactory;
	}
}
