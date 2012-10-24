package com.alexnevsky.hotel.tags;

import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.A_HREF_CANCEL_ORDER;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.A_HREF_DELETE_ORDER;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.A_HREF_END;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.BR_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.CAPTION_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.CAPTION_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.COLON_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.COMMA_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.FIELDSET_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.FIELDSET_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.FORM_BOOKING_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.FORM_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.HR_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.INPUT_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.INPUT_SELECT_ROOM_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.INPUT_SUBMIT_SELECT_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.LABEL_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.LABEL_ROOM_ID_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.LEGEND_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.LEGEND_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.LI_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.LI_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.MINUS;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.NUMBER_SIGN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.OPTION_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.OPTION_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.PERIOD_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.P_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.P_START_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.P_SUBMIT_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.SELECT_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.SELECT_ROOM_ID_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.SPACE_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.STRONG_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.STRONG_COLOR_DARKORCHILD_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.STRONG_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TABLE_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TABLE_LIGHT_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TAG_END;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TD_ALT_CLASS;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TD_BEGIN_HTML_TAG;
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
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.VERTICAL_BAR_HTML_TAG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.controller.Controller;
import com.alexnevsky.hotel.dao.AbstractDAOFactory;
import com.alexnevsky.hotel.dao.CreditCardDAO;
import com.alexnevsky.hotel.dao.CustomerDAO;
import com.alexnevsky.hotel.dao.FormDAO;
import com.alexnevsky.hotel.dao.OrderDAO;
import com.alexnevsky.hotel.dao.RoomDAO;
import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.MessageManager;
import com.alexnevsky.hotel.model.CreditCard;
import com.alexnevsky.hotel.model.Customer;
import com.alexnevsky.hotel.model.Form;
import com.alexnevsky.hotel.model.Order;
import com.alexnevsky.hotel.model.Room;
import com.alexnevsky.hotel.model.enums.OrderStatusEnum;
import com.alexnevsky.hotel.model.enums.RoomClassEnum;

/**
 * Free Rooms List JSP Custom Tag.
 * 
 * @version 1.0 06.06.2011
 * @author Alex Nevsky
 */
public class FreeRoomsListTag extends TagSupport {

	static {
		logger = Logger.getLogger(FreeRoomsListTag.class);
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

		Customer customer = null;
		CreditCard creditCard = null;
		Order order = null;
		Form form = null;
		Room orderRoom = null;
		List<Room> freeRoomList = null;

		try {
			AbstractDAOFactory daoFactory = Controller.getDAOFactory();

			OrderDAO orderDAO = daoFactory.getOrderDAO();
			FormDAO formDAO = daoFactory.getFormDAO();
			CustomerDAO customerDAO = daoFactory.getCustomerDAO();
			RoomDAO roomDAO = daoFactory.getRoomDAO();
			CreditCardDAO creditCardDAO = daoFactory.getCreditCardDAO();

			order = orderDAO.find(this.orderId);
			form = formDAO.find(order.getFormId());

			freeRoomList = this.getFreeRooms(form);

			customer = customerDAO.find(order.getCustomerId());
			creditCard = creditCardDAO.find(customer.getCreditCardNumber());

			Long orderRoomId = order.getRoomId();

			Long formId = order.getFormId();
			form = formDAO.find(formId);

			orderRoom = roomDAO.find(orderRoomId);
		} catch (DAOException ex) {
			logger.error(ex, ex);
			return MessageManager.DAO_EXCEPTION_ERROR_MESSAGE;
		}

		dataToView = this.generateHTML(freeRoomList, order, customer, creditCard, form, orderRoom);

		return dataToView;
	}

