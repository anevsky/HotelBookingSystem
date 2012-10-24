package com.alexnevsky.hotel.webapp;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.alexnevsky.hotel.dao.AbstractDAOFactory;

/**
 * Configures the webapp.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public class Config implements ServletContextListener {

	private static final String ATTRIBUTE_CONFIG = "config";
	private static final String DATABASE_NAME = "database.name";

	private AbstractDAOFactory daoFactory;

	@Override
	public synchronized void contextInitialized(ServletContextEvent event) {
		// Obtain the AbstractDAOFactory and put the Config self in the app scope.
		ServletContext servletContext = event.getServletContext();
		String databaseName = servletContext.getInitParameter(DATABASE_NAME);
		this.daoFactory = AbstractDAOFactory.getInstance(databaseName);
		servletContext.setAttribute(ATTRIBUTE_CONFIG, this);
	}

	@Override
	public synchronized void contextDestroyed(ServletContextEvent event) {
		// Nothing to do here.
	}

	/**
	 * Returns the current Config instance from the application scope.
	 * 
	 * @param servletContext
	 *            The application scope to return current Config instance for.
	 * @return The current Config instance from the application scope.
	 */
	public static synchronized Config getInstance(ServletContext servletContext) {
		return (Config) servletContext.getAttribute(ATTRIBUTE_CONFIG);
	}

	/**
	 * Returns the DAO Factory associated with this Config.
	 * 
	 * @return The DAO Factory associated with this Config
	 */
	public synchronized AbstractDAOFactory getDAOFactory() {
		return this.daoFactory;
	}

}
