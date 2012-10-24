package com.alexnevsky.hotel.commands.customer;

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
import com.alexnevsky.hotel.model.Customer;
import com.alexnevsky.hotel.model.Form;
import com.alexnevsky.hotel.model.Order;
import com.alexnevsky.hotel.model.enums.OrderStatusEnum;

/**
 * This customer's command deletes the customer's order with given id.
 * 
 * @version 1.0 22.05.2011
 * @author Alex Nevsky
 */
public class DeleteMyOrderCommand implements ICommand {

	static {
		logger = Logger.getLogger(DeleteMyOrderCommand.class);
	}
	private static Logger logger;

	/**
	 * Deletes order and redirects customer to the main page.
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
			FormDAO formDAO = daoFactory.getFormDAO();
			Customer customer = (Customer) request.getSession().getAttribute(AttributesManager.ATTRIBUTE_CUSTOMER);

			Order order = null;
			order = orderDAO.find(orderId);

			Form form = null;
			form = formDAO.find(order.getFormId());

			if (customer.getId().compareTo(order.getCustomerId()) == 0
					&& OrderStatusEnum.CANCELLED.equals(order.getOrderStatus())) {
				logger.info("Customer '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN)
						+ "'. Delete '" + order + "'" + " and '" + form + "'" + ". RemoteAddr: "
						+ request.getRemoteAddr());
				orderDAO.delete(order);
				formDAO.delete(form);
			} else {
				logger.warn("Customer '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN)
						+ "'. Trying to delete not own order '" + orderId + "'" + ". RemoteAddr: "
						+ request.getRemoteAddr());
				request.setAttribute(AttributesManager.ATTRIBUTE_ERROR_MESSAGE,
						MessageManager.PERMISSION_EXCEPTION_ERROR_MESSAGE);
				page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
				return page;
			}

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
		return "DeleteMyOrderCommand{" + '}';
	}
}