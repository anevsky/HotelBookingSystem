package com.alexnevsky.hotel.tags;

import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.BR_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.CAPTION_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.CAPTION_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.HR_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TABLE_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TABLE_LIGHT_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TAG_END;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TD_ALT_CLASS;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TD_CLASS_BEGIN;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TD_EMPTY_CLASS;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TD_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_COL_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_COL_NO_BG_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_ROW_CLASS_BEGIN;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_SPEC_ALT_CLASS;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_SPEC_CLASS;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TR_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TR_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.UL_CLASS_LINK_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.UL_END_HTML_TAG;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.controller.Controller;
import com.alexnevsky.hotel.dao.AbstractDAOFactory;
import com.alexnevsky.hotel.dao.CustomerDAO;
import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.MessageManager;
import com.alexnevsky.hotel.model.Customer;

/**
 * Customers List JSP Custom Tag.
 * 
 * @version 1.0 06.06.2011
 * @author Alex Nevsky
 */
public class CustomersListTag extends TagSupport {

	static {
		logger = Logger.getLogger(CustomersListTag.class);
	}
	private static final long serialVersionUID = 1L;
	private static Logger logger;

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

		List<Customer> customerList = null;

		try {
			AbstractDAOFactory daoFactory = Controller.getDAOFactory();

			CustomerDAO customerDAO = daoFactory.getCustomerDAO();

			customerList = customerDAO.list();
		} catch (DAOException ex) {
			logger.error(ex, ex);
			return MessageManager.DAO_EXCEPTION_ERROR_MESSAGE;
		}

		dataToView = this.generateHTML(customerList);

		return dataToView;
	}

	private String generateHTML(List<Customer> customerList) {
		StringBuilder sb = new StringBuilder();

		Locale locale = null;
		String specialUserLocale = (String) this.pageContext.getSession().getAttribute(
				AttributesManager.PARAM_NAME_SPECIAL_USER_LOCALE);
		if (specialUserLocale != null && !specialUserLocale.isEmpty()) {
			locale = new Locale(specialUserLocale);
		} else {
			locale = this.pageContext.getRequest().getLocale();
		}

		sb.append(TABLE_LIGHT_BEGIN_HTML_TAG);
		sb.append(CAPTION_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_CUSTOMERS_INFO, locale));
		sb.append(CAPTION_END_HTML_TAG);

		sb.append(TR_BEGIN_HTML_TAG);
		sb.append(TH_COL_NO_BG_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ID, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_FIRST_NAME, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_LAST_NAME, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_EMAIL, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_PHONE, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ADDRESS, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_CREDIT_CARD_NUMBER, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TR_END_HTML_TAG);

		String thClass = TH_SPEC_CLASS;
		String tdClass = TD_EMPTY_CLASS;

		for (Customer customer : customerList) {
			Long customerId = customer.getId();
			String firstName = customer.getFirstName();
			String lastName = customer.getLastName();
			String email = customer.getEmail();
			String phone = customer.getPhone();
			String address = customer.getAddress();
			Long creditCardNumber = customer.getCreditCardNumber();

			sb.append(TR_BEGIN_HTML_TAG);
			sb.append(TH_ROW_CLASS_BEGIN);
			sb.append(thClass);
			sb.append(TAG_END);
			sb.append(customerId);
			sb.append(TH_END_HTML_TAG);
			sb.append(TD_CLASS_BEGIN);
			sb.append(tdClass);
			sb.append(TAG_END);
			sb.append(firstName);
			sb.append(TD_END_HTML_TAG);
			sb.append(TD_CLASS_BEGIN);
			sb.append(tdClass);
			sb.append(TAG_END);
			sb.append(lastName);
			sb.append(TD_END_HTML_TAG);
			sb.append(TD_CLASS_BEGIN);
			sb.append(tdClass);
			sb.append(TAG_END);
			sb.append(email);
			sb.append(TD_END_HTML_TAG);
			sb.append(TD_CLASS_BEGIN);
			sb.append(tdClass);
			sb.append(TAG_END);
			sb.append(phone);
			sb.append(TD_END_HTML_TAG);
			sb.append(TD_CLASS_BEGIN);
			sb.append(tdClass);
			sb.append(TAG_END);
			sb.append(address);
			sb.append(TD_END_HTML_TAG);
			sb.append(TD_CLASS_BEGIN);
			sb.append(tdClass);
			sb.append(TAG_END);
			sb.append(creditCardNumber);
			sb.append(TD_END_HTML_TAG);
			sb.append(TR_END_HTML_TAG);

			if (thClass.equalsIgnoreCase(TH_SPEC_CLASS)) {
				thClass = TH_SPEC_ALT_CLASS;
				tdClass = TD_ALT_CLASS;
			} else {
				thClass = TH_SPEC_CLASS;
				tdClass = TD_EMPTY_CLASS;
			}
		}

		sb.append(TABLE_END_HTML_TAG);
		sb.append(BR_HTML_TAG);

		sb.append(UL_CLASS_LINK_BEGIN_HTML_TAG);
		sb.append(UL_END_HTML_TAG);
		sb.append(BR_HTML_TAG);
		sb.append(HR_HTML_TAG);
		sb.append(BR_HTML_TAG);

		return sb.toString();
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