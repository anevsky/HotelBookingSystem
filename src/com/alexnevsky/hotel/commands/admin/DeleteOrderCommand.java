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
 * This admin's command deletes the order with given id.
 * 
 * @version 1.0 22.05.2011
 * @author Alex Nevsky
 */
public class DeleteOrderCommand implements ICommand {

	static {
		logger = Logger.getLogger(DeleteOrderCommand.class);
	}
	private static Logger logger;

	/**
	 * Deletes order's and redirects admin to the main page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		Long orderId = Long.valueOf(request.getParameter(AttributesManager.PARAM_NAME_ORDER_ID));

		logger.info("Admin '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. Execute "
				+ this.toString() + ". RemoteAddr: " + request.getRemoteAddr());

		try {
			AbstractDAOFactory daoFactory = Controller.getDAOFactory();

			OrderDAO orderDAO = daoFactory.getOrderDAO();
			FormDAO formDAO = daoFactory.getFormDAO();

			Order order = null;
			order = orderDAO.find(orderId);

			Form form = null;
			form = formDAO.find(order.getFormId());

			logger.info("Admin '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN)
					+ "'. Delete '" + order + "'" + " and '" + form + "'" + ". RemoteAddr: " + request.getRemoteAddr());

			orderDAO.delete(order);
			formDAO.delete(form);

			request.setAttribute(AttributesManager.ATTRIBUTE_RESULT, MessageManager.RESULT_DELETE_ORDER_MESSAGE);

		} catch (DAOException ex) {
			logger.error(ex, ex);
			request.setAttribute(AttributesManager.ATTRIBUTE_ERROR_MESSAGE, MessageManager.DAO_EXCEPTION_ERROR_MESSAGE);
			page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
			return page;
		}

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);

		return page;
	}

	@Override
	public String toString() {
		return "DeleteOrderCommand{" + '}';
	}
}