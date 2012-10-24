package com.alexnevsky.hotel.manager;

import java.util.ResourceBundle;

/**
 * Contains config strings and their in-program names.
 * Implements Singleton pattern.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class ConfigurationManager {

	private static final String BUNDLE_NAME = "properties/config";

	public static final String CONTROLLER = "controller.path";
	public static final String INDEX_PAGE_PATH = "page.path.index";
	public static final String ERROR_PAGE_PATH = "page.path.error";
	public static final String MAIN_PAGE_PATH = "page.path.main";
	public static final String LOGIN_PAGE_PATH = "page.path.login";
	public static final String BOOKING_ROOM_PAGE_PATH = "page.path.booking.room";
	public static final String ORDERS_PAGE_PATH = "page.path.orders";
	public static final String BILLS_PAGE_PATH = "page.path.bills";
	public static final String ROOMS_PAGE_PATH = "page.path.rooms";
	public static final String CUSTOMERS_PAGE_PATH = "page.path.customers";

	public static final String USER_ROLE_ADMIN = "user.role.admin";
	public static final String USER_ROLE_CUSTOMER = "user.role.customer";

	private static ConfigurationManager instance;

	private ResourceBundle resourceBundle;

	/**
	 * Helper class, so private constructor.
	 */
	private ConfigurationManager() {
	}

	/**
	 * Returns ConfigurationManager instance.
	 * 
	 * @return Ready-to-use ConfigurationManager instance.
	 */
	public static ConfigurationManager getInstance() {
		if (instance == null) {
			instance = new ConfigurationManager();
			instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
		}
		return instance;
	}

	/**
	 * Returns config string.
	 * 
	 * @param key
	 *            Key to search string for.
	 * @return Returns config string.
	 */
	public String getProperty(String key) {
		return this.resourceBundle.getString(key);
	}
}
