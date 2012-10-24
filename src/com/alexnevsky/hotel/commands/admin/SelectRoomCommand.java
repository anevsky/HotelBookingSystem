package com.alexnevsky.hotel.commands.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.controller.Controller;
import com.alexnevsky.hotel.dao.AbstractDAOFactory;
import com.alexnevsky.hotel.dao.FormDAO;
import com.alexnevsky.hotel.dao.OrderDAO;
import com.alexnevsky.hotel.dao.RoomDAO;
import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.ConfigurationManager;
import com.alexnevsky.hotel.manager.MessageManager;
import com.alexnevsky.hotel.model.Form;
import com.alexnevsky.hotel.model.Order;
import com.alexnevsky.hotel.model.Room;
import com.alexnevsky.hotel.model.enums.OrderStatusEnum;

/**
 * This admin's command selects room for customer's order.
 * 
 * @version 1.0 22.05.2011
 * @author Alex Nevsky
 */
public class SelectRoomCommand implements ICommand {

	static {
		logger = Logger.getLogger(ViewOrdersCommand.class);
	}
	private static Logger logger;

	/**
	 * Selects free room for customer's order
	 * and redirects admin to the main page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		String roomIdStr = request.getParameter(AttributesManager.PARAM_NAME_ROOM_ID);

		Long roomId = null;
		if (!roomIdStr.isEmpty() && roomIdStr != null) {
			roomId = Long.valueOf(roomIdStr);
		}

		Long orderId = (Long) request.getSession().getAttribute(AttributesManager.PARAM_NAME_ORDER_ID);
		Long formId = (Long) request.getSession().getAttribute(AttributesManager.PARAM_NAME_FORM_ID);
		request.getSession().removeAttribute(AttributesManager.PARAM_NAME_ORDER_ID);
		request.getSession().removeAttribute(AttributesManager.PARAM_NAME_FORM_ID);

		logger.info("Admin '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. Execute "
				+ this.toString() + ". RemoteAddr: " + request.getRemoteAddr());

		try {
			AbstractDAOFactory daoFactory = Controller.getDAOFactory();

			OrderDAO orderDAO = daoFactory.getOrderDAO();
			FormDAO formDAO = daoFactory.getFormDAO();
			RoomDAO roomDAO = daoFactory.getRoomDAO();

			Order order = null;
			order = orderDAO.find(orderId);

			Form form = null;
			form = formDAO.find(formId);

			Room room = null;
			room = roomDAO.find(roomId);

			Double amount = null;
			if (form != null && room != null) {
				amount = room.getNightPrice() * form.getNights();
				orderDAO.update(roomId, amount, order.getId());
				orderDAO.update(OrderStatusEnum.CHECKED, order.getId());
			}

			request.setAttribute(AttributesManager.ATTRIBUTE_RESULT, MessageManager.RESULT_SELECT_ROOM_MESSAGE);

		} catch (DAOException ex) {
			logger.error(ex, ex);
			request.setAttribute(AttributesManager.ATTRIBUTE_ERROR_MESSAGE, MessageManager.DAO_EXCEPTION_ERROR_MESSAGE);
			page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
			return page;
		}

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);

		return page;
	}

	@Override
	public String toString() {
		return "ViewOrdersCommand{" + '}';
	}
}