	private List<Room> getFreeRooms(Form form) throws DAOException {
		List<Room> freeRoomList = null;

		AbstractDAOFactory daoFactory = Controller.getDAOFactory();

		OrderDAO orderDAO = daoFactory.getOrderDAO();
		FormDAO formDAO = daoFactory.getFormDAO();
		RoomDAO roomDAO = daoFactory.getRoomDAO();

		Date arrival = form.getArrival();
		Integer nights = form.getNights();
		Date departure = this.getDepartureDate(arrival, nights);

		Integer adult = form.getAdult();
		Integer child = form.getChild();
		RoomClassEnum roomClass = form.getRoomClass();

		List<Room> roomParamList = null;
		roomParamList = roomDAO.list(adult, child, roomClass);

		List<Room> notFreeRoomList = new ArrayList<Room>();

		for (Room room : roomParamList) {
			List<Order> orderWithParamRoomList = null;
			orderWithParamRoomList = orderDAO.listWhereRoomAndStatus(room.getId(), OrderStatusEnum.CHECKED);

			for (Order orderTmp : orderWithParamRoomList) {
				Form formTmp = null;
				formTmp = formDAO.find(orderTmp.getFormId());

				Date arrivalTmp = formTmp.getArrival();
				Integer nightsTmp = formTmp.getNights();
				Date departureTmp = new Date();
				GregorianCalendar gregorianCalendarTmp = new GregorianCalendar();
				gregorianCalendarTmp.setTime(arrivalTmp);
				gregorianCalendarTmp.add(Calendar.DATE, nightsTmp);
				departureTmp = gregorianCalendarTmp.getTime();
				int after = arrival.compareTo(departureTmp);
				if (!departure.before(arrivalTmp)) {
					notFreeRoomList.add(room);
				} else if (after < 0) {
					notFreeRoomList.add(room);
				}
			}
		}

		roomParamList.removeAll(notFreeRoomList);

		freeRoomList = roomParamList;

		return freeRoomList;
	}

	private String generateHTML(List<Room> freeRoomList, Order order, Customer customer, CreditCard creditCard,
			Form form, Room orderRoom) {
		StringBuilder sb = new StringBuilder();

		Locale locale = null;
		String specialUserLocale = (String) this.pageContext.getSession().getAttribute(
				AttributesManager.PARAM_NAME_SPECIAL_USER_LOCALE);
		if (specialUserLocale != null && !specialUserLocale.isEmpty()) {
			locale = new Locale(specialUserLocale);
		} else {
			locale = this.pageContext.getRequest().getLocale();
		}

		String roomClass = form.getRoomClass().toString();

		String orderStatus = order.getOrderStatus().toString();

		String commentary = form.getCommentary();

		String firstName = customer.getFirstName();
		String lastName = customer.getLastName();

		// ---------------------------------------------------------------------
		// add customer and room name
		sb.append(this.printCustomerAndRoomName(firstName, lastName, roomClass, locale));

		// ---------------------------------------------------------------------
		// add customer table
		sb.append(this.printCustomerTable(customer, form, creditCard, locale));

		// ---------------------------------------------------------------------
		// add order table
		sb.append(this.printOrderTable(order, form, orderRoom, locale));

		// ---------------------------------------------------------------------
		// add after tables info and actions
		sb.append(this.printAfterTables(roomClass, commentary, this.orderId, orderStatus, locale));

		// ---------------------------------------------------------------------
		// add select room form
		sb.append(this.printSelectRoomForm(freeRoomList, locale));

		// ---------------------------------------------------------------------
		// add rooms table
		sb.append(this.printRoomsTable(freeRoomList, locale));

		return sb.toString();
	}

	private String printCustomerAndRoomName(String firstName, String lastName, String roomClass, Locale locale) {
		StringBuilder sb = new StringBuilder();

		sb.append(P_START_HTML_TAG);
		sb.append(STRONG_COLOR_DARKORCHILD_BEGIN_HTML_TAG);
		sb.append(firstName);
		sb.append(SPACE_HTML_TAG);
		sb.append(lastName);
		sb.append(STRONG_END_HTML_TAG);
		sb.append(COMMA_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(roomClass);
		sb.append(SPACE_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ROOM, locale));
		sb.append(PERIOD_HTML_TAG);
		sb.append(P_END_HTML_TAG);

		return sb.toString();
	}

