package com.alexnevsky.hotel.commands.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.controller.Controller;
import com.alexnevsky.hotel.dao.AbstractDAOFactory;
import com.alexnevsky.hotel.dao.OrderDAO;
import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.ConfigurationManager;
import com.alexnevsky.hotel.manager.MessageManager;
import com.alexnevsky.hotel.model.Customer;
import com.alexnevsky.hotel.model.Order;

/**
 * This customer's command shows customer's order bill.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public class ShowMyBillCommand implements ICommand {

	static {
		logger = Logger.getLogger(ShowMyBillCommand.class);
	}
	private static Logger logger;

	/**
	 * Checks customer's id, sets boolean condition for custom tag
	 * and redirects customer to the view bill page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		Long orderId = Long.valueOf(request.getParameter(AttributesManager.PARAM_NAME_ORDER_ID));

		logger.info("Customer '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN)
				+ "'. Execute " + this.toString() + ". RemoteAddr: " + request.getRemoteAddr());

		try {
			AbstractDAOFactory daoFactory = Controller.getDAOFactory();

			OrderDAO orderDAO = daoFactory.getOrderDAO();
			Customer customer = (Customer) request.getSession().getAttribute(AttributesManager.ATTRIBUTE_CUSTOMER);

			Order order = null;
			order = orderDAO.find(orderId);

			if (customer.getId().compareTo(order.getCustomerId()) == 0) {
				Boolean viewBillsList = true;
				request.setAttribute(AttributesManager.ATTRIBUTE_VIEW_BILLS_LIST, viewBillsList);
				request.setAttribute(AttributesManager.PARAM_NAME_ORDER_ID, orderId);
			} else {
				logger.warn("Customer '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN)
						+ "'. Trying to view not own order bill '" + orderId + "'" + ". RemoteAddr: "
						+ request.getRemoteAddr());
				request.setAttribute(AttributesManager.ATTRIBUTE_ERROR_MESSAGE,
						MessageManager.PERMISSION_EXCEPTION_ERROR_MESSAGE);
				page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
				return page;
			}
		} catch (DAOException ex) {
			logger.error(ex, ex);
			request.setAttribute(AttributesManager.ATTRIBUTE_ERROR_MESSAGE, MessageManager.DAO_EXCEPTION_ERROR_MESSAGE);
			page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
			return page;
		}

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.BILLS_PAGE_PATH);

		return page;
	}

	@Override
	public String toString() {
		return "ShowMyBillCommand{" + '}';
	}
}