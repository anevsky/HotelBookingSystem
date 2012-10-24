package com.alexnevsky.hotel.commands.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.ConfigurationManager;

/**
 * This command allows the user to log out.
 * 
 * @version 1.0 11.05.2011
 * @author Alex Nevsky
 */
public class LogoutCommand implements ICommand {

	static {
		logger = Logger.getLogger(LogoutCommand.class);
	}
	private static Logger logger;

	/**
	 * Log out user from the system, invalidates user's session
	 * and redirects user to the index page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		HttpSession session = request.getSession();

		if (session != null) {
			logger.info("Session invalidate for user with role = '"
					+ session.getAttribute(AttributesManager.ATTRIBUTE_USER_ROLE) + "' and login = '"
					+ session.getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'" + ". RemoteAddr: "
					+ request.getRemoteAddr());
			session.invalidate();
		}

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.INDEX_PAGE_PATH);

		return page;
	}

	@Override
	public String toString() {
		return "LogoutCommand{" + '}';
	}
}