	private String printCustomerTable(Customer customer, Form form, CreditCard creditCard, Locale locale) {
		StringBuilder sb = new StringBuilder();

		String thClass = TH_SPEC_ALT_CLASS;
		String tdClass = TD_ALT_CLASS;

		String creditCardType = creditCard.getCardType().toString();
		String creditCardHolderName = creditCard.getHolderName().toString();

		Long customerId = customer.getId();
		String firstName = customer.getFirstName();
		String lastName = customer.getLastName();
		String email = customer.getEmail();
		String phone = customer.getPhone();
		String address = customer.getAddress();
		Long creditCardNumber = customer.getCreditCardNumber();

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
		sb.append(TR_END_HTML_TAG);

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
		sb.append(TR_END_HTML_TAG);

		sb.append(TABLE_END_HTML_TAG);
		sb.append(BR_HTML_TAG);

		sb.append(UL_CLASS_LINK_BEGIN_HTML_TAG);

		sb.append(LI_BEGIN_HTML_TAG);
		sb.append(STRONG_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ADDRESS, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(STRONG_END_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(address);
		sb.append(LI_END_HTML_TAG);

		sb.append(LI_BEGIN_HTML_TAG);
		sb.append(STRONG_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_CREDIT_CARD, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(STRONG_END_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(creditCardType);
		sb.append(COMMA_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(NUMBER_SIGN_HTML_TAG);
		sb.append(creditCardNumber);
		sb.append(COMMA_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(creditCardHolderName);
		sb.append(LI_END_HTML_TAG);

		sb.append(UL_END_HTML_TAG);
		sb.append(BR_HTML_TAG);

		return sb.toString();
	}

	private String printOrderTable(Order order, Form form, Room room, Locale locale) {
		StringBuilder sb = new StringBuilder();

		Long orderRoomId = order.getRoomId();
		Double orderAmount = order.getAmount();
		String orderDateCreatedStr = order.getDateCreated().toString();

		String orderStatus = order.getOrderStatus().toString();

		Double roomPrice = 0.0;

		if (room != null) {
			roomPrice = room.getNightPrice();
		}

		Integer formAdult = form.getAdult();
		Integer formChild = form.getChild();
		Date formArrival = form.getArrival();
		Integer formNights = form.getNights();

		String orderRoomIdStr = orderRoomId.toString();
		String orderAmountStr = orderAmount.toString();
		String formArrivalStr = formArrival.toString();
		String roomPriceStr = roomPrice.toString();

		if (orderRoomIdStr.isEmpty() || orderRoomId == null || orderRoomId < 1) {
			orderRoomIdStr = MINUS;
		}

		if (orderAmountStr.isEmpty() || orderAmount == null || orderAmount < 1) {
			orderAmountStr = MINUS;
		}

		if (formArrivalStr.isEmpty() || formArrivalStr == null) {
			formArrivalStr = MINUS;
		}

		if (roomPriceStr.isEmpty() || roomPrice == null || roomPrice < 1) {
			roomPriceStr = MINUS;
		}

		sb.append(TABLE_LIGHT_BEGIN_HTML_TAG);
		sb.append(CAPTION_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ORDER, locale));
		sb.append(SPACE_HTML_TAG);
		sb.append(NUMBER_SIGN_HTML_TAG);
		sb.append(this.orderId);
		sb.append(PERIOD_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_CREATED, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(orderDateCreatedStr);
		sb.append(CAPTION_END_HTML_TAG);

		sb.append(TR_BEGIN_HTML_TAG);
		sb.append(TH_COL_NO_BG_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_STATUS, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ROOM, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ADULT, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_CHILD, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ARRIVAL, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_NIGHTS, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_NIGHT_PRICE, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_AMOUNT, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TR_END_HTML_TAG);

		sb.append(TR_BEGIN_HTML_TAG);
		sb.append(TH_ROW_CLASS_BEGIN);
		sb.append(TH_SPEC_CLASS);
		sb.append(TAG_END);
		sb.append(orderStatus);
		sb.append(TH_END_HTML_TAG);
		sb.append(TD_BEGIN_HTML_TAG);
		sb.append(orderRoomIdStr);
		sb.append(TD_END_HTML_TAG);
		sb.append(TD_BEGIN_HTML_TAG);
		sb.append(formAdult);
		sb.append(TD_END_HTML_TAG);
		sb.append(TD_BEGIN_HTML_TAG);
		sb.append(formChild);
		sb.append(TD_END_HTML_TAG);
		sb.append(TD_BEGIN_HTML_TAG);
		sb.append(formArrivalStr);
		sb.append(TD_END_HTML_TAG);
		sb.append(TD_BEGIN_HTML_TAG);
		sb.append(formNights);
		sb.append(TD_END_HTML_TAG);
		sb.append(TD_BEGIN_HTML_TAG);
		sb.append(roomPriceStr);
		sb.append(TD_END_HTML_TAG);
		sb.append(TD_BEGIN_HTML_TAG);
		sb.append(orderAmountStr);
		sb.append(TD_END_HTML_TAG);
		sb.append(TR_END_HTML_TAG);

		sb.append(TABLE_END_HTML_TAG);
		sb.append(BR_HTML_TAG);

		return sb.toString();
	}

	private String printAfterTables(String roomClass, String commentary, Long orderId, String orderStatus, Locale locale) {
		StringBuilder sb = new StringBuilder();

		sb.append(UL_CLASS_LINK_BEGIN_HTML_TAG);

		sb.append(LI_BEGIN_HTML_TAG);
		sb.append(STRONG_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ROOM_CLASS, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(STRONG_END_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(roomClass);
		sb.append(LI_END_HTML_TAG);

		if (commentary != null && !commentary.isEmpty()) {
			sb.append(LI_BEGIN_HTML_TAG);
			sb.append(STRONG_BEGIN_HTML_TAG);
			sb.append(this.getMessage(MessageManager.TAG_CUSTOMER_COMMENTARY, locale));
			sb.append(COLON_HTML_TAG);
			sb.append(STRONG_END_HTML_TAG);
			sb.append(SPACE_HTML_TAG);
			sb.append(commentary);
			sb.append(LI_END_HTML_TAG);
		}

		// add available actions
		sb.append(this.printActions(orderId, orderStatus, locale));

		sb.append(UL_END_HTML_TAG);
		sb.append(BR_HTML_TAG);
		sb.append(HR_HTML_TAG);
		sb.append(BR_HTML_TAG);

		return sb.toString();
	}

	private String printActions(Long orderId, String orderStatus, Locale locale) {
		StringBuilder sb = new StringBuilder();

		sb.append(LI_BEGIN_HTML_TAG);
		sb.append(STRONG_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ACTIONS, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(STRONG_END_HTML_TAG);

		if (!OrderStatusEnum.CANCELLED.toString().toUpperCase().equals(orderStatus.toUpperCase())) {
			sb.append(A_HREF_CANCEL_ORDER);
			sb.append(orderId);
			sb.append(TAG_END);
			sb.append(this.getMessage(MessageManager.TAG_CANCEL, locale));
			sb.append(A_HREF_END);
			sb.append(SPACE_HTML_TAG);
			sb.append(VERTICAL_BAR_HTML_TAG);
			sb.append(SPACE_HTML_TAG);
		}

		sb.append(A_HREF_DELETE_ORDER);
		sb.append(orderId);
		sb.append(TAG_END);
		sb.append(this.getMessage(MessageManager.TAG_DELETE, locale));
		sb.append(A_HREF_END);

		sb.append(PERIOD_HTML_TAG);

		sb.append(LI_END_HTML_TAG);

		return sb.toString();
	}

	private String printSelectRoomForm(List<Room> freeRoomList, Locale locale) {
		StringBuilder sb = new StringBuilder();

		sb.append(FORM_BOOKING_BEGIN_HTML_TAG);
		sb.append(INPUT_SELECT_ROOM_HTML_TAG);
		sb.append(FIELDSET_BEGIN_HTML_TAG);
		sb.append(LEGEND_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_SELECT_ROOM_FOR_ORDER, locale));
		sb.append(LEGEND_END_HTML_TAG);
		sb.append(BR_HTML_TAG);
		sb.append(P_START_HTML_TAG);
		sb.append(LABEL_ROOM_ID_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ROOM, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(LABEL_END_HTML_TAG);
		sb.append(SELECT_ROOM_ID_BEGIN_HTML_TAG);

		for (Room roomForSelect : freeRoomList) {
			sb.append(OPTION_BEGIN_HTML_TAG);
			sb.append(roomForSelect.getId());
			sb.append(OPTION_END_HTML_TAG);
		}

		sb.append(SELECT_END_HTML_TAG);
		sb.append(P_END_HTML_TAG);
		sb.append(BR_HTML_TAG);
		sb.append(P_SUBMIT_BEGIN_HTML_TAG);
		sb.append(INPUT_SUBMIT_SELECT_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_SELECT, locale));
		sb.append(TAG_END);
		sb.append(INPUT_END_HTML_TAG);
		sb.append(P_END_HTML_TAG);
		sb.append(FIELDSET_END_HTML_TAG);
		sb.append(FORM_END_HTML_TAG);
		sb.append(BR_HTML_TAG);

		return sb.toString();
	}

	private String printRoomsTable(List<Room> freeRoomList, Locale locale) {
		StringBuilder sb = new StringBuilder();

		String thClass = TH_SPEC_ALT_CLASS;
		String tdClass = TD_ALT_CLASS;

		sb.append(TABLE_LIGHT_BEGIN_HTML_TAG);
		sb.append(CAPTION_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ROOMS_INFO, locale));
		sb.append(CAPTION_END_HTML_TAG);

		sb.append(TR_BEGIN_HTML_TAG);
		sb.append(TH_COL_NO_BG_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ID, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ADULT_MAX, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_CHILD_MAX, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_CLASS, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TH_COL_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_NIGHT_PRICE, locale));
		sb.append(TH_END_HTML_TAG);
		sb.append(TR_END_HTML_TAG);

		for (Room room : freeRoomList) {
			Long roomId = room.getId();
			Integer adultMax = room.getAdultMax();
			Integer childMax = room.getChildMax();
			String roomListClass = room.getRoomClass().toString();
			Double nightPrice = room.getNightPrice();

			sb.append(TR_BEGIN_HTML_TAG);
			sb.append(TH_ROW_CLASS_BEGIN);
			sb.append(thClass);
			sb.append(TAG_END);
			sb.append(roomId);
			sb.append(TH_END_HTML_TAG);
			sb.append(TD_CLASS_BEGIN);
			sb.append(tdClass);
			sb.append(TAG_END);
			sb.append(adultMax);
			sb.append(TD_END_HTML_TAG);
			sb.append(TD_CLASS_BEGIN);
			sb.append(tdClass);
			sb.append(TAG_END);
			sb.append(childMax);
			sb.append(TD_END_HTML_TAG);
			sb.append(TD_CLASS_BEGIN);
			sb.append(tdClass);
			sb.append(TAG_END);
			sb.append(roomListClass);
			sb.append(TD_END_HTML_TAG);
			sb.append(TD_CLASS_BEGIN);
			sb.append(tdClass);
			sb.append(TAG_END);
			sb.append(nightPrice);
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

		if (freeRoomList.isEmpty()) {
			sb.append(this.getMessage(MessageManager.TAG_NO_FREE_ROOMS_FOR_ORDER, locale));
		}

		sb.append(UL_CLASS_LINK_BEGIN_HTML_TAG);
		sb.append(UL_END_HTML_TAG);
		sb.append(BR_HTML_TAG);
		sb.append(HR_HTML_TAG);
		sb.append(BR_HTML_TAG);

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