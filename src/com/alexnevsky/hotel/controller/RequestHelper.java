package com.alexnevsky.hotel.controller;

import java.util.HashMap;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.commands.admin.CancelOrderCommand;
import com.alexnevsky.hotel.commands.admin.DeleteOrderCommand;
import com.alexnevsky.hotel.commands.admin.FindRoomCommand;
import com.alexnevsky.hotel.commands.admin.SelectRoomCommand;
import com.alexnevsky.hotel.commands.admin.ViewBillCommand;
import com.alexnevsky.hotel.commands.admin.ViewCustomersCommand;
import com.alexnevsky.hotel.commands.admin.ViewOrdersCommand;
import com.alexnevsky.hotel.commands.admin.ViewRoomsCommand;
import com.alexnevsky.hotel.commands.customer.BookingRoomCommand;
import com.alexnevsky.hotel.commands.customer.CancelMyOrderCommand;
import com.alexnevsky.hotel.commands.customer.DeleteMyOrderCommand;
import com.alexnevsky.hotel.commands.customer.ProcessFormCommand;
import com.alexnevsky.hotel.commands.customer.ShowMyBillCommand;
import com.alexnevsky.hotel.commands.customer.ShowMyOrdersCommand;
import com.alexnevsky.hotel.commands.general.LangCommand;
import com.alexnevsky.hotel.commands.general.LoginCommand;
import com.alexnevsky.hotel.commands.general.LogoutCommand;
import com.alexnevsky.hotel.commands.general.NoCommand;
import com.alexnevsky.hotel.manager.AttributesManager;

