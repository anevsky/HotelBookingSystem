package com.alexnevsky.hotel.commands.customer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.ConfigurationManager;
import com.alexnevsky.hotel.model.Customer;

/**
 * This customer's command shows all customer's orders.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public class ShowMyOrdersCommand implements ICommand {

	static {
		logger = Logger.getLogger(ShowMyOrdersCommand.class);
	}
	private static Logger logger;

	/**
	 * Checks customer's id, sets boolean condition for custom tag
	 * and redirects customer to the view orders page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		logger.info("Customer '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN)
				+ "'. Execute " + this.toString() + ". RemoteAddr: " + request.getRemoteAddr());

		Boolean viewOrdersCustomerList = true;
		request.setAttribute(AttributesManager.ATTRIBUTE_VIEW_ORDERS_CUSTOMER_LIST, viewOrdersCustomerList);

		Customer customer = (Customer) request.getSession().getAttribute(AttributesManager.ATTRIBUTE_CUSTOMER);
		request.setAttribute(AttributesManager.ATTRIBUTE_CUSTOMER_ID, customer.getId());

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ORDERS_PAGE_PATH);

		return page;
	}

	@Override
	public String toString() {
		return "ShowMyOrdersCommand{" + '}';
	}
}
