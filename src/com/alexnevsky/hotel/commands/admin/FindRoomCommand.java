package com.alexnevsky.hotel.commands.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.controller.Controller;
import com.alexnevsky.hotel.dao.AbstractDAOFactory;
import com.alexnevsky.hotel.dao.FormDAO;
import com.alexnevsky.hotel.dao.OrderDAO;
import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.ConfigurationManager;
import com.alexnevsky.hotel.manager.MessageManager;
import com.alexnevsky.hotel.model.Form;
import com.alexnevsky.hotel.model.Order;

/**
 * This admin's command finds free rooms for customer's order.
 * 
 * @version 1.0 22.05.2011
 * @author Alex Nevsky
 */
public class FindRoomCommand implements ICommand {

	static {
		logger = Logger.getLogger(FindRoomCommand.class);
	}
	private static Logger logger;

	/**
	 * Finds free rooms for customer's order
	 * and redirects admin to the select room page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		Long orderId = Long.valueOf(request.getParameter(AttributesManager.PARAM_NAME_ORDER_ID));

		logger.info("Admin '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. Execute "
				+ this.toString() + ". RemoteAddr: " + request.getRemoteAddr());

		Boolean viewFreeRoomList = true;
		request.setAttribute(AttributesManager.ATTRIBUTE_VIEW_FREE_ROOMS_LIST, viewFreeRoomList);

		try {
			AbstractDAOFactory daoFactory = Controller.getDAOFactory();

			OrderDAO orderDAO = daoFactory.getOrderDAO();
			FormDAO formDAO = daoFactory.getFormDAO();

			Order order = null;
			order = orderDAO.find(orderId);

			Form form = null;
			form = formDAO.find(order.getFormId());

			request.getSession().setAttribute(AttributesManager.PARAM_NAME_ORDER_ID, order.getId());
			request.getSession().setAttribute(AttributesManager.PARAM_NAME_FORM_ID, form.getId());

		} catch (DAOException ex) {
			logger.error(ex, ex);
			request.setAttribute(AttributesManager.ATTRIBUTE_ERROR_MESSAGE, MessageManager.DAO_EXCEPTION_ERROR_MESSAGE);
			page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
			return page;
		}

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ROOMS_PAGE_PATH);

		return page;
	}

	@Override
	public String toString() {
		return "FindRoomCommand{" + '}';
	}
}
