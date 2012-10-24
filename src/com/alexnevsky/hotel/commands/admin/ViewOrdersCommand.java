package com.alexnevsky.hotel.commands.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.ConfigurationManager;

/**
 * This admin's command shows all customer's orders with available actions.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public class ViewOrdersCommand implements ICommand {

	static {
		logger = Logger.getLogger(ViewOrdersCommand.class);
	}
	private static Logger logger;

	/**
	 * Sets boolean condition for custom tag
	 * and redirects admin to the view orders page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		logger.info("Admin '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. Execute "
				+ this.toString() + ". RemoteAddr: " + request.getRemoteAddr());

		Boolean viewOrdersList = true;
		request.setAttribute(AttributesManager.ATTRIBUTE_VIEW_ORDERS_LIST, viewOrdersList);

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ORDERS_PAGE_PATH);

		return page;
	}

	@Override
	public String toString() {
		return "ViewOrdersCommand{" + '}';
	}
}