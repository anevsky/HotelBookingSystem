package com.alexnevsky.hotel.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Common interface for all commands.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public interface ICommand {

	/**
	 * Processes user request and returns path to the page with results.
	 * 
	 * @param request
	 *            User request that contains necessary parameters for processing.
	 * @param response
	 *            Response to send back to the user.
	 * @return
	 *         Path to the page with results of action performing.
	 */
	public String execute(HttpServletRequest request, HttpServletResponse response);
}
