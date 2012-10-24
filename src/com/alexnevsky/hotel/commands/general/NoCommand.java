package com.alexnevsky.hotel.commands.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.manager.ConfigurationManager;

/**
 * This default command with no actions.
 * 
 * @version 1.0 11.05.2011
 * @author Alex Nevsky
 */
public class NoCommand implements ICommand {

	/**
	 * Just redirects user to the main page.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);
		return page;
	}

	@Override
	public String toString() {
		return "NoCommand{" + '}';
	}
}