/**
 * Request helper class for resolving actions.
 * Implements Singleton pattern.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class RequestHelper {

	static {
		logger = Logger.getLogger(RequestHelper.class);
	}
	private static Logger logger;
	private static RequestHelper instance = null;
	/**
	 * All available commands catalog.
	 */
	private HashMap<String, ICommand> commandCatalog = new HashMap<String, ICommand>();
	/**
	 * Administrator's and system's commands catalog.
	 */
	private HashSet<Class<?>> superCommandCatalog = new HashSet<Class<?>>();

	/**
	 * Construct command's catalogues.
	 */
	private RequestHelper() {
		// general commands
		this.commandCatalog.put(AttributesManager.COMMAND_LOGIN, new LoginCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_LOGOUT, new LogoutCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_LANG, new LangCommand());

		// customer commands
		this.commandCatalog.put(AttributesManager.COMMAND_BOOKING_ROOM, new BookingRoomCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_PROCESS_FORM, new ProcessFormCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_SHOW_MY_ORDERS, new ShowMyOrdersCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_SHOW_MY_BILL, new ShowMyBillCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_CANCEL_MY_ORDER, new CancelMyOrderCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_DELETE_MY_ORDER, new DeleteMyOrderCommand());

		// admin commands
		this.commandCatalog.put(AttributesManager.COMMAND_VIEW_ROOMS, new ViewRoomsCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_VIEW_ORDERS, new ViewOrdersCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_VIEW_BILL, new ViewBillCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_VIEW_CUSTOMERS, new ViewCustomersCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_CANCEL_ORDER, new CancelOrderCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_DELETE_ORDER, new DeleteOrderCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_FIND_ROOM, new FindRoomCommand());
		this.commandCatalog.put(AttributesManager.COMMAND_SELECT_ROOM, new SelectRoomCommand());

		// admin and system commands
		this.superCommandCatalog.add(ViewRoomsCommand.class);
		this.superCommandCatalog.add(ViewOrdersCommand.class);
		this.superCommandCatalog.add(ViewBillCommand.class);
		this.superCommandCatalog.add(ViewCustomersCommand.class);
		this.superCommandCatalog.add(CancelOrderCommand.class);
		this.superCommandCatalog.add(DeleteOrderCommand.class);
		this.superCommandCatalog.add(FindRoomCommand.class);
	}

	/**
	 * Returns RequestHelper instance.
	 * 
	 * @return Ready-to-use RequestHelper instance.
	 */
	public static RequestHelper getInstance() {
		if (instance == null) {
			instance = new RequestHelper();
		}
		return instance;
	}

	/**
	 * Parses given request and finds "command" parameter in it.
	 * 
	 * @param request
	 *            User's request to parse.
	 * @return Corresponding for this purposes ICommand implementor.
	 */
	public ICommand getCommand(HttpServletRequest request) {
		String action = request.getParameter(AttributesManager.PARAM_NAME_COMMAND);

		logger.info("User '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. "
				+ "Request command action: '" + action + "'" + ". RemoteAddr: " + request.getRemoteAddr());

		ICommand command = this.processRequest(request, action);

		if (command == null) {
			command = new NoCommand();
		}

		return command;
	}

	/**
	 * Finds corresponding ICommand implementor and returns it.
	 * Also checks the user's permissions and authorization.
	 * 
	 * @param request
	 *            User's request.
	 * @param action
	 *            User's action.
	 * @return Corresponding for this purposes ICommand implementor.
	 */
	private ICommand processRequest(HttpServletRequest request, String action) {
		ICommand command = null;

		command = this.commandCatalog.get(action);

		if (this.isAuthUser(request)) {
			if (this.isAdminCommand(command)) {
				if (this.isAdminUser(request)) {
					logger.info("Admin '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN)
							+ "'. " + "Process incoming request with following super command: " + command
							+ ". RemoteAddr: " + request.getRemoteAddr());
				} else {
					logger.warn("User '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN)
							+ "'. " + "Attempt to execute the super command: " + command + ". RemoteAddr: "
							+ request.getRemoteAddr());
					command = new NoCommand();
				} // end isAdminUser
			} // end isAdminCommand

			logger.info("User '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. "
					+ "Process incoming request with following command: " + command + ". RemoteAddr: "
					+ request.getRemoteAddr());

		} else { // not auth user. if lang command available for everybody
			if (action != null && action.equalsIgnoreCase(AttributesManager.ATTRIBUTE_LANG)) {
				command = new LangCommand();

				logger.info("Not auth user change language: '" + command + "'" + ". RemoteAddr: "
						+ request.getRemoteAddr());

			} else { // not lang command. login command: default command for not auth user
				command = new LoginCommand();

				logger.warn("Action '" + action + "' from not auth user. Create and send command to controller: '"
						+ command + "'" + ". RemoteAddr: " + request.getRemoteAddr());
			} // end if lang action
		} // end isAuthUser()

		return command;
	}

	/**
	 * Returns true if an authorized user sends request.
	 * 
	 * @param request
	 *            User's request.
	 * @return True if an authorized user sends request.
	 */
	public boolean isAuthUser(HttpServletRequest request) {
		boolean success = false;

		HttpSession session = request.getSession();

		if (session == null) {
			success = false;
			return success;
		}

		if (session.getAttribute(AttributesManager.ATTRIBUTE_ADMIN) != null
				|| session.getAttribute(AttributesManager.ATTRIBUTE_CUSTOMER) != null) {
			success = true;
		} else {
			session.invalidate();
		}

		return success;
	}

	/**
	 * Returns true if the user that sent the request is Admin.
	 * 
	 * @param request
	 *            User's request.
	 * @return True if the given user is Admin.
	 */
	public boolean isAdminUser(HttpServletRequest request) {
		boolean success = false;

		HttpSession session = request.getSession();

		if (session == null) {
			success = false;
			return success;
		}

		if (session.getAttribute(AttributesManager.ATTRIBUTE_ADMIN) != null) {
			success = true;
		}

		return success;
	}

	/**
	 * Returns true if the given command is admin's or system's command.
	 * 
	 * @param command
	 *            Command.
	 * @return True if the given command is admin's or system's command.
	 */
	public boolean isAdminCommand(ICommand command) {
		boolean success = false;

		if (command != null) {
			if (this.superCommandCatalog.contains(command.getClass())) {
				success = true;
			}
		}

		return success;
	}
}
