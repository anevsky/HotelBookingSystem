package com.alexnevsky.hotel.manager;

/**
 * Contains attribute strings and their in-program names.
 * 
 * @version 1.0 08.06.2011
 * @author Alex Nevsky
 */
public class AttributesManager {

	public static final String COMMAND_LOGIN = "login";
	public static final String COMMAND_LOGOUT = "logout";
	public static final String COMMAND_LANG = "lang";

	public static final String COMMAND_BOOKING_ROOM = "bookingroom";
	public static final String COMMAND_PROCESS_FORM = "processform";
	public static final String COMMAND_SHOW_MY_ORDERS = "showmyorders";
	public static final String COMMAND_SHOW_MY_BILL = "showmybill";
	public static final String COMMAND_CANCEL_MY_ORDER = "cancelmyorder";
	public static final String COMMAND_DELETE_MY_ORDER = "deletemyorder";

	public static final String COMMAND_VIEW_ROOMS = "viewrooms";
	public static final String COMMAND_VIEW_ORDERS = "vieworders";
	public static final String COMMAND_VIEW_BILL = "viewbill";
	public static final String COMMAND_VIEW_CUSTOMERS = "viewcustomers";
	public static final String COMMAND_CANCEL_ORDER = "cancelorder";
	public static final String COMMAND_DELETE_ORDER = "deleteorder";
	public static final String COMMAND_FIND_ROOM = "findroom";
	public static final String COMMAND_SELECT_ROOM = "selectroom";

	public static final String ATTRIBUTE_ERROR_MESSAGE = "errorMessage";

	public static final String ATTRIBUTE_VIEW_FREE_ROOMS_LIST = "viewFreeRoomsList";
	public static final String ATTRIBUTE_VIEW_BILLS_LIST = "viewBillsList";
	public static final String ATTRIBUTE_VIEW_CUSTOMERS_LIST = "viewCustomersList";
	public static final String ATTRIBUTE_VIEW_ORDERS_CUSTOMER_LIST = "viewOrdersCustomerList";
	public static final String ATTRIBUTE_VIEW_ORDERS_LIST = "viewOrdersList";
	public static final String ATTRIBUTE_VIEW_ROOMS_LIST = "viewRoomsList";

	public static final String ATTRIBUTE_YEAR = "year";
	public static final String ATTRIBUTE_RESULT = "result";
	public static final String ATTRIBUTE_LANG = "lang";

	public static final String ATTRIBUTE_CUSTOMER = "customer";
	public static final String ATTRIBUTE_CUSTOMER_ID = "customerId";

	public static final String ATTRIBUTE_LOCALE_RU = "ru";
	public static final String ATTRIBUTE_LOCALE_EN = "en";

	public static final String ATTRIBUTE_USER_ROLE = "userRole";
	public static final String ATTRIBUTE_ADMIN = "admin";
	public static final String ATTRIBUTE_LOGIN = "login";

	public static final String PARAM_NAME_COMMAND = "command";
	public static final String PARAM_NAME_SPECIAL_USER_LOCALE = "specialUserLocale";

	public static final String PARAM_NAME_LOGIN = "login";
	public static final String PARAM_NAME_PASSWORD = "password";

	public static final String PARAM_NAME_ORDER_ID = "orderid";
	public static final String PARAM_NAME_ROOM_ID = "roomid";
	public static final String PARAM_NAME_FORM_ID = "formid";

	public static final String PARAM_NAME_ADULT = "adult";
	public static final String PARAM_NAME_CHILD = "child";
	public static final String PARAM_NAME_ROOM_CLASS = "room-class";
	public static final String PARAM_NAME_ARRIVAL_YEAR = "arrival-year";
	public static final String PARAM_NAME_ARRIVAL_MONTH = "arrival-month";
	public static final String PARAM_NAME_ARRIVAL_DAY = "arrival-day";
	public static final String PARAM_NAME_NIGHTS = "nights";
	public static final String PARAM_NAME_COMMENTARY = "commentary";

	public static final String PARAM_NAME_LOCALE = "locale";

	/**
	 * Helper class, so private constructor.
	 */
	private AttributesManager() {
	}
}
