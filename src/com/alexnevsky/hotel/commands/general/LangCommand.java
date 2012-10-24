package com.alexnevsky.hotel.commands.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.ConfigurationManager;

/**
 * This command sets special user's locale.
 * 
 * @version 1.0 11.05.2011
 * @author Alex Nevsky
 */
public class LangCommand implements ICommand {

	static {
		logger = Logger.getLogger(LangCommand.class);
	}
	private static Logger logger;

	/**
	 * Sets special user's locale that he chose
	 * and redirects user to the index page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		String locale = request.getParameter(AttributesManager.PARAM_NAME_LOCALE);

		if (locale == null || locale.isEmpty()) {
			logger.info("User '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. "
					+ "Locale is empty or null" + ". RemoteAddr: " + request.getRemoteAddr());
			page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.CONTROLLER);
			return page;
		}

		if (locale.equalsIgnoreCase(AttributesManager.ATTRIBUTE_LOCALE_RU)
				|| locale.equalsIgnoreCase(AttributesManager.ATTRIBUTE_LOCALE_EN)) {
			logger.info("User '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. "
					+ "Set special user locale: '" + locale + "'" + ". RemoteAddr: " + request.getRemoteAddr());
			request.setAttribute(AttributesManager.PARAM_NAME_SPECIAL_USER_LOCALE, locale);
		} else {
			logger.warn("User '" + request.getSession().getAttribute(AttributesManager.PARAM_NAME_LOGIN) + "'. "
					+ "Trying set unsupported special user locale: '" + locale + "'" + ". RemoteAddr: "
					+ request.getRemoteAddr());
		}

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.INDEX_PAGE_PATH);

		return page;
	}

	@Override
	public String toString() {
		return "LangCommand{" + '}';
	}
}
