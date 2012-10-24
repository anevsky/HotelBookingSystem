package com.alexnevsky.hotel.commands.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.ConfigurationManager;

/**
 * This admin's command shows customer's order bill.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public class ViewBillCommand implements ICommand {

	static {
		logger = Logger.getLogger(ViewBillCommand.class);
	}
	private static Logger logger;

	/**
	 * Sets boolean condition for custom tag
	 * and redirects admin to the view bill page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		Long orderId = Long.valueOf(request.getParameter(AttributesManager.PARAM_NAME_ORDER_ID));

		logger.info("Admin '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. Execute "
				+ this.toString() + ". RemoteAddr: " + request.getRemoteAddr());

		Boolean viewBillsList = true;
		request.setAttribute(AttributesManager.ATTRIBUTE_VIEW_BILLS_LIST, viewBillsList);
		request.setAttribute(AttributesManager.PARAM_NAME_ORDER_ID, orderId);

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.BILLS_PAGE_PATH);

		return page;
	}

	@Override
	public String toString() {
		return "ViewBillCommand{" + '}';
	}
}