package com.alexnevsky.hotel.tags;

import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.A_HREF_CANCEL_MY_ORDER;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.A_HREF_DELETE_MY_ORDER;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.A_HREF_END;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.A_HREF_SHOW_MY_BILL;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.BR_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.CAPTION_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.CAPTION_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.COLON_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.HR_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.LI_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.LI_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.MINUS;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.NUMBER_SIGN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.PERIOD_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.SPACE_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.STRONG_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.STRONG_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TABLE_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TABLE_LIGHT_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TAG_END;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TD_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TD_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_COL_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_COL_NO_BG_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_ROW_CLASS_BEGIN;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TH_SPEC_CLASS;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TR_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.TR_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.UL_CLASS_LINK_BEGIN_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.UL_END_HTML_TAG;
import static com.alexnevsky.hotel.tags.helpers.HTMLHelper.VERTICAL_BAR_HTML_TAG;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.controller.Controller;
import com.alexnevsky.hotel.dao.AbstractDAOFactory;
import com.alexnevsky.hotel.dao.FormDAO;
import com.alexnevsky.hotel.dao.OrderDAO;
import com.alexnevsky.hotel.dao.RoomDAO;
import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.MessageManager;
import com.alexnevsky.hotel.model.Form;
import com.alexnevsky.hotel.model.Order;
import com.alexnevsky.hotel.model.Room;
import com.alexnevsky.hotel.model.enums.OrderStatusEnum;
import com.alexnevsky.hotel.model.enums.RoomClassEnum;

/**
 * Customer's Orders List JSP Custom Tag.
 * 
 * @version 1.0 06.06.2011
 * @author Alex Nevsky
 */
public class OrdersCustomerListTag extends TagSupport {

	static {
		logger = Logger.getLogger(OrdersCustomerListTag.class);
	}
	private static final long serialVersionUID = 1L;
	private static Logger logger;
	private Long customerId;

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

		StringBuilder sb = new StringBuilder();

		try {
			AbstractDAOFactory daoFactory = Controller.getDAOFactory();

			OrderDAO orderDAO = daoFactory.getOrderDAO();
			List<Order> orderList = null;
			orderList = orderDAO.listWhereCustomer(this.customerId);

			FormDAO formDAO = daoFactory.getFormDAO();
			Form form = null;

			RoomDAO roomDAO = daoFactory.getRoomDAO();
			Room room = null;

			for (Order order : orderList) {
				Long orderRoomId = order.getRoomId();

				room = roomDAO.find(orderRoomId);

				Long formId = order.getFormId();
				form = formDAO.find(formId);

				sb.append(this.generateHTML(order, form, room));
			}
		} catch (DAOException ex) {
			logger.error(ex, ex);
			return MessageManager.DAO_EXCEPTION_ERROR_MESSAGE;
		}

		dataToView = sb.toString();

		return dataToView;
	}

	private String generateHTML(Order order, Form form, Room room) {
		StringBuilder sb = new StringBuilder();

		Locale locale = null;
		String specialUserLocale = (String) this.pageContext.getSession().getAttribute(
				AttributesManager.PARAM_NAME_SPECIAL_USER_LOCALE);
		if (specialUserLocale != null && !specialUserLocale.isEmpty()) {
			locale = new Locale(specialUserLocale);
		} else {
			locale = this.pageContext.getRequest().getLocale();
		}

		String orderStatus = order.getOrderStatus().toString();
		Long orderId = order.getId();
		Long orderRoomId = order.getRoomId();
		Double orderAmount = order.getAmount();
		String orderDateCreatedStr = order.getDateCreated().toString();

		Integer formAdult = form.getAdult();
		Integer formChild = form.getChild();
		RoomClassEnum roomClass = form.getRoomClass();
		Date formArrival = form.getArrival();
		Integer formNights = form.getNights();
		String commentary = form.getCommentary();

		Double roomPrice = 0.0;

		if (room != null) {
			roomPrice = room.getNightPrice();
		}

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
		sb.append(orderId);
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
			sb.append(this.getMessage(MessageManager.TAG_YOUR_COMMENTARY, locale));
			sb.append(COLON_HTML_TAG);
			sb.append(STRONG_END_HTML_TAG);
			sb.append(SPACE_HTML_TAG);
			sb.append(commentary);
			sb.append(LI_END_HTML_TAG);
		}

		sb.append(LI_BEGIN_HTML_TAG);
		sb.append(STRONG_BEGIN_HTML_TAG);
		sb.append(this.getMessage(MessageManager.TAG_ACTIONS, locale));
		sb.append(COLON_HTML_TAG);
		sb.append(SPACE_HTML_TAG);
		sb.append(STRONG_END_HTML_TAG);

		if (OrderStatusEnum.UNCHECKED.toString().toUpperCase().equals(orderStatus.toUpperCase())) {
			sb.append(A_HREF_CANCEL_MY_ORDER);
			sb.append(orderId);
			sb.append(TAG_END);
			sb.append(this.getMessage(MessageManager.TAG_CANCEL, locale));
			sb.append(A_HREF_END);
			sb.append(SPACE_HTML_TAG);
			sb.append(VERTICAL_BAR_HTML_TAG);
			sb.append(SPACE_HTML_TAG);
		}

		if (OrderStatusEnum.CANCELLED.toString().toUpperCase().equals(orderStatus.toUpperCase())) {
			sb.append(A_HREF_DELETE_MY_ORDER);
			sb.append(orderId);
			sb.append(TAG_END);
			sb.append(this.getMessage(MessageManager.TAG_DELETE, locale));
			sb.append(A_HREF_END);
			sb.append(SPACE_HTML_TAG);
			sb.append(VERTICAL_BAR_HTML_TAG);
			sb.append(SPACE_HTML_TAG);
		}

		sb.append(A_HREF_SHOW_MY_BILL);
		sb.append(orderId);
		sb.append(TAG_END);
		sb.append(this.getMessage(MessageManager.TAG_SHOW_BILL, locale));
		sb.append(A_HREF_END);

		sb.append(PERIOD_HTML_TAG);

		sb.append(LI_END_HTML_TAG);

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