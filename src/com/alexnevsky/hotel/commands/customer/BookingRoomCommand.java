package com.alexnevsky.hotel.commands.customer;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.ConfigurationManager;

/**
 * This customer's command redirects customer to the booking room page.
 * 
 * @version 1.0 11.05.2011
 * @author Alex Nevsky
 */
public class BookingRoomCommand implements ICommand {

	static {
		logger = Logger.getLogger(BookingRoomCommand.class);
	}
	private static Logger logger;

	/**
	 * Redirects customer to the booking room page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		logger.info("Customer '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN)
				+ "'. Execute " + this.toString() + ". RemoteAddr: " + request.getRemoteAddr());

		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		request.setAttribute(AttributesManager.ATTRIBUTE_YEAR, gregorianCalendar.get(Calendar.YEAR));

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.BOOKING_ROOM_PAGE_PATH);

		return page;
	}

	@Override
	public String toString() {
		return "BookingRoomCommand{" + '}';
	}
}