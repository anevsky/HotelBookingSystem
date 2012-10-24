package com.alexnevsky.hotel.commands.customer;

import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

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
import com.alexnevsky.hotel.model.enums.RoomClassEnum;

/**
 * This customer's command processes customer's form data for the new order.
 * 
 * @version 1.0 11.05.2011
 * @author Alex Nevsky
 */
public class ProcessFormCommand implements ICommand {

	static {
		logger = Logger.getLogger(ProcessFormCommand.class);
	}
	private static Logger logger;

	/**
	 * Processes customer's booking form and redirects customer to the main page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		logger.info("User '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. Execute "
				+ this.toString() + ". Process form data" + ". RemoteAddr: " + request.getRemoteAddr());

		Form form = this.completeForm(request);

		Date arrival = form.getArrival();

		GregorianCalendar now = new GregorianCalendar();
		if (arrival.before(now.getTime())) {
			request.setAttribute(AttributesManager.ATTRIBUTE_ERROR_MESSAGE, MessageManager.WRONG_DATE_ERROR_MESSAGE);
			page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
			return page;
		}

		try {
			AbstractDAOFactory daoFactory = Controller.getDAOFactory();

			FormDAO formDAO = daoFactory.getFormDAO();
			try {
				formDAO.create(form);
			} catch (IllegalArgumentException ex) {
				logger.error(ex, ex);
			}

			Customer customer = (Customer) request.getSession().getAttribute(AttributesManager.ATTRIBUTE_CUSTOMER);

			Order order = new Order();
			order.setCustomerId(customer.getId());
			order.setFormId(form.getId());

			OrderDAO orderDAO = daoFactory.getOrderDAO();
			try {
				orderDAO.create(order);
			} catch (IllegalArgumentException ex) {
				logger.error(ex, ex);
			}

			logger.info("User '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. "
					+ "Booking room form data:" + form + ". RemoteAddr: " + request.getRemoteAddr());

			logger.info("User '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. "
					+ "Create order:" + order + ". RemoteAddr: " + request.getRemoteAddr());

			request.setAttribute(AttributesManager.ATTRIBUTE_RESULT, MessageManager.RESULT_PROCESS_FORM_MESSAGE);

		} catch (DAOException ex) {
			logger.error(ex, ex);
			request.setAttribute(AttributesManager.ATTRIBUTE_ERROR_MESSAGE, MessageManager.DAO_EXCEPTION_ERROR_MESSAGE);
			page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
			return page;
		}

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);

		return page;
	}

	/**
	 * Retrieves booking form data from request and complete order's form.
	 * 
	 * @param request
	 *            The request with booking form data.
	 * @return The order's form.
	 */
	private Form completeForm(HttpServletRequest request) {
		String adult = getFieldValue(request, AttributesManager.PARAM_NAME_ADULT);
		String child = getFieldValue(request, AttributesManager.PARAM_NAME_CHILD);
		String roomClass = getFieldValue(request, AttributesManager.PARAM_NAME_ROOM_CLASS);
		String arrivalYear = getFieldValue(request, AttributesManager.PARAM_NAME_ARRIVAL_YEAR);
		String arrivalMonth = getFieldValue(request, AttributesManager.PARAM_NAME_ARRIVAL_MONTH);
		String arrivalDay = getFieldValue(request, AttributesManager.PARAM_NAME_ARRIVAL_DAY);
		String nights = getFieldValue(request, AttributesManager.PARAM_NAME_NIGHTS);
		String commentary = getFieldValue(request, AttributesManager.PARAM_NAME_COMMENTARY);

		String login = (String) request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN);
		GregorianCalendar gregorianCalendar = new GregorianCalendar();

		Integer hash = (login + gregorianCalendar.getTime().toString()).hashCode();
		Long formId = hash.longValue();

		gregorianCalendar.set(Integer.valueOf(arrivalYear), Integer.valueOf(arrivalMonth) - 1,
				Integer.valueOf(arrivalDay));

		Date arrival = gregorianCalendar.getTime();

		Form form = new Form(formId, Integer.valueOf(adult), Integer.valueOf(child), RoomClassEnum.valueOf(roomClass
				.toUpperCase()), arrival, Integer.valueOf(nights), commentary);

		return form;
	}

	/**
	 * Returns the form field value from the given request associated with the
	 * given field name. It returns null if the form field value is null or is
	 * empty after trimming all whitespace.
	 * 
	 * @param request
	 *            The request to return the form field value for.
	 * @param fieldName
	 *            The field name to be associated with the field value.
	 * @return The form field value from the given request associated with the
	 *         given field name.
	 */
	public static String getFieldValue(HttpServletRequest request, String fieldName) {
		String value = request.getParameter(fieldName);
		return isEmpty(value) ? null : value;
	}

	/**
	 * Returns true if the given value is null or is empty.
	 * 
	 * @param value
	 *            The value to be checked on emptiness.
	 * @return True if the given value is null or is empty.
	 */
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		} else if (value instanceof String) {
			return ((String) value).trim().length() == 0;
		} else if (value instanceof Object[]) {
			return ((Object[]) value).length == 0;
		} else if (value instanceof Collection<?>) {
			return ((Collection<?>) value).isEmpty();
		} else if (value instanceof Map<?, ?>) {
			return ((Map<?, ?>) value).isEmpty();
		} else {
			return value.toString() == null || value.toString().trim().length() == 0;
		}
	}

	@Override
	public String toString() {
		return "ProcessFormCommand{" + '}';
	}
}
