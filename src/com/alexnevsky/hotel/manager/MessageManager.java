package com.alexnevsky.hotel.manager;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Contains constants for getting localized strings and managing methods.
 * Implements Singleton pattern.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class MessageManager {

	private static final String BUNDLE_NAME = "properties/messages";

	public static final String SYSTEM_TITLE = "system.title";
	public static final String WELCOME_TITLE = "welcome.title";
	public static final String ERROR_TITLE = "error.title";

	public static final String COMMAND_RESERVE_ROOM = "command.reserve.room";

	public static final String LOGIN_ERROR_MESSAGE = "exception.login.error.message";
	public static final String SERVLET_EXCEPTION_ERROR_MESSAGE = "exception.servlet.error.message";
	public static final String IO_EXCEPTION_ERROR_MESSAGE = "exception.io.error.message";
	public static final String DAO_EXCEPTION_ERROR_MESSAGE = "exception.dao.error.message";
	public static final String PERMISSION_EXCEPTION_ERROR_MESSAGE = "exception.permission.error.message";
	public static final String WRONG_DATE_ERROR_MESSAGE = "exception.wrong.date.error.message";

	public static final String RESULT_CANCEL_ORDER_MESSAGE = "page.main.result.cancel.order.message";
	public static final String RESULT_DELETE_ORDER_MESSAGE = "page.main.result.delete.order.message";
	public static final String RESULT_SELECT_ROOM_MESSAGE = "page.main.result.select.room.message";
	public static final String RESULT_PROCESS_FORM_MESSAGE = "page.main.result.process.form.message";

	public static final String TAG_CREATED = "tag.created";
	public static final String TAG_CUSTOMER = "tag.customer";
	public static final String TAG_CREDIT_CARD_NUMBER = "tag.credit.card.number";
	public static final String TAG_ARRIVAL = "tag.arrival";
	public static final String TAG_DEPARTURE = "tag.departure";
	public static final String TAG_ORDER_ID = "tag.order.id";
	public static final String TAG_ORDER_AMOUNT = "tag.order.amount";
	public static final String TAG_CUSTOMERS_INFO = "tag.customers.info";
	public static final String TAG_ID = "tag.id";
	public static final String TAG_FIRST_NAME = "tag.first.name";
	public static final String TAG_LAST_NAME = "tag.last.name";
	public static final String TAG_EMAIL = "tag.email";
	public static final String TAG_PHONE = "tag.phone";
	public static final String TAG_ADDRESS = "tag.address";
	public static final String TAG_ORDER = "tag.order";
	public static final String TAG_STATUS = "tag.status";
	public static final String TAG_ROOM = "tag.room";
	public static final String TAG_ADULT = "tag.adult";
	public static final String TAG_CHILD = "tag.child";
	public static final String TAG_NIGHTS = "tag.nights";
	public static final String TAG_PRICE = "tag.price";
	public static final String TAG_AMOUNT = "tag.amount";
	public static final String TAG_ROOM_CLASS = "tag.room.class";
	public static final String TAG_YOUR_COMMENTARY = "tag.your.commentary";
	public static final String TAG_CUSTOMER_COMMENTARY = "tag.customer.commentary";
	public static final String TAG_ACTIONS = "tag.actions";
	public static final String TAG_FIND_ROOM = "tag.find.room";
	public static final String TAG_CANCEL = "tag.cancel";
	public static final String TAG_DELETE = "tag.delete";
	public static final String TAG_SHOW_BILL = "tag.show.bill";
	public static final String TAG_ROOMS_INFO = "tag.rooms.info";
	public static final String TAG_CLASS = "tag.class";
	public static final String TAG_NIGHT_PRICE = "tag.night.price";
	public static final String TAG_ADULT_MAX = "tag.adult.max";
	public static final String TAG_CHILD_MAX = "tag.child.max";
	public static final String TAG_CREDIT_CARD = "tag.credit.card";
	public static final String TAG_NO_FREE_ROOMS_FOR_ORDER = "tag.no.free.rooms.for.order";
	public static final String TAG_SELECT_ROOM_FOR_ORDER = "tag.select.room.for.order";
	public static final String TAG_SELECT = "tag.select";

	private static MessageManager instance;

	private ResourceBundle resourceBundle;
	private HashMap<Locale, ResourceBundle> localeResourceBundle = new HashMap<Locale, ResourceBundle>();

	/**
	 * Helper class, so private constructor.
	 */
	private MessageManager() {
	}

	/**
	 * Returns MessageManager instance.
	 * 
	 * @return Ready-to-use MessageManager instance.
	 */
	public static MessageManager getInstance() {
		if (instance == null) {
			instance = new MessageManager();
			instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
		}
		return instance;
	}

	/**
	 * Returns string for default locale. Default locale is server locale.
	 * 
	 * @param key
	 *            Key to search string for.
	 * @return Returns string for default locale.
	 */
	public String getProperty(String key) {
		return this.resourceBundle.getString(key);
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
	public String getProperty(String key, Locale loc) {
		ResourceBundle res = null;
		if (this.localeResourceBundle.containsKey(loc)) {
			res = this.localeResourceBundle.get(loc);
		} else {
			res = ResourceBundle.getBundle(BUNDLE_NAME, loc);
			this.localeResourceBundle.put(loc, res);
		}
		return res.getString(key);
	}
}
