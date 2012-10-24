package com.alexnevsky.hotel.tags;

import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.BR_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.COLON_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.DATE_FORMAT_YYYYMMDD_TEMPLATE;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.DOLLAR_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.DOUBLE_BR_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.P_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.P_START_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.SPACE_HTML_TAG;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.controller.Controller;
import com.alexnevsky.hotel.dao.AbstractDAOFactory;
import com.alexnevsky.hotel.dao.CustomerDAO;
import com.alexnevsky.hotel.dao.FormDAO;
import com.alexnevsky.hotel.dao.OrderDAO;
import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.MessageManager;
import com.alexnevsky.hotel.model.Customer;
import com.alexnevsky.hotel.model.Form;
import com.alexnevsky.hotel.model.Order;

/**
 * Bills List JSP Custom Tag.
 * 
 * @version 1.0 06.06.2011
 * @author Alex Nevsky
 */
public class BillsListTag extends TagSupport {

	static {
		logger = Logger.getLogger(BillsListTag.class);
	}
	private static final long serialVersionUID = 1L;
	private static Logger logger;
	private Long orderId;

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * Provides logics for custom tag.
	 */
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = this.pageContext.getOut();
		try {
			String dataToView = this.getData();
			out.write(dataToView);
		} catch (IOException e) {
			logger.warn(e, e);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return SKIP_BODY;
	}

	private String getData() {
		String dataToView = null;

		Order order = null;
		Form form = null;
		Customer customer = null;

		try {
			AbstractDAOFactory daoFactory = Controller.getDAOFactory();

			OrderDAO orderDAO = daoFactory.getOrderDAO();
			FormDAO formDAO = daoFactory.getFormDAO();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();

			order = orderDAO.find(this.orderId);
			form = formDAO.find(order.getFormId());
			customer = customerDAO.find(order.getCustomerId());
		} catch (DAOException ex) {
			logger.error(ex, ex);
			return MessageManager.DAO_EXCEPTION_ERROR_MESSAGE;
		}

		dataToView = this.generateHTML(order, form, customer);

		return dataToView;
	}

	private String generateHTML(Order order, Form form, Customer customer) {
		StringBuilder sb = new StringBuilder();

		Locale locale = null;
		String specialUserLocale = (String) this.pageContext.getSession().getAttribute(
				AttributesManager.PARAM_NAME_SPECIAL_USER_LOCALE);
		if (specialUserLocale != null && !specialUserLocale.isEmpty()) {
			locale = new Locale(specialUserLocale);
		} else {
			locale = this.pageContext.getRequest().getLocale();
		}

		Date arrival = form.getArrival();
		Integer nights = form.getNights();
		Date departure = this.getDepartureDate(arrival, nights);

		Double orderAmount = order.getAmount();
		String orderDateCreatedStr = order.getDateCreated().toString();

		String firstName = customer.getFirstName();
		String lastName = customer.getLastName();
		Long creditCardNumber = customer.getCreditCardNumber();

		sb.append(P_START_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_CREATED, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(orderDateCreatedStr);
		sb.append(DOUBLE_BR_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_CUSTOMER, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(firstName);
		sb.append(SPACE_HTML_TAG);
		sb.append(lastName);
		sb.append(BR_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_CREDIT_CARD_NUMBER, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(creditCardNumber);
		sb.append(DOUBLE_BR_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ARRIVAL, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(arrival);
		sb.append(BR_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_DEPARTURE, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_TEMPLATE);
		sb.append(dateformatYYYYMMDD.format(departure));
		sb.append(DOUBLE_BR_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ORDER_ID, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(this.orderId);
		sb.append(BR_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ORDER_AMOUNT, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(DOLLAR_HTML_TAG);
		sb.append(orderAmount);
		sb.append(DOUBLE_BR_HTML_TAG);
		sb.append(P_END_HTML_TAG);

		return sb.toString();
	}

	/**
	 * Returns departure date.
	 * 
	 * @param arrival
	 *            Arrival date.
	 * @param nights
	 *            Nights.
	 * @return Returns departure date.
	 */
	private Date getDepartureDate(Date arrival, Integer nights) {
		Date departure = new Date();
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(arrival);
		gregorianCalendar.add(Calendar.DATE, nights);
		departure = gregorianCalendar.getTime();

		return departure;
	}

	/**
	 * Returns string for specified locale.
	 * 
	 * @param key
	 *            Key to search string for.
	 * @param loc
	 *            Preferred locale.
	 * @return Returns string for specified locale.
	 */
	private String getMessage(String key, Locale locale) {
		if (locale != null) {
			return MessageManager.getInstance().getProperty(key, locale);
		} else {
			return MessageManager.getInstance().getProperty(key);
		}
	}
